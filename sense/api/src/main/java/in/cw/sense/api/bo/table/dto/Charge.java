package in.cw.sense.api.bo.table.dto;

public class Charge {
	private String name;
	private RateValue fnb;
	private RateValue liquor;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RateValue getFnb() {
		return fnb;
	}

	public void setFnb(RateValue fnb) {
		this.fnb = fnb;
	}

	public RateValue getLiquor() {
		return liquor;
	}

	public void setLiquor(RateValue liquor) {
		this.liquor = liquor;
	}
}
