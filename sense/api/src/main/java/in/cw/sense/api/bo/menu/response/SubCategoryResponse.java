package in.cw.sense.api.bo.menu.response;

import in.cw.sense.api.bo.menu.dto.SubCategoryDto;
import in.cw.sense.api.bo.response.GenericResponse;

@SuppressWarnings("rawtypes")
public class SubCategoryResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private SubCategoryDto subCategory;

	public SubCategoryDto getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategoryDto subCategory) {
		this.subCategory = subCategory;
	}

}
