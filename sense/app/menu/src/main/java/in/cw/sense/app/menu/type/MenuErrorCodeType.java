package in.cw.sense.app.menu.type;

import cwf.helper.BusinessErrorCode;

public enum MenuErrorCodeType implements BusinessErrorCode {
	MENU_NOT_FOUND(10401),
	SUB_MENU_NOT_FOUND(10402),
	MENU_CATEGORY_NOT_FOUND(10403),
	MENU_ITEM_NOT_FOUND(10404),
	MENU_NAME_ALREADY_EXISTS(10405),
	REQUEST_VALIDATION_FAILURE(90002);

	private int value;

	MenuErrorCodeType(int value) {
		this.value = value;
	}

	@Override
	public Integer getBusinessErrorCode() {
		return value;
	}
}
