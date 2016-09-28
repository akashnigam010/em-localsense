package in.cw.sense.app.bill;

import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import cwf.dbhelper.sequencegenerator.SequenceDao;
import cwf.helper.exception.BusinessException;
import cwf.helper.type.GenericErrorCodeType;
import in.cw.sense.api.bo.bill.dto.BillDto;
import in.cw.sense.api.bo.bill.entity.BillEntity;
import in.cw.sense.api.bo.bill.entity.OrderUnit;
import in.cw.sense.api.bo.bill.type.BillStatusType;
import in.cw.sense.api.bo.bill.type.CloudSyncStatusType;
import in.cw.sense.api.bo.kot.entity.Kot;
import in.cw.sense.app.bill.mapper.BillMapper;
import in.cw.sense.app.bill.type.BillDetailsErrorCodeType;
import in.cw.sense.app.tabledetails.TablesDetailsDao;

@Repository
public class BillDao {
	@Autowired SequenceDao sequenceDao;
	@Autowired BillMapper mapper;
	@Autowired TablesDetailsDao tablesDao;
	@Autowired MongoTemplate senseMongoTemplate;
	
	private static final Logger LOG = Logger.getLogger(BillDao.class);
	private static final String ID = "_id";
	private static final String TABLE_ID = "tableId";
	private static final String BILL_SEQ = "bill_seq";
	private static final String BILL_STATUS = "status";
	private static final String SYNC_STATUS = "syncStatus";
	
	public void setSenseMongoTemplate(MongoTemplate senseMongoTemplate) {
		this.senseMongoTemplate = senseMongoTemplate;
	}
	
	public BillEntity getBillForATable(Integer tableId, Integer billId) throws BusinessException {
		try {
			Query findQuery = Query.query(Criteria.where(ID).is(billId))
					.addCriteria(Criteria.where(TABLE_ID).is(tableId));
			return senseMongoTemplate.findOne(findQuery, BillEntity.class);
		} catch (Exception e) {
			LOG.error("Exception occured while fetching bill details for tableId: "+ tableId, e);
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}
	
	public List<BillDto> getSettledBills(Date startDate, Date endDate) throws BusinessException {
		try {
			Query findQuery = Query.query(Criteria.where(BILL_STATUS).is(BillStatusType.SETTLED.getStatus()))
						.addCriteria(Criteria.where("createdDateTime").gte(startDate).lt(endDate));
			List<BillEntity> bills = senseMongoTemplate.find(findQuery, BillEntity.class);
			return mapper.mapBillEntitiesToDtos(bills);
		} catch (Exception e) {
			LOG.error("Exception occured while fetching all bill details", e);
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}
	
	public BillEntity addOrderItemsToBill(List<OrderUnit> orders, Integer tableId) 
			throws BusinessException {
		int billId = sequenceDao.getNextSequenceId(BILL_SEQ);
		BillEntity bill = new BillEntity();
		bill.setId(billId);
		bill.setOrders(orders);
		mapper.mapTableOrderDetailsToBill(tablesDao.getTableInformation(tableId), bill);
		senseMongoTemplate.save(bill);
		return bill;
	}
	
	public BillEntity updateTableOrdersToBill(List<OrderUnit> orders, Integer billId, Integer tableId) 
			throws BusinessException {
		BillEntity bill = senseMongoTemplate.findById(billId, BillEntity.class);
		if (bill == null) {
			throw new BusinessException(BillDetailsErrorCodeType.BILL_DETAILS_NOT_FOUND);
		}
		bill.setOrders(orders);
		mapper.mapTableOrderDetailsToBill(tablesDao.getTableInformation(tableId), bill);
		senseMongoTemplate.save(bill);
		return bill;
	}

	public BillEntity saveBill(BillEntity bill) {
		senseMongoTemplate.save(bill);
		return bill;
	}
	
	public BillEntity getBill(Integer id) {
		return senseMongoTemplate.findById(id, BillEntity.class);
	}

	public List<Kot> getAssociatedKotDetails(Integer billId) {
		Query findQuery = Query.query(Criteria.where("billIds").in(billId));
		return senseMongoTemplate.find(findQuery, Kot.class);
	}
	

	public void saveKot(Kot kot) {
		senseMongoTemplate.save(kot);
	}
	
	/**
	 * This method returns the list of all bill dto objects which is not syned to cloud.
	 * 
	 * @return	Collection of bill dto object, which is not synced to cloud database.
	 * @throws BusinessException in case of any exception.
	 * TODO: convert to BillDto - Himant
	 */
	public List<BillDto> getAllNonSynchedBills() throws BusinessException {
		try {
			Query findQuery = Query.query(Criteria.where(SYNC_STATUS).is(CloudSyncStatusType.NOT_IN_SYNC.getSyncStatus()))
						.addCriteria(Criteria.where(BILL_STATUS).is(BillStatusType.SETTLED.getStatus()));
			List<BillEntity> bills = senseMongoTemplate.find(findQuery, BillEntity.class);
			return mapper.mapBillEntitiesToDtos(bills);
		} catch (Exception e) {
			LOG.error("Exception occured while fetching all bill details", e);
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}
}
