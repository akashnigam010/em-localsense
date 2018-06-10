package in.cw.sense.api.bo.restaurant.type;

public enum BarInfoType {
	NOTAVAILABLE(1, "NOT AVAILABLE"), AVAILABLE(2, "AVAILABLE");

	private int code;
	private String description;

	private BarInfoType(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static BarInfoType getBarInfoByCode(int code) {
		for (BarInfoType type : BarInfoType.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		return null;
	}
	
	public Boolean isBarAvailable() {
		if (this == AVAILABLE) {
			return true;
		} else {
			return false;
		}
	}

}
