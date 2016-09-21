package in.cw.sense.api.bo.menu.type;

public enum MenuType {
	FNB("FNB"), BAR("BAR");

	private String type;

	private MenuType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static MenuType getType(String typeString) {
		for (MenuType type : MenuType.values()) {
			if (type.getType().equalsIgnoreCase(typeString)) {
				return type;
			}
		}
		return null;
	}
}
