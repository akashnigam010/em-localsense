package in.cw.sense.api.bo.bill.type;

public enum BillStatusType {
	UNSETTLED("UNSETTLED"), SETTLED("SETTLED"), CANCELLED("CANCELLED");

	private String status;

	private BillStatusType(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public static BillStatusType getBillStatusByValue(String status) {
		for (BillStatusType type : BillStatusType.values()) {
			if (type.getStatus().equals(status)) {
				return type;
			}
		}
		return null;
	}

	public static Boolean contains(String status) {
		for (BillStatusType type : BillStatusType.values()) {
			if (type.getStatus().equalsIgnoreCase(status)) {
				return Boolean.TRUE;
			}
		}

		return Boolean.FALSE;
	}
}
