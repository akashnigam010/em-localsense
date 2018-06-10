package in.cw.sense.app.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cwf.helper.exception.BusinessException;
import in.cw.csense.app.message.element.HandShakeMessageElement;
import in.cw.csense.app.message.element.Message;
import in.cw.csense.app.message.element.MessageElementType;
import in.cw.sense.api.bo.setting.dto.CloudConnectDto;
import in.cw.sense.app.helper.MessageSenderHelper;
import in.cw.sense.app.setting.SettingDao;

@Service
public class CloudConnectServiceImpl implements CloudConnectService {
	private static final Logger LOG = Logger.getLogger(CloudConnectServiceImpl.class);
	private MessageSenderHelper senderHelper = new MessageSenderHelper();

	@Autowired
	private SettingDao setting;

	@Override
	public void connect() {
		senderHelper.initiateConnection();
	}

	@Override
	public void handShake() {
		try {
			senderHelper.sendMessage(getHandShakeMessage());
		} catch (BusinessException e) {
			LOG.error("Exception occured while sendMessage in handShake", e);
		}
	}

	/**
	 * Provides the Hand shake message required to establish connection with
	 * Cloud server.
	 * 
	 * @return message, An instance of Message type.
	 */
	private Message getHandShakeMessage() {
		Message message = new Message();
		message.setType(MessageElementType.HANDSHAKE);
		HandShakeMessageElement element = new HandShakeMessageElement(getRestaurentInfo());
		message.setMessageElement(element);
		return message;
	}

	/**
	 * Returns the details of restaurant willing to connect to Cloud server.
	 * 
	 * @return result, An instance of CloudConnectDto class.
	 */
	private CloudConnectDto getRestaurentInfo() {
		CloudConnectDto result = null;
		try {
			result = setting.getCloudSettings();
		} catch (BusinessException e) {
			LOG.error("Exception occured while getting cloud settings...", e);
		}
		return result;
	}
}
