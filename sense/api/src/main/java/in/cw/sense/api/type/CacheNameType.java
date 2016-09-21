package in.cw.sense.api.type;

public enum CacheNameType {
	LOCAL_MENU_CACHE("menu-cache");

	private String cacheName;

	CacheNameType(String cacheName) {
		this.cacheName = cacheName;
	}
	
	public String getCacheName() {
		return cacheName;
	}
}
