package in.cw.sense.api.bo.bill.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public class DiscountEntity {
	@Field("fnb")
	private TypeValueEntity fnb;
	
	@Field("liquor")
	private TypeValueEntity liquor;

	public TypeValueEntity getFnb() {
		return fnb;
	}

	public void setFnb(TypeValueEntity fnb) {
		this.fnb = fnb;
	}

	public TypeValueEntity getLiquor() {
		return liquor;
	}

	public void setLiquor(TypeValueEntity liquor) {
		this.liquor = liquor;
	}
}
