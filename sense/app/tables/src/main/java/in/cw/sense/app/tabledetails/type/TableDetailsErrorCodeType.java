package in.cw.sense.app.tabledetails.type;

import cwf.helper.BusinessErrorCode;

public enum TableDetailsErrorCodeType implements BusinessErrorCode {
	TABLE_NOT_FOUND(10301), 
	SEATING_AREA_NOT_FOUND(10302), 
	SEATING_AREA_WITH_TABLES_CANT_BE_DELETED(10304), 
	CANT_DELETE_TABLE_WITH_ORDERS_ASSIGNED(10305);

	private int code;

	TableDetailsErrorCodeType(int code) {
		this.code = code;
	}

	@Override
	public Integer getBusinessErrorCode() {
		return code;
	}
}
