package in.cw.sense.app.setting;

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
import in.cw.sense.api.bo.setting.dto.CloudConnectDto;
import in.cw.sense.api.bo.setting.entity.CloudConnect;
import in.cw.sense.api.bo.setting.request.SettingRequest;
import in.cw.sense.app.setting.type.SettingErrorCodeType;

@Repository
public class SettingDao {
	private static final Logger LOG = Logger.getLogger(SettingDao.class);
	@Autowired SenseContext context;
	@Autowired SettingMapper mapper;
	@Autowired MongoTemplate senseMongoTemplate;

	public void setSenseMongoTemplate(MongoTemplate senseMongoTemplate) {
		this.senseMongoTemplate = senseMongoTemplate;
	}

	public CloudConnectDto getCloudSettings() throws BusinessException {
		List<CloudConnect> cloudConnnectEntities = senseMongoTemplate.findAll(CloudConnect.class);
		if (cloudConnnectEntities.size() == 0) {
			throw new BusinessException(SettingErrorCodeType.CLOUD_CONNECT_DETAILS_NOT_AVAILABLE);
		} else if (cloudConnnectEntities.size() > 1) {
			throw new BusinessException(SettingErrorCodeType.INVALID_DATA);
		} else {
			return mapper.map(cloudConnnectEntities.get(0));
		}
	}

	public CloudConnectDto saveCloudSettings(SettingRequest request) throws BusinessException {
		CloudConnect entity = null;
		try {
			//TODO : atomic transaction required
			senseMongoTemplate.remove(new Query(), CloudConnect.class);
			entity = mapper.map(request, new CloudConnect());
			senseMongoTemplate.save(entity);
			return mapper.map(entity);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("No encryption algorithm found while encrypting cloud connect password");
			throw new BusinessException(SettingErrorCodeType.ENCRYPT_ALGO_NOT_FOUND, e.getMessage());
		}
	}
}
