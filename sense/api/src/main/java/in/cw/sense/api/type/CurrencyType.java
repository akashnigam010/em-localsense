package in.cw.sense.api.type;

public enum CurrencyType {
	INR("INR", "Indian Rupee"), 
	USD("USD", "US Dollar");

	private String code;
	private String description;

	private CurrencyType(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static CurrencyType getCurrency(String codeString) {
		for (CurrencyType type : CurrencyType.values()) {
			if (type.getCode().equalsIgnoreCase(codeString)) {
				return type;
			}
		}
		return null;
	}
}
