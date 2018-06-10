package in.cw.sense.api.bo.menu.response;

import java.util.ArrayList;
import java.util.List;

import in.cw.sense.api.bo.menu.dto.CategoryDto;
import in.cw.sense.api.bo.response.GenericResponse;

@SuppressWarnings("rawtypes")
public class GetCategoriesResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<CategoryDto> categories;

	public List<CategoryDto> getCategories() {
		if (this.categories == null) {
			this.categories = new ArrayList<>();
		}
		return categories;
	}

	public void setCategories(List<CategoryDto> categories) {
		this.categories = categories;
	}
}
