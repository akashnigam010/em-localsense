package in.cw.sense.api.bo.emailTemplate.dto;

import in.cw.sense.api.bo.emailTemplate.type.EmailTemplateType;

public class EmailTemplateDto {
	private EmailTemplateType templateType;
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
