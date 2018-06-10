package in.cw.sense.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cwf.helper.ResponseHelper;
import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.menu.request.CategoryRequest;
import in.cw.sense.api.bo.menu.request.ItemRequest;
import in.cw.sense.api.bo.menu.request.SubCategoryRequest;
import in.cw.sense.api.bo.menu.response.CategoryResponse;
import in.cw.sense.api.bo.menu.response.IdResponse;
import in.cw.sense.api.bo.menu.response.ItemResponse;
import in.cw.sense.api.bo.menu.response.SubCategoryResponse;
import in.cw.sense.app.menu.MenuDelegate;
import in.cw.sense.app.service.validation.MenuValidator;

@RestController
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('OWNER') or hasAuthority('MANAGER')")
@RequestMapping(value = "/menu")
public class MenuService {
	@Autowired
	MenuDelegate delegate;
	@Autowired
	ResponseHelper helper;
	@Autowired
	MenuValidator menuValidator;

	@RequestMapping(value = "/addEditItem", method = RequestMethod.POST, headers = "Accept=application/json")
	public ItemResponse addEditItem(@RequestBody ItemRequest request) {
		ItemResponse response = new ItemResponse();
		try {
			menuValidator.validateItem(request);
			response = delegate.addEditItem(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/deleteItem", method = RequestMethod.POST, headers = "Accept=application/json")
	public IdResponse deleteItem(@RequestBody ItemRequest request) {
		IdResponse response = new IdResponse();
		try {
			menuValidator.validateDeleteItem(request);
			response = delegate.deleteItem(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/addEditCategory", method = RequestMethod.POST, headers = "Accept=application/json")
	public CategoryResponse addEditCategory(@RequestBody CategoryRequest request) {
		CategoryResponse response = new CategoryResponse();
		try {
			menuValidator.validateCategory(request);
			response = delegate.addEditCategory(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/deleteCategory", method = RequestMethod.POST, headers = "Accept=application/json")
	public IdResponse deleteCategory(@RequestBody CategoryRequest request) {
		IdResponse response = new IdResponse();
		try {
			menuValidator.validateDeleteCategory(request);
			response = delegate.deleteCategory(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/addEditSubCategory", method = RequestMethod.POST, headers = "Accept=application/json")
	public SubCategoryResponse addEditSubCategory(@RequestBody SubCategoryRequest request) {
		SubCategoryResponse response = new SubCategoryResponse();
		try {
			menuValidator.validateSubCategory(request);
			response = delegate.addEditSubCategory(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/deleteSubCategory", method = RequestMethod.POST, headers = "Accept=application/json")
	public IdResponse deleteSubCategory(@RequestBody SubCategoryRequest request) {
		IdResponse response = new IdResponse();
		try {
			menuValidator.validateDeleteSubCategory(request);
			response = delegate.deleteSubCategory(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
