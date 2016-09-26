package in.cw.sense.app.bill;

import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import cwf.helper.exception.BusinessException;
import cwf.helper.type.GenericErrorCodeType;
import in.cw.sense.api.bo.emailTemplate.dto.EmailTemplateDto;
import in.cw.sense.api.bo.emailTemplate.entity.EmailTemplate;
import in.cw.sense.api.bo.emailTemplate.type.EmailTemplateType;
import in.cw.sense.app.bill.mapper.EmailTemplateMapper;

@Repository
public class EmailTemplateDao {
	private static final Logger LOG = Logger.getLogger(BillDao.class);

	@Autowired MongoTemplate senseMongoTemplate;
	@Autowired EmailTemplateMapper mapper;

	public void setSenseMongoTemplate(MongoTemplate senseMongoTemplate) {
		this.senseMongoTemplate = senseMongoTemplate;
	}

	public Map<EmailTemplateType, EmailTemplateDto> getBillEmailTemplates() throws BusinessException {
		List<EmailTemplate> entities = senseMongoTemplate.findAll(EmailTemplate.class);
		if (entities == null || entities.isEmpty()) {
			LOG.error("Email Templates Not Found");
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
		return mapper.mapEmailTemplates(entities);
	}
}
