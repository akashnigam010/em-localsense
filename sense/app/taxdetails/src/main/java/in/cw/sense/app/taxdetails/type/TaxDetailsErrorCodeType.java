package in.cw.sense.app.taxdetails.type;

import cwf.helper.BusinessErrorCode;

public enum TaxDetailsErrorCodeType  implements BusinessErrorCode {
	TAX_NAME_CANT_BE_EMPTY(10500),
	INCORRECT_TAX_TYPE(10501),
	FOOD_AND_LIQUOR_TAX_CANT_BE_EMPTY(10502),
	GENERIC_ERROR(10600),
	TAX_ID_NOT_NULL(10503),
	TAX_ID_NULL(10504),
	TAX_DETAILS_DOESNOT_EXIST(10505);

	private int value;

	TaxDetailsErrorCodeType(int value) {
		this.value = value;
	}

	@Override
	public Integer getBusinessErrorCode() {
		return value;
	}

}
