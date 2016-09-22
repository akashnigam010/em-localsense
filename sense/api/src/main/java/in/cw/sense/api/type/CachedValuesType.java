package in.cw.sense.api.type;

public enum CachedValuesType {
	MENU_ITEMS("menu_items");

	private String cachedValues;

	CachedValuesType(String cachedValues) {
		this.cachedValues = cachedValues;
	}

	public String getCachedValues() {
		return cachedValues;
	}
}
