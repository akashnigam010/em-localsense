package in.cw.sense.app.setting.type;

import cwf.helper.BusinessErrorCode;

public enum SettingErrorCodeType implements BusinessErrorCode{
	CLOUD_CONNECT_DETAILS_NOT_AVAILABLE(10800),
	INVALID_DATA(10801),
	ENCRYPT_ALGO_NOT_FOUND(10802),
	REQUEST_VALIDATION_FAILURE(90002);

	private int code;

	SettingErrorCodeType(int code) {
		this.code = code;
	}

	@Override
	public Integer getBusinessErrorCode() {
		return code;
	}
}
