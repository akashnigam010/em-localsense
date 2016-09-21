package in.cw.sense.api.bo.support.type;

public enum EntityType {
	RESTAURANT("Restaurant"), SUPPORT_CENTER("Support Center");

	private String desc;

	private EntityType(String desc) {
		this.setDesc(desc);
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static EntityType getEntityType(String desc) {
		for (EntityType type : EntityType.values()) {
			if (type.getDesc().equalsIgnoreCase(desc)) {
				return type;
			}
		}
		return null;
	}
}
