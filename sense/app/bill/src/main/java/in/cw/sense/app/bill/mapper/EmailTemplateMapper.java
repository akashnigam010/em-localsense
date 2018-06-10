package in.cw.sense.app.bill.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import in.cw.sense.api.bo.emailTemplate.dto.EmailTemplateDto;
import in.cw.sense.api.bo.emailTemplate.entity.EmailTemplate;
import in.cw.sense.api.bo.emailTemplate.type.EmailTemplateType;

@Component
public class EmailTemplateMapper {

	public EmailTemplateDto mapEmailTemplate(EmailTemplate entity) {
		EmailTemplateDto dto = new EmailTemplateDto();
		dto.setTemplateType(entity.getTemplateType());
		dto.setValue(entity.getValue());
		return dto;
	}

	public Map<EmailTemplateType, EmailTemplateDto> mapEmailTemplates(List<EmailTemplate> entites) {
		Map<EmailTemplateType, EmailTemplateDto> map = new HashMap<>();
		for (EmailTemplate entity : entites) {
			map.put(entity.getTemplateType(), mapEmailTemplate(entity));
		}
		return map;
	}
}
