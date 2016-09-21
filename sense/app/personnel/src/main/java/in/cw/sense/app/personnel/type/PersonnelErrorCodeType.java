package in.cw.sense.app.personnel.type;

import cwf.helper.BusinessErrorCode;

public enum PersonnelErrorCodeType implements BusinessErrorCode {
	PIN_ALREADY_EXISTS(10101), 
	ACCESS_CODE_NULL_OR_EMPTY(10102),
	PIN_CODE_LENGTH_ERROR(10103),
	PERSONNEL_DETAILS_MISSING(10104),
	MISSING_ID_ERROR(10105),
	PERSONNEL_DETAILS_NOT_FOUND(10106),
	ACCESS_CODE_LENGTH_ERROR(10107), 
	ENCRYPT_ALGO_NOT_FOUND(10802);

	private Integer value;

	PersonnelErrorCodeType(Integer value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
