package in.cw.sense.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cwf.helper.exception.BusinessException;
import in.cw.csense.app.message.element.BillDetailMessageElement;
import in.cw.csense.app.message.element.Message;
import in.cw.csense.app.message.element.MessageElementType;
import in.cw.sense.api.bo.bill.entity.BillEntity;
import in.cw.sense.app.bill.BillDao;
import in.cw.sense.app.helper.MessageSenderHelper;

/**
 * Bill Synch service.
 * A service utility to synch local bill details to cloud system.
 * 
 * @author Arya
 *
 */
@Service
public class BillSynchServiceImpl implements BillSynchService {

	@Autowired
	private BillDao billDao;

	private MessageSenderHelper senderHelper = new MessageSenderHelper();

	/**
	 * This method is responsible to collect all the un-synched bills from the database, and send those bills to cloud server.
	 */
	public void syncBillToCloud() {
		try {
			List<BillEntity> unSynchedBills = billDao.getAllNonSynchedBills();
			System.out.println("Total un synched bills: " + unSynchedBills.size());
			sendMessageWithBillDtos(unSynchedBills);

		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Send unsynched bill details to cloud.
	 * 
	 * @param unSynchedBillDto Collection of BillDto that need to be synched.
	 */
	private void sendMessageWithBillDtos(final List<BillEntity> unSynchedBillDto) {
		try {
			System.out.println("Sending unsynched bills to cloud ... ");
			senderHelper.sendMessage(prepareMessageWithUnSynchedBills(unSynchedBillDto));
		} catch (BusinessException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Prepares the Message object from given Collection of BillDto object.
	 * 
	 * @param unSynchedBills Collection of BillEntity object.
	 * @return message, an instance of Message.
	 */
	private Message prepareMessageWithUnSynchedBills(List<BillEntity> unSynchedBills) {
		Message message = new Message();
		BillDetailMessageElement messageElement = new BillDetailMessageElement(unSynchedBills);
		message.setMessageElement(messageElement);
		message.setType(MessageElementType.BILL_DETAIL);
		return message;
	}
}
