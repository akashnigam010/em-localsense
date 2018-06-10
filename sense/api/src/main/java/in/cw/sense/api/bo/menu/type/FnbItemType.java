package in.cw.sense.api.bo.menu.type;

public enum FnbItemType {
	VEG("VEG", "Vegetarian"), 
	NONVEG("NONVEG", "Non Vegetarian"), 
	BEVERAGE("BEVERAGE", "Beverage");

	private String code;
	private String description;

	private FnbItemType(String code, String description) {
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

	public static FnbItemType getFnbItemType(String codeString) {
		for (FnbItemType type : FnbItemType.values()) {
			if (type.getCode().equalsIgnoreCase(codeString)) {
				return type;
			}
		}
		return null;

	}
}
