package in.cw.sense.app.login;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import cwf.helper.exception.BusinessException;
import cwf.helper.hash.HashUtil;
import cwf.helper.type.GenericErrorCodeType;
import in.cw.sense.api.bo.personnel.dto.PersonnelDto;
import in.cw.sense.api.bo.personnel.entity.Personnel;
import in.cw.sense.api.bo.personnel.request.UserSignOnRequest;
import in.cw.sense.api.bo.support.entity.Message;
import in.cw.sense.app.login.mapper.LoginMapper;
import in.cw.sense.app.login.type.LoginErrorCodeType;

@Repository
public class LoginDao {
	private static final Logger LOG = Logger.getLogger(LoginDao.class);
	private static final String PIN = "pin";
	private static final String ACCESS_CODE = "accessCode";
	private static final String IS_NEW = "isNew";
	@Autowired MongoTemplate senseMongoTemplate;
	@Autowired LoginMapper mapper;

	public void setSenseMongoTemplate(MongoTemplate senseMongoTemplate) {
		this.senseMongoTemplate = senseMongoTemplate;
	}

	public PersonnelDto login(UserSignOnRequest request, Boolean isPinSignOn) throws BusinessException {
		try {
			Query query = new Query(Criteria.where(PIN).is(HashUtil.hash(request.getPin())));
			if (!isPinSignOn) {
				query.addCriteria(Criteria.where(ACCESS_CODE).is(HashUtil.hash(request.getAccessCode())));
			}
			Personnel user = senseMongoTemplate.findOne(query, Personnel.class);
			if (user == null) {
				throw new BusinessException(LoginErrorCodeType.USER_PIN_ACCESSCODE_DOESNOT_MATCH);
			}
			return mapper.map(user);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("No encryption algorithm found while logging in");
			throw new BusinessException(LoginErrorCodeType.ENCRYPT_ALGO_NOT_FOUND, e.getMessage());
		}
	}

	public Integer getNewMessageCount() throws BusinessException {
		try {
			Query findQuery = Query.query(Criteria.where(IS_NEW).is(true));
			List<Message> entities = senseMongoTemplate.find(findQuery, Message.class);
			return entities.size();
		} catch (Exception e) {
			LOG.error("Exception occured while deleting message", e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}
}
