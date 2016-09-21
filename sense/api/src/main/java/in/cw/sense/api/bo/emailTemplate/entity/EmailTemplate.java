package in.cw.sense.api.bo.emailTemplate.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import in.cw.sense.api.bo.emailTemplate.type.EmailTemplateType;

@Document(collection = "email_template")
public class EmailTemplate {
	@Field("templateType")
	private EmailTemplateType templateType;

	@Field("value")
	private String value;

	public EmailTemplateType getTemplateType() {
		return templateType;
	}

	public void setTemplateType(EmailTemplateType templateType) {
		this.templateType = templateType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
