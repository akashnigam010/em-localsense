package in.cw.sense.api.bo.bill.dto;

import java.math.BigDecimal;

public class RateValueDto {
	private BigDecimal rate;
	private BigDecimal value;

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}
