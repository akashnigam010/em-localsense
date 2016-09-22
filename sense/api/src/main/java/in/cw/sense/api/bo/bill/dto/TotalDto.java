package in.cw.sense.api.bo.bill.dto;

import java.math.BigDecimal;

public class TotalDto {
	private BigDecimal fnb;
	private BigDecimal liquor;

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
}
