package in.cw.sense.api.bo.menu.dto;

import in.cw.sense.api.bo.menu.type.MenuType;

public class SubCategoryDto {
	private Integer id;
	private Integer categoryId;
	private MenuType menuType;
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MenuType getMenuType() {
		return menuType;
	}

	public void setMenuType(MenuType menuType) {
		this.menuType = menuType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
}
