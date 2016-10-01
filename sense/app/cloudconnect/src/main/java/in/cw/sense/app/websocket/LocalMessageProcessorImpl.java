package in.cw.sense.app.websocket;

import java.util.List;

import javax.websocket.Session;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

import in.cw.csense.app.message.element.BillAckMessageElement;
import in.cw.csense.app.message.element.BillDetailMessageElement;
import in.cw.csense.app.message.element.HandShakeMessageElement;
import in.cw.csense.app.message.element.ProcessedBill;
import in.cw.csense.app.message.element.RequestMessageElement;
import in.cw.csense.app.message.processor.MessageProcessor;
import in.cw.csense.app.socket.SessionCollector;
import in.cw.sense.api.bo.bill.dto.BillDto;
import in.cw.sense.api.bo.bill.entity.BillEntity;
import in.cw.sense.api.bo.bill.type.CloudSyncStatusType;
import in.cw.sense.api.bo.setting.dto.CloudConnectDto;

public class LocalMessageProcessorImpl implements MessageProcessor {
	private static final Logger LOG = Logger.getLogger(LocalMessageProcessorImpl.class);

	@Override
	public void process(HandShakeMessageElement message, Session session) {
		processNewSession(message, session);
	}

	@Override
	public void process(BillDetailMessageElement message, Session session) {
		processTheBills(message.getBills(), session);
		LOG.debug("Processing unsynced bills at cloude side...");
	}

	/**
	 * @param bills  
	 * @param session 
	 */
	private void processTheBills(List<BillDto> bills, final Session session) {
		/*
		 * BillMessageProcessorHelper helper = new
		 * BillMessageProcessorHelper(SharedQueue.getInstance().getQueue());
		 * helper.processBills(bills, session);
		 */
	}

	@Override
	public void process(RequestMessageElement message, Session session) {
	}

	@Override
	public void process(BillAckMessageElement billAckMessageElement, Session session) {
		processBillAckMessage(billAckMessageElement);
	}

	/**
	 * This method will actually stores the new session into the datastructure.
	 * everytime when clould sense has to connect to its client will use this
	 * session to communicate.
	 * 
	 * @param handShakeMessage
	 *            provided web socket message recieved from client.
	 * @param session
	 *            the new session.
	 */
	private void processNewSession(HandShakeMessageElement handShakeMessage, Session session) {
		CloudConnectDto cloudConnectDto = handShakeMessage.getCloudConnect();
		if (cloudConnectDto != null) {
			SessionCollector.putSession(cloudConnectDto.getRestaurantId(), session);
			LOG.info("New session has been stored in the local map for the client : "
					+ cloudConnectDto.getRestaurantId());
		}
	}

	private void processBillAckMessage(BillAckMessageElement billAckMessageElement) {
		for (ProcessedBill processBill : billAckMessageElement.getProcessBills()) {
			List<Integer> success = processBill.getSucceededBillIds();
			List<Integer> failed = processBill.getFailedBillIds();
			if (success.isEmpty()) {
				LOG.debug("No bills were sucessfully processed....");
			} else {
				for (Integer billId : success) {
					LOG.debug("Updating local sync status for bill id : " + billId);
					try {
						updateBillSyncStatus(billId, CloudSyncStatusType.IN_SYNC);
					} catch (Exception e) {
						LOG.error("Exception occured while updating cloud sync status on local DB", e);
					}
				}
			}

			if (!failed.isEmpty()) {
				for (Integer billId : failed) {
					LOG.debug("List of bill ids which were failed in cloud sync : " + billId);
				}
			}
		}
	}
	
	public void updateBillSyncStatus(Integer billId, CloudSyncStatusType syncStatus) throws Exception {
		if (billId != null && syncStatus != null) {
			BillEntity bill = mongoTemplate().findById(billId, BillEntity.class);
			if (bill != null) {
				bill.setSyncStatus(syncStatus);
				mongoTemplate().save(bill);
			}
		}
	}
	
	//ToDo : Remove This once autowiring is done
	public MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient("localhost", 27017), "sense-local-db");
	}

	public MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		return mongoTemplate;
	}
}
