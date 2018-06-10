package in.cw.sense.api.bo.bill.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public class ChargeEntity {
	@Field("name")
	private String name;

	@Field("fnb")
	private RateValueEntity fnb;

	@Field("liquor")
	private RateValueEntity liquor;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RateValueEntity getFnb() {
		return fnb;
	}

	public void setFnb(RateValueEntity fnb) {
		this.fnb = fnb;
	}

	public RateValueEntity getLiquor() {
		return liquor;
	}

	public void setLiquor(RateValueEntity liquor) {
		this.liquor = liquor;
	}
}
