package in.cw.sense.api.bo.menu.response;

import in.cw.sense.api.bo.menu.dto.CategoryDto;
import in.cw.sense.api.bo.response.GenericResponse;

@SuppressWarnings("rawtypes")
public class CategoryResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private CategoryDto category;

	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto category) {
		this.category = category;
	}

}
