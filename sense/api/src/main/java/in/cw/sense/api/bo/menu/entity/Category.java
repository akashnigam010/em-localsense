package in.cw.sense.api.bo.menu.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import in.cw.sense.api.bo.menu.type.MenuType;

@Document(collection = "category")
public class Category {
	@Id
	@Field
	private Integer id;
	@Field("type")
	private MenuType type;
	@Field("name")
	private String name;
	
	public Category(Integer id, MenuType type, String name) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MenuType getType() {
		return type;
	}

	public void setType(MenuType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
