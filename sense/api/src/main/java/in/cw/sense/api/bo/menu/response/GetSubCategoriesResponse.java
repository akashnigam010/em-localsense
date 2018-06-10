package in.cw.sense.api.bo.menu.response;

import java.util.ArrayList;
import java.util.List;

import in.cw.sense.api.bo.menu.dto.SubCategoryDto;
import in.cw.sense.api.bo.response.GenericResponse;

@SuppressWarnings("rawtypes")
public class GetSubCategoriesResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<SubCategoryDto> subCategories;

	public List<SubCategoryDto> getSubCategories() {
		if (this.subCategories == null) {
			this.subCategories = new ArrayList<>();
		}
		return subCategories;
	}

	public void setSubCategories(List<SubCategoryDto> subCategories) {
		this.subCategories = subCategories;
	}
}
