package in.cw.sense.api.bo.bill.entity;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Field;

import in.cw.sense.api.bo.menu.type.MenuType;

public class OrderUnit {
	@Field("itemId")
	private Integer itemId;
	@Field("itemName")
	private String itemName;
	@Field("quantity")
	private Integer quantity;
	@Field("price")
	private BigDecimal price;
	@Field("type")
	private MenuType type;
	@Field("value")
	private BigDecimal value;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public MenuType getType() {
		return type;
	}

	public void setType(MenuType type) {
		this.type = type;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}
