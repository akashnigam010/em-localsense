package in.cw.sense.app.report.type;

import cwf.helper.BusinessErrorCode;

public enum ReportErrorCodeType implements BusinessErrorCode {
	START_DATE_OR_END_DATE_MISSING(10706),
	REQUEST_VALIDATION_FAILURE(90002);

	private int value;

	ReportErrorCodeType(int value) {
		this.value = value;
	}

	@Override
	public Integer getBusinessErrorCode() {
		return value;
	}
}
