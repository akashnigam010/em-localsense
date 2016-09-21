package in.cw.sense.api.bo.menu.request;

import java.math.BigDecimal;

import in.cw.sense.api.type.CurrencyType;

public class ItemRequest {
	private Integer id;
	private String type;
	private Integer categoryId;
	private Integer subCategoryId;
	private String name;
	private BigDecimal price;
	private CurrencyType currency;
	private String description;
	private String image;
	private Boolean available;
	private String fnbItemType;
	private Integer servesFor;
	private Integer preparationTime;
	private Integer spicyLevel;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public CurrencyType getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyType currency) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getFnbItemType() {
		return fnbItemType;
	}

	public void setFnbItemType(String fnbItemType) {
		this.fnbItemType = fnbItemType;
	}

	public Integer getServesFor() {
		return servesFor;
	}

	public void setServesFor(Integer servesFor) {
		this.servesFor = servesFor;
	}

	public Integer getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(Integer preparationTime) {
		this.preparationTime = preparationTime;
	}

	public Integer getSpicyLevel() {
		return spicyLevel;
	}

	public void setSpicyLevel(Integer spicyLevel) {
		this.spicyLevel = spicyLevel;
	}

	public Integer getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
}
