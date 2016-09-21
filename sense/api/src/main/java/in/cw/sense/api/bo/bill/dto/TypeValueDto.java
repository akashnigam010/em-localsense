package in.cw.sense.api.bo.bill.dto;

import java.math.BigDecimal;

import in.cw.sense.api.bo.menu.type.DiscountType;

public class TypeValueDto {
	private DiscountType discountType;
	private BigDecimal value;
	private BigDecimal amount;

	public DiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(DiscountType type) {
		this.discountType = type;
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
