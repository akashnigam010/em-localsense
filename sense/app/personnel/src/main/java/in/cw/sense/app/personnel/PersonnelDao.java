package in.cw.sense.app.personnel;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import cwf.dbhelper.SenseContext;
import cwf.helper.exception.BusinessException;
import cwf.helper.hash.HashUtil;
import in.cw.sense.api.bo.personnel.dto.PersonnelDto;
import in.cw.sense.api.bo.personnel.entity.Personnel;
import in.cw.sense.api.bo.personnel.request.AddEditPersonnelRequest;
import in.cw.sense.app.personnel.mapper.PersonnelMapper;
import in.cw.sense.app.personnel.type.PersonnelErrorCodeType;

@Repository
public class PersonnelDao {
	private static final Logger LOG = Logger.getLogger(PersonnelDao.class);
	@Autowired SenseContext context;
	@Autowired PersonnelMapper mapper;
	@Autowired MongoTemplate senseMongoTemplate;

	private static final String ID = "id";
	private static final String PERSONNEL_ID_SEQ = "personnel_seq";
	private static final String PIN = "pin";

	public void setSenseMongoTemplate(MongoTemplate senseMongoTemplate) {
		this.senseMongoTemplate = senseMongoTemplate;
	}
	
	public List<PersonnelDto> addPersonnel(AddEditPersonnelRequest request) throws BusinessException {
		try {
			checkIfPinAlreadyExists(request);
			int seqId = (int) context.getNextSequenceId(PERSONNEL_ID_SEQ);
			Personnel personnel = new Personnel();
			personnel.setId(seqId);
			mapper.map(request, personnel);
			senseMongoTemplate.save(personnel);
			return getPersonnels();
		} catch (NoSuchAlgorithmException e) {
			LOG.error("No encryption algorithm found while adding personnel");
			throw new BusinessException(PersonnelErrorCodeType.ENCRYPT_ALGO_NOT_FOUND, e.getMessage());
		}
	}

	public List<PersonnelDto> editPersonnel(AddEditPersonnelRequest request) throws BusinessException {
		try {
			checkIfPinAlreadyExists(request);
			Personnel personnel = senseMongoTemplate.findById(request.getId(), Personnel.class);
			mapper.map(request, personnel);
			senseMongoTemplate.save(personnel);
			return getPersonnels();
		} catch (NoSuchAlgorithmException e) {
			LOG.error("No encryption algorithm found while editing personnel");
			throw new BusinessException(PersonnelErrorCodeType.ENCRYPT_ALGO_NOT_FOUND, e.getMessage());
		}
	}

	public List<PersonnelDto> getPersonnels() {
		List<Personnel> personnels = senseMongoTemplate.findAll(Personnel.class);
		return mapper.map(personnels);
	}

	public List<PersonnelDto> deletePersonnel(Integer id) throws BusinessException {
		Query query = new Query(Criteria.where(ID).is(id));
		Personnel personnel = senseMongoTemplate.findById(id, Personnel.class);
		if (personnel == null) {
			throw new BusinessException(PersonnelErrorCodeType.PERSONNEL_DETAILS_NOT_FOUND);
		}
		senseMongoTemplate.findAndRemove(query, Personnel.class);
		return getPersonnels();
	}

	private void checkIfPinAlreadyExists(AddEditPersonnelRequest request) throws BusinessException {
		try {
			Query pinCheckQuery = new Query(Criteria.where(PIN).is(HashUtil.hash(request.getPin())));
			Personnel pinAlreadyExists = senseMongoTemplate.findOne(pinCheckQuery, Personnel.class);
			if (pinAlreadyExists != null && !pinAlreadyExists.getId().equals(request.getId())) {
				throw new BusinessException(PersonnelErrorCodeType.PIN_ALREADY_EXISTS);
			}
		} catch (NoSuchAlgorithmException e) {
			LOG.error("No encryption algorithm found for encryption while checking for duplicate pin");
			throw new BusinessException(PersonnelErrorCodeType.ENCRYPT_ALGO_NOT_FOUND, e.getMessage());
		}
	}
}
