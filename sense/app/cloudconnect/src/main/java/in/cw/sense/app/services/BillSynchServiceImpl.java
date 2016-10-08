package in.cw.sense.app.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cwf.helper.exception.BusinessException;
import in.cw.csense.app.message.element.BillDetailMessageElement;
import in.cw.csense.app.message.element.Message;
import in.cw.csense.app.message.element.MessageElementType;
import in.cw.sense.api.bo.bill.dto.BillDto;
import in.cw.sense.app.bill.BillDao;
import in.cw.sense.app.helper.MessageSenderHelper;
import in.cw.sense.app.restaurantinfo.RestaurantInfoDao;

/**
 * Bill Synch service. A service utility to synch local bill details to cloud
 * system.
 * 
 * @author Arya
 *
 */
@Service
public class BillSynchServiceImpl implements BillSynchService {
	private static final Logger LOG = Logger.getLogger(BillSynchServiceImpl.class);
	private MessageSenderHelper senderHelper = new MessageSenderHelper();

	@Autowired BillDao billDao;
	@Autowired RestaurantInfoDao restaurantInfoDao;

	/**
	 * This method is responsible to collect all the un-synched bills from the
	 * database, and send those bills to cloud server.
	 */
	@Override
	public void syncBillToCloud() {
		try {
			List<BillDto> unSynchedBills = billDao.getAllNonSynchedBills();
			Integer restaurantId = restaurantInfoDao.getRestaurantInformation().getId();
			LOG.debug("Total un synched bills are : " + unSynchedBills.size());
			sendMessageWithBillDtos(unSynchedBills, restaurantId);
		} catch (BusinessException e) {
			LOG.error("Exception occured while fetching restaurant information", e);
		}
	}

	/**
	 * Send unsynched bill details to cloud.
	 * 
	 * @param unSynchedBillDto
	 *            Collection of BillDto that need to be synched.
	 */
	private void sendMessageWithBillDtos(final List<BillDto> unSynchedBillDto, Integer restaurantId) {
		try {
			LOG.debug("Sending unsynched bills to cloud ...");
			senderHelper.sendMessage(prepareMessageWithUnSynchedBills(unSynchedBillDto, restaurantId));
		} catch (BusinessException e) {
			LOG.error("Exception occured while sending unsynced bills to cloud", e);
		}
	}

	/**
	 * Prepares the Message object from given Collection of BillDto object.
	 * 
	 * @param unSynchedBills
	 *            Collection of BillEntity object.
	 * @return message, an instance of Message.
	 */
	private Message prepareMessageWithUnSynchedBills(List<BillDto> unSynchedBills, Integer restaurantId) {
		Message message = new Message();
		BillDetailMessageElement messageElement = new BillDetailMessageElement(unSynchedBills, restaurantId);
		message.setMessageElement(messageElement);
		message.setType(MessageElementType.BILL_DETAIL);
		return message;
	}
}
