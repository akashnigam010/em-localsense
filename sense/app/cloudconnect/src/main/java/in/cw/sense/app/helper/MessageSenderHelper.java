package in.cw.sense.app.helper;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.websocket.DeploymentException;

import cwf.helper.InternetAvailabilityChecker;
import cwf.helper.exception.BusinessException;
import in.cw.csense.app.message.element.Message;
import in.cw.csense.app.util.JsonUtil;
import in.cw.sense.app.websocket.LSClient;

/**
 * A helper class to assist in message sending to Cloud.
 * 
 * @author Arya
 *
 */
public class MessageSenderHelper {

	private static LSClient lsClient;

	public MessageSenderHelper() {

	}

	public void initiateConnection() {
		InternetAvailabilityChecker checker = new InternetAvailabilityChecker();
		try {
			if (checker.isInternetAvailable()) {
				System.out.println("hello ");
				lsClient = LSClient.getInstance();
			} else {
				System.out.println("Internet connection not available.....");
			}
		} catch (IOException | DeploymentException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(Message message) throws BusinessException, InterruptedException {
		try {
			if (lsClient != null && lsClient.isSessionOpen()) {
				lsClient.sendMessage(JsonUtil.toJson(message));
			} else {
				System.out.println("Session is closed, cant send message now..");
			}
		} catch (BusinessException e) {
			throw e;
		}
	}

}
