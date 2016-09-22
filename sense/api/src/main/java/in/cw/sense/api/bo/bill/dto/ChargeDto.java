package in.cw.sense.api.bo.bill.dto;

public class ChargeDto {
	private String name;
	private RateValueDto fnb;
	private RateValueDto liquor;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RateValueDto getFnb() {
		return fnb;
	}

	public void setFnb(RateValueDto fnb) {
		this.fnb = fnb;
	}

	public RateValueDto getLiquor() {
		return liquor;
	}

	public void setLiquor(RateValueDto liquor) {
		this.liquor = liquor;
	}
}
