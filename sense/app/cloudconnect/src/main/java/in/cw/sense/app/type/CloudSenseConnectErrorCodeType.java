package in.cw.sense.app.type;

import cwf.helper.BusinessErrorCode;

public enum CloudSenseConnectErrorCodeType implements BusinessErrorCode {
	SESSION_NOT_OPEN(10008);

	private int code;

	CloudSenseConnectErrorCodeType(int code) {
		this.code = code;
	}

	@Override
	public Integer getBusinessErrorCode() {
		return code;
	}
}
