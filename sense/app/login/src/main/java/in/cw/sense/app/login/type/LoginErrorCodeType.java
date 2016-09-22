package in.cw.sense.app.login.type;

import cwf.helper.BusinessErrorCode;

public enum LoginErrorCodeType implements BusinessErrorCode {
	LOGIN_ERROR(10001), 
	USER_PIN_ACCESSCODE_DOESNOT_MATCH(10002), 
	PIN_NOT_FOUND(10003), 
	USER_DETAILS_NOT_FOUND(10004), 
	ACCESS_CODE_EMPTY(10005), 
	PIN_EMPTY(10006), 
	ENCRYPT_ALGO_NOT_FOUND(10802);

	private int value;

	LoginErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
