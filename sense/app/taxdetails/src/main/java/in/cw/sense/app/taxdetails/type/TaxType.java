package in.cw.sense.app.taxdetails.type;

import org.apache.commons.lang3.StringUtils;

public enum TaxType {
	INTERNAL("I"),
	EXTERNAL("E");
	
	private String type;
	
	public String getType() {
		return type;
	}
	
	TaxType(String type) {
		this.type = type;
	}
	
	public static String getTaxCodeByType(String type) {
		for (TaxType taxType : TaxType.values()) {
			if (StringUtils.equalsIgnoreCase(taxType.getType(), type)) {
				return taxType.getType();
			}
		}

		return null;
	}
	
	public static TaxType getTaxByType(String type) {
		for (TaxType taxType : TaxType.values()) {
			if (StringUtils.equalsIgnoreCase(taxType.getType(), type)) {
				return taxType;
			}
		}

		return null;
	}
}
