package in.cw.sense.api.bo.bill.entity;

import java.math.BigDecimal;

public class TotalEntity {
	private String name;
	private BigDecimal fnb = BigDecimal.ZERO;
	private BigDecimal liquor = BigDecimal.ZERO;

	public BigDecimal getFnb() {
		return fnb;
	}

	public void setFnb(BigDecimal fnb) {
		this.fnb = fnb;
	}

	public BigDecimal getLiquor() {
		return liquor;
	}

	public void setLiquor(BigDecimal liquor) {
		this.liquor = liquor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
