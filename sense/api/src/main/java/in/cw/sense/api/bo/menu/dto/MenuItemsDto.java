package in.cw.sense.api.bo.menu.dto;

import java.math.BigDecimal;

public class MenuItemsDto {
	private Integer id;
	private Integer menuId;
	private Integer categoryId;
	private String name;
	private BigDecimal price;
	private String currency;
	private Boolean isLiquor;
	private Boolean isVeg;
	private String desc;
	private Integer servesFor;
	private String image;
	private Boolean available;
	private Integer prepTime;
	private Integer spicyLevel;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Boolean getIsLiquor() {
		return isLiquor;
	}

	public void setIsLiquor(Boolean isLiqour) {
		this.isLiquor = isLiqour;
	}

	public Boolean getIsVeg() {
		return isVeg;
	}

	public void setIsVeg(Boolean isVeg) {
		this.isVeg = isVeg;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getServesFor() {
		return servesFor;
	}

	public void setServesFor(Integer servesFor) {
		this.servesFor = servesFor;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Integer getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(Integer prepTime) {
		this.prepTime = prepTime;
	}

	public Integer getSpicyLevel() {
		return spicyLevel;
	}

	public void setSpicyLevel(Integer spicyLevel) {
		this.spicyLevel = spicyLevel;
	}
}
