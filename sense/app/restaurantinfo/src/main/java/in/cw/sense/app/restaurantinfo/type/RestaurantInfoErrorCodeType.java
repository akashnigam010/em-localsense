package in.cw.sense.app.restaurantinfo.type;

import cwf.helper.BusinessErrorCode;

public enum RestaurantInfoErrorCodeType implements BusinessErrorCode {
	RESTAURANT_INFO_NOT_AVAILABLE(10201),
	INVALID_DATA(10202),
	REQUEST_VALIDATION_FAILURE(90002);

	private int code;

	RestaurantInfoErrorCodeType(int code) {
		this.code = code;
	}

	@Override
	public Integer getBusinessErrorCode() {
		return code;
	}
}
