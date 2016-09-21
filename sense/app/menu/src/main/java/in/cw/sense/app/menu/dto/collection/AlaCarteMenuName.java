package in.cw.sense.app.menu.dto.collection;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "menu_names")
public class AlaCarteMenuName extends MenuNames {
	@Field("categories")
	private List<MenuCategory> categories;

	public List<MenuCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<MenuCategory> categories) {
		if(categories != null) {
			this.categories = categories;
		} else {
			categories = new ArrayList<>();
		}
	}

	@Override
	public String toString() {
		return "AlaCarteMenuNames [categories=" + categories + "]";
	}
}
