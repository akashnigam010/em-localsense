package in.cw.sense.app.service.validation;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.menu.request.CategoryRequest;
import in.cw.sense.api.bo.menu.request.ItemRequest;
import in.cw.sense.api.bo.menu.request.SubCategoryRequest;
import in.cw.sense.api.bo.menu.type.FnbItemType;
import in.cw.sense.api.bo.menu.type.MenuType;
import in.cw.sense.app.menu.type.MenuErrorCodeType;

@Component
public class MenuValidator {
	private static final Logger LOG = Logger.getLogger(MenuValidator.class);
	
	public void validateGetItems(ItemRequest request) throws BusinessException {
		MenuType type = MenuType.getType(request.getType());
		if (type == null) {
			LOG.error("Menu Type is not properly populated in ItemRequest");
			throw new BusinessException(MenuErrorCodeType.REQUEST_VALIDATION_FAILURE);
		}
	}

	public void validateItem(ItemRequest request) throws BusinessException {
		MenuType type = MenuType.getType(request.getType());
		if (type == null || request.getCategoryId() == null || StringUtils.isEmpty(request.getName()) || 
				request.getPrice() == null || request.getCurrency() == null || request.getAvailable() == null) {
			LOG.error("One or more fields are not properly populated in ItemRequest");
			throw new BusinessException(MenuErrorCodeType.REQUEST_VALIDATION_FAILURE);
		}
		if (type == MenuType.FNB) {
			// fnb item specific validations
			FnbItemType fnbItemType = FnbItemType.getFnbItemType(request.getFnbItemType());
			if (fnbItemType == null || request.getServesFor() == null || request.getPreparationTime() == null) {
				LOG.error("One or more fields are not properly populated in ItemRequest");
				throw new BusinessException(MenuErrorCodeType.REQUEST_VALIDATION_FAILURE);
			}
		} else {
			// bar item specific validations
		}

	}

	public void validateDeleteItem(ItemRequest request) throws BusinessException {
		if (request.getId() == null) {
			LOG.error("ID field is not properly populated in ItemRequest");
			throw new BusinessException(MenuErrorCodeType.REQUEST_VALIDATION_FAILURE);
		}
	}

	public void validateGetCategories(CategoryRequest request) throws BusinessException {
		MenuType type = MenuType.getType(request.getType());
		if (type == null) {
			LOG.error("Menu Type is not populated in CategoryRequest");
			throw new BusinessException(MenuErrorCodeType.REQUEST_VALIDATION_FAILURE);
		}
	}

	public void validateCategory(CategoryRequest request) throws BusinessException {
		MenuType type = MenuType.getType(request.getType());
		if (type == null || StringUtils.isEmpty(request.getName())) {
			LOG.error("Menu Type is not populated in CategoryRequest");
			throw new BusinessException(MenuErrorCodeType.REQUEST_VALIDATION_FAILURE);
		}
	}

	public void validateDeleteCategory(CategoryRequest request) throws BusinessException {
		if (request.getId() == null) {
			LOG.error("Category ID is not populated in CategoryRequest");
			throw new BusinessException(MenuErrorCodeType.REQUEST_VALIDATION_FAILURE);
		}
	}

	public void validateGetSubCategories(SubCategoryRequest request) throws BusinessException {
		MenuType type = MenuType.getType(request.getType());
		if (type == null) {
			LOG.error("Menu Type is not populated in SubCategoryRequest");
			throw new BusinessException(MenuErrorCodeType.REQUEST_VALIDATION_FAILURE);
		}
	}

	public void validateSubCategory(SubCategoryRequest request) throws BusinessException {
		MenuType type = MenuType.getType(request.getType());
		if (StringUtils.isEmpty(request.getName()) || request.getCategoryId() == null || type == null) {
			LOG.error("One or more fields are not populated in SubCategoryRequest");
			throw new BusinessException(MenuErrorCodeType.REQUEST_VALIDATION_FAILURE);
		}
	}

	public void validateDeleteSubCategory(SubCategoryRequest request) throws BusinessException {
		if (request.getId() == null) {
			LOG.error("Sub category ID is not populated in SubCategoryRequest");
			throw new BusinessException(MenuErrorCodeType.REQUEST_VALIDATION_FAILURE);
		}
	}

}
