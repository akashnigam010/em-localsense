package in.cw.sense.api.bo.emailTemplate.type;

public enum EmailTemplateType {
	PARENT("PARENT"),
	CATEGORY("CATEGORY"),
	ITEM("ITEM"),
	CHARGE("CHARGE"),
	SUBTOTAL("SUBTOTAL"),
	TOTAL_EXCLUSIVE("TOTAL_EXCLUSIVE"),
	TOTAL_INCLUSIVE("TOTAL_INCLUSIVE"),
	PROMOTION("PROMOTION"),
	FOOTER("FOOTER");

	private String type;

	private EmailTemplateType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static EmailTemplateType getEmailType(String type) {
		for (EmailTemplateType emailType : EmailTemplateType.values()) {
			if (emailType.getType().equals(type)) {
				return emailType;
			}
		}
		return null;
	}

}
