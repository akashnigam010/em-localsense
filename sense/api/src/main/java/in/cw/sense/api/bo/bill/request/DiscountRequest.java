package in.cw.sense.api.bo.bill.request;

import java.math.BigDecimal;

import in.cw.sense.api.bo.menu.type.DiscountType;
import in.cw.sense.api.bo.menu.type.MenuType;

public class DiscountRequest {
	private Integer billId;
	private MenuType menuType;
	private DiscountType discountType;
	private BigDecimal value;

	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public MenuType getMenuType() {
		return menuType;
	}

	public void setMenuType(MenuType menuType) {
		this.menuType = menuType;
	}

	public DiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}
