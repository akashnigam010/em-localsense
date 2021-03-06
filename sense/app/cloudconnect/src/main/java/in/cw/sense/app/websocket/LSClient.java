package in.cw.sense.app.websocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.apache.log4j.Logger;

import cwf.helper.exception.BusinessException;
import in.cw.sense.app.socket.LocalApplicationContextProvider;
import in.cw.sense.app.type.CloudSenseConnectErrorCodeType;

/**
 * This class act as Local sense client end point for web-socket channel
 * established with cloud server.
 * 
 * This class will be the entry point for every message communication between
 * local sense and cloud sense.
 * 
 * @author Himant Dewangan
 *
 */
@ClientEndpoint
public class LSClient {
	private static final Logger LOG = Logger.getLogger(LSClient.class);
	// TODO : Replace the hard coded uri with value defined in properties file.
	private final String uri = "ws://localhost:8087/cloudsense/server/abc";

	/** Websocket session object. */
	private Session session;

	/** LSCLient instance. */
	private static LSClient lsClient = null;

	// @Autowired
	// TODO : remove the direct initialization of the field, instead use
	// autowire it.
	private MessageProcessInitializer localMessageProcessInitializer;

	private LSClient() throws DeploymentException, IOException, URISyntaxException {
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		container.connectToServer(this, new URI(uri));
	}

	{
		if (this.localMessageProcessInitializer == null) {
			this.localMessageProcessInitializer = (MessageProcessInitializer) LocalApplicationContextProvider
					.getApplicationContext().getBean("localMessageProcessInitializer");
		}
	}

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		LOG.debug("Message recieved : " + message);
		localMessageProcessInitializer.process(message, session);
	}

	@OnClose
	public void onClose() {
		LOG.error("Websocket session from cloud is closed.");
		lsClient = null;
		this.session = null;
	}
	
	@OnError
	public void onError(Throwable throwable) {
		LOG.error("Websocket session from cloud is closed due to some error." + throwable.getMessage());
		lsClient = null;
		this.session = null;
	}

	public void sendMessage(String message) throws BusinessException {
		try {
			if (this.session != null && this.session.isOpen()) {
				session.getBasicRemote().sendText(message);
			} else {
				LOG.warn("Websocket session is not open.. can not send message now.... ");
				throw new BusinessException(CloudSenseConnectErrorCodeType.SESSION_NOT_OPEN);
			}
		} catch (IOException e) {
			LOG.error("Exception occured while sending message to cloud...", e);
		}
	}

	/**
	 * Returns the LSCLient instance, initialized once (singleton instance)
	 * 
	 * @return lsClient the instance of LSClient class.
	 * @throws DeploymentException
	 *             in case of deployment exception.
	 * @throws IOException
	 *             in case of IO exception.
	 * @throws URISyntaxException
	 *             in case URI is not correct.
	 */
	public static LSClient getInstance() throws DeploymentException, IOException, URISyntaxException {
		createConnection();
		return lsClient;
	}
	
	public static void createConnection() throws DeploymentException, IOException, URISyntaxException {
		if (lsClient == null) {
			// double locking check for singleton object.
			synchronized (LSClient.class) {
				if (lsClient == null) {
					lsClient = new LSClient();
				}
			}
		}
	}

	public void reTryToConnectToServer() throws DeploymentException, IOException, URISyntaxException {
		new LSClient();
	}

	public boolean isSessionOpen() {
		return (this.session != null && this.session.isOpen());
	}
	
	
}
