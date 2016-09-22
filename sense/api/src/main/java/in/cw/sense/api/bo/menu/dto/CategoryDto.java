package in.cw.sense.api.bo.menu.dto;

import in.cw.sense.api.bo.menu.type.MenuType;

public class CategoryDto {
	private Integer id;
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
}
