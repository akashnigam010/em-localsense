package in.cw.sense.app.helper;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.websocket.DeploymentException;

import org.apache.log4j.Logger;

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
	private static final Logger LOG = Logger.getLogger(MessageSenderHelper.class);
	private static LSClient lsClient;

	public void initiateConnection() {
		InternetAvailabilityChecker checker = new InternetAvailabilityChecker();
		try {
			if (checker.isInternetAvailable()) {
				LOG.debug("Checking your internet availalbility...");
				lsClient = LSClient.getInstance();
			} else {
				LOG.warn("Internet connection not available..... Please check internet availability");
			}
		} catch (IOException | DeploymentException | URISyntaxException e) {
			LOG.error("Unexpected exception occured while fetching LSClient.. ", e);
		}
	}

	public void sendMessage(Message message) throws BusinessException {
		try {
			if (lsClient != null && lsClient.isSessionOpen()) {
				lsClient.sendMessage(JsonUtil.toJson(message));
			} else {
				LOG.warn("Session is closed, cant send message now... ");
			}
		} catch (BusinessException e) {
			throw e;
		}
	}
}
