package in.cw.sense.api.bo.menu.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import in.cw.sense.api.bo.menu.type.MenuType;
import in.cw.sense.api.type.CurrencyType;

@Document(collection = "item")
public abstract class Item {
	@Id
	@Field
	private Integer id;
	@Field("categoryId")
	private Integer categoryId;
	@Field("subCategoryId")
	private Integer subCategoryId;
	@Field("type")
	private MenuType type;
	@Field("name")
	private String name;
	@Field("price")
	private BigDecimal price;
	@Field("currency")
	private CurrencyType currency;
	@Field("description")
	private String description;
	@Field("image")
	private String image;
	@Field("available")
	private Boolean available;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
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

	public MenuType getType() {
		return type;
	}

	public void setType(MenuType type) {
		this.type = type;
	}
	
	public Boolean getIsLiquor() {
		if (MenuType.BAR == type) {
			return true;
		}
		return false;
	}
}
