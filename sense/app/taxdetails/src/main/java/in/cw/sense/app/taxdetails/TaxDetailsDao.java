package in.cw.sense.app.taxdetails;

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
import in.cw.sense.api.bo.bill.entity.BillEntity;
import in.cw.sense.api.bo.bill.type.BillStatusType;
import in.cw.sense.api.bo.tax.entity.TaxDetailsEntity;
import in.cw.sense.api.bo.tax.request.TaxDetailsRequest;
import in.cw.sense.app.taxdetails.mapper.TaxDetailsMapper;
import in.cw.sense.app.taxdetails.type.TaxDetailsErrorCodeType;
import in.cw.sense.app.taxdetails.type.TaxType;

@Repository
public class TaxDetailsDao {
	@Autowired SequenceDao sequenceDao;
	@Autowired TaxDetailsMapper mapper;
	@Autowired MongoTemplate senseMongoTemplate;

	private static final Logger LOG = Logger.getLogger(TaxDetailsDao.class);
	private static final String ID ="_id";
	private static final String TAX_DETAILS_SEQ = "tax_details_seq";
	private static final String TAX_TYPE = "taxType";
	private static final String STATUS = "status";

	public void setSenseMongoTemplate(MongoTemplate senseMongoTemplate) {
		this.senseMongoTemplate = senseMongoTemplate;
	}

	public List<TaxDetailsEntity> getAllTaxDetails() throws BusinessException {
		try {
			return senseMongoTemplate.findAll(TaxDetailsEntity.class);
		} catch (Exception e) {
			LOG.error( "Failed to fetch tax details", e);
			throw new BusinessException(TaxDetailsErrorCodeType.GENERIC_ERROR);
		}
	}
	
	public List<TaxDetailsEntity> getTaxDetailsInternalOrExternal(TaxType taxType) throws BusinessException {
		try {
			Query query = Query.query(Criteria.where(TAX_TYPE).is(taxType.getType()));
			return senseMongoTemplate.find(query, TaxDetailsEntity.class);
		} catch (Exception e) {
			LOG.error( "Failed to fetch tax details", e);
			throw new BusinessException(TaxDetailsErrorCodeType.GENERIC_ERROR);
		}
	}

	public void addOrEditTaxDetails(TaxDetailsRequest request) throws BusinessException {
		TaxDetailsEntity taxDetails = null;
		try {
			if (request.getId() == null) {
				taxDetails = new TaxDetailsEntity();
				taxDetails.setId(sequenceDao.getNextSequenceId(TAX_DETAILS_SEQ));
			} else {
				taxDetails = senseMongoTemplate.findById(request.getId(), TaxDetailsEntity.class);
				if (taxDetails == null) {
					throw new BusinessException(TaxDetailsErrorCodeType.TAX_DETAILS_DOESNOT_EXIST);
				}
			}
			mapper.map(request, taxDetails);
			senseMongoTemplate.save(taxDetails);
		} catch (Exception e) {
			LOG.error( "Failed to fetch tax details", e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(TaxDetailsErrorCodeType.GENERIC_ERROR);
		}
	}

	public void removeTaxDetails(TaxDetailsRequest request) throws BusinessException {
		try {
			Query query = Query.query(Criteria.where(ID).is(request.getId()));
			senseMongoTemplate.findAndRemove(query, TaxDetailsEntity.class);
		} catch (Exception e) {
			LOG.error( "Failed to fetch tax details", e);
			throw new BusinessException(TaxDetailsErrorCodeType.GENERIC_ERROR);
		}
	}

	public void deleteUnsettledBillDetails() throws BusinessException {
		try {
			Query findQuery = Query.query(Criteria.where(STATUS).is(BillStatusType.UNSETTLED.getStatus()));
			senseMongoTemplate.findAllAndRemove(findQuery, BillEntity.class);
		} catch (Exception e) {
			LOG.error( "Failed to delete unsettled bill details", e);
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
	}
}