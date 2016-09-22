package in.cw.sense.app.websocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cwf.helper.exception.BusinessException;
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

	// TODO : Replace the hard coded uri with value defined in properties file.
	private final String uri = "ws://localhost:8087/cloudsense/server";

	/** Websocket session object. */
	private Session session;

	/** LSCLient instance. */
	private static LSClient lsClient = null;

	// @Autowired
	// TODO : remove the direct initialization of the field, instead use
	// autowire.
	private MessageProcessInitializer messageProcessInitializer = new MessageProcessInitializer();

	private LSClient() throws DeploymentException, IOException, URISyntaxException {
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		container.connectToServer(this, new URI(uri));
	}

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("Message recieved : " + message);
		messageProcessInitializer.process(message, session);
	}

	@OnClose
	public void onClose() {
		this.session = null;
	}

	public void sendMessage(String message) throws BusinessException {
		try {
			if (this.session != null && this.session.isOpen()) {
				session.getBasicRemote().sendText(message);
			} else {
				System.out.println("Websocket session is not open.. can not send message now.... ");
				throw new BusinessException(CloudSenseConnectErrorCodeType.SESSION_NOT_OPEN);
			}
		} catch (IOException ex) {

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
		if (lsClient == null) {
			// double locking check for singleton object.
			synchronized (LSClient.class) {
				if (lsClient == null) {
					lsClient = new LSClient();
				}
			}
		}
		return lsClient;
	}

	public void reTryToConnectToServer() throws DeploymentException, IOException, URISyntaxException {
		new LSClient();
	}

	public boolean isSessionOpen() {
		return (this.session != null && this.session.isOpen());
	}

}
