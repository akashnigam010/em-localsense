package in.cw.sense.app.menu.dto.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "menu_names")
public abstract class MenuNames {
	@Id
	@Field
	private Integer id;
	@Field("name")
	private String name;
	@Field("type")
	private String type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "MenuName [id=" + id + ", name=" + name + ", menuType=" + type + "]";
	}
}
