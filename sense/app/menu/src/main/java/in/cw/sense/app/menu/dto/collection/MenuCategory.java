package in.cw.sense.app.menu.dto.collection;

import org.springframework.data.mongodb.core.mapping.Field;

public class MenuCategory {
	private Integer id;
	@Field("name")
	private String name;

	public MenuCategory(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

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
}
