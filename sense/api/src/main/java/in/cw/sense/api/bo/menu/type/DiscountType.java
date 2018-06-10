package in.cw.sense.api.bo.menu.type;

public enum DiscountType {
	PERCENT("PERCENT"), 
	DIRECT("DIRECT");

	private String type;

	private DiscountType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static DiscountType getType(String typeString) {
		for (DiscountType type : DiscountType.values()) {
			if (type.getType().equalsIgnoreCase(typeString)) {
				return type;
			}
		}
		return null;
	}
}
