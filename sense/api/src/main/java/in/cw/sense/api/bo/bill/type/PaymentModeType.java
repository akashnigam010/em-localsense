package in.cw.sense.api.bo.bill.type;

public enum PaymentModeType {
	CASH(1, "CASH"), 
	CARD(2, "CARD"), 
	COMPLIMENTARY(3, "COMPLIMENTARY"), 
	COUPON(4, "COUPON"),
	CANCELLED(5, "CANCELLED");

	private int code;
	private String type;

	private PaymentModeType(int code, String type) {
		this.code = code;
		this.type = type;
	}

	public int getCode() {
		return code;
	}

	public String getType() {
		return type;
	}

	public static PaymentModeType getPaymentModeByCode(int paymentMode) {
		for (PaymentModeType type : PaymentModeType.values()) {
			if (type.getCode() == paymentMode) {
				return type;
			}
		}

		return null;
	}
	
	public static Boolean contains(int paymentMode) {
		for (PaymentModeType type : PaymentModeType.values()) {
			if (type.getCode() == paymentMode) {
				return Boolean.TRUE;
			}
		}

		return Boolean.FALSE;
	}
}
