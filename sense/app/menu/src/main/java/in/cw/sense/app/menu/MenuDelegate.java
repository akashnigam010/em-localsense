package in.cw.sense.app.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.menu.dto.CategoryDto;
import in.cw.sense.api.bo.menu.dto.ItemDto;
import in.cw.sense.api.bo.menu.dto.SubCategoryDto;
import in.cw.sense.api.bo.menu.entity.Category;
import in.cw.sense.api.bo.menu.entity.Item;
import in.cw.sense.api.bo.menu.entity.SubCategory;
import in.cw.sense.api.bo.menu.request.CategoryRequest;
import in.cw.sense.api.bo.menu.request.ItemRequest;
import in.cw.sense.api.bo.menu.request.SubCategoryRequest;
import in.cw.sense.api.bo.menu.response.CategoryResponse;
import in.cw.sense.api.bo.menu.response.GetCategoriesResponse;
import in.cw.sense.api.bo.menu.response.GetItemsResponse;
import in.cw.sense.api.bo.menu.response.GetSubCategoriesResponse;
import in.cw.sense.api.bo.menu.response.IdResponse;
import in.cw.sense.api.bo.menu.response.ItemResponse;
import in.cw.sense.api.bo.menu.response.SubCategoryResponse;
import in.cw.sense.api.bo.menu.type.MenuType;
import in.cw.sense.app.menu.mapper.MenuMapper;

@Service
public class MenuDelegate {
	@Autowired MenuDao dao;
	@Autowired MenuMapper mapper;

	public GetItemsResponse getItems(MenuType type) throws BusinessException {
		GetItemsResponse response = new GetItemsResponse();
		List<Item> itemEntities = dao.getItems(type);
		List<ItemDto> items = mapper.mapItemEntities(itemEntities);
		response.setItems(items);
		return response;
	}
	
	public ItemResponse addEditItem(ItemRequest request) throws BusinessException {
		ItemResponse response = new ItemResponse();
		Item item = dao.addEditItem(request);
		ItemDto itemDto = mapper.mapItemEntity(item);
		response.setItem(itemDto);
		return response;
	}
	
	public IdResponse deleteItem(ItemRequest request) throws BusinessException {
		IdResponse response = new IdResponse();
		Integer itemId = dao.deleteItem(request);
		response.setId(itemId);
		return response;
	}
	
	/**
	 * @param type - pass null to get all categories
	 * @return
	 * @throws BusinessException
	 */
	public GetCategoriesResponse getCategories(MenuType type) throws BusinessException {
		GetCategoriesResponse response = new GetCategoriesResponse();
		List<Category> categoryEntities = dao.getCategories(type);
		List<CategoryDto> categories = mapper.mapCategoryEntites(categoryEntities);
		response.setCategories(categories);
		return response;
	}
	
	public CategoryResponse addEditCategory(CategoryRequest request) throws BusinessException {
		CategoryResponse response = new CategoryResponse();
		Category category = dao.addEditCategory(request);
		CategoryDto categoryDto = mapper.mapCategoryEntity(category);
		response.setCategory(categoryDto);
		return response;
	}
	
	public IdResponse deleteCategory(CategoryRequest request) throws BusinessException {
		IdResponse response = new IdResponse();
		Integer categoryId = dao.deleteCategory(request);
		response.setId(categoryId);
		return response;
	}
	
	/**
	 * @param type - pass null to get all categories
	 * @return
	 * @throws BusinessException
	 */
	public GetSubCategoriesResponse getSubCategories(MenuType type) throws BusinessException {
		GetSubCategoriesResponse response = new GetSubCategoriesResponse();
		List<SubCategory> subCategoryEntities = dao.getSubCategories(type);
		List<SubCategoryDto> subCategories = mapper.mapSubCategoryEntites(subCategoryEntities);
		response.setSubCategories(subCategories);
		return response;
	}
	
	public SubCategoryResponse addEditSubCategory(SubCategoryRequest request) throws BusinessException {
		SubCategoryResponse response = new SubCategoryResponse();
		SubCategory subCategory = dao.addEditSubCategory(request);
		SubCategoryDto subCategoryDto = mapper.mapSubCategoryEntity(subCategory);
		response.setSubCategory(subCategoryDto);
		return response;
	}
	
	public IdResponse deleteSubCategory(SubCategoryRequest request) throws BusinessException {
		IdResponse response = new IdResponse();
		Integer categoryId = dao.deleteSubCategory(request);
		response.setId(categoryId);
		return response;
	}
}
