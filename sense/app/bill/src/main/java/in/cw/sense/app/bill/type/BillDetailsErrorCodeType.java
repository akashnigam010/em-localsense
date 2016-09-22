package in.cw.sense.app.bill.type;

import cwf.helper.BusinessErrorCode;

public enum BillDetailsErrorCodeType implements BusinessErrorCode {
	ORDERS_NOT_ASSIGNED_TO_TABLE(10700),
	UNABLE_TO_FETCH_TAX_DETAILS(10701), 
	BILL_DETAILS_NOT_FOUND(10702),
	BILL_TABLE_MISMATCH(10703),
	TABLE_ID_CANNOT_BE_EMPTY(10704),
	TABLE_DETAILS_NOT_FOUND(10301),
	DETAILS_NOT_POPULATED(10705),
	START_DATE_OR_END_DATE_MISSING(10706),
	BILL_ID_CANNOT_BE_EMPTY(10707),
	BAD_BILL_STATUS(10708),
	PAYMENT_MODE_NOT_FOUND(10709),
	BILL_SETTLED_EARLIER(10710),
	BILL_CANCELLED_EARLIER(10711),
	EMPTY_REASON_FOR_CANCELLATION(10712), 
	ASSOCIATED_KOT_NOT_FOUND(10713);

	private int code;

	BillDetailsErrorCodeType(int code) {
		this.code = code;
	}

	@Override
	public Integer getBusinessErrorCode() {
		return code;
	}
}
