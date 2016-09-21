package in.cw.sense.api.bo.bill.entity;

import java.math.BigDecimal;

import in.cw.sense.api.bo.menu.type.DiscountType;

public class TypeValueEntity {
	private DiscountType type;
	private BigDecimal value;
	private BigDecimal amount;

	public DiscountType getType() {
		return type;
	}

	public void setType(DiscountType type) {
		this.type = type;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
