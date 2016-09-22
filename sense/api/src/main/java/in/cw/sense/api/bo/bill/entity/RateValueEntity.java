package in.cw.sense.api.bo.bill.entity;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * This class file is used to explain the rate of TAX and value derived on
 * particular item
 */
public class RateValueEntity {
	@Field("rate")
	private BigDecimal rate;

	@Field("value")
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
