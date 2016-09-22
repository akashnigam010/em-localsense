package in.cw.sense.app.menu.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.cw.sense.api.bo.menu.dto.BarItemDto;
import in.cw.sense.api.bo.menu.dto.CategoryDto;
import in.cw.sense.api.bo.menu.dto.FnbItemDto;
import in.cw.sense.api.bo.menu.dto.ItemDto;
import in.cw.sense.api.bo.menu.dto.SubCategoryDto;
import in.cw.sense.api.bo.menu.entity.BarItem;
import in.cw.sense.api.bo.menu.entity.Category;
import in.cw.sense.api.bo.menu.entity.FnbItem;
import in.cw.sense.api.bo.menu.entity.Item;
import in.cw.sense.api.bo.menu.entity.SubCategory;
import in.cw.sense.api.bo.menu.request.ItemRequest;
import in.cw.sense.api.bo.menu.type.FnbItemType;
import in.cw.sense.api.bo.menu.type.MenuType;

@Component
public class MenuMapper {
	/**
	 * Parent Item - Only Id, Description and Image Path could be null<br>
	 * Fnb Item - Spicy Level could be null
	 * 
	 * @param request
	 * @return
	 */
	public Item mapRequestToItemEntity(ItemRequest request) {
		MenuType type = MenuType.getType(request.getType());
		if (type == MenuType.FNB) {
			FnbItem fnbItem = new FnbItem();
			fnbItem.setType(type);
			fnbItem = (FnbItem) mapCommonItemEntityProperties(fnbItem, request);
			FnbItemType fnbItemType = FnbItemType.getFnbItemType(request.getFnbItemType());
			fnbItem.setFnbItemType(fnbItemType);
			fnbItem.setServesFor(request.getServesFor());
			fnbItem.setPreparationTime(request.getPreparationTime());
			if (request.getSpicyLevel() != null) {
				fnbItem.setSpicyLevel(request.getSpicyLevel());
			}
			return fnbItem;
		} else {
			BarItem barItem = new BarItem();
			barItem.setType(type);
			barItem = (BarItem) mapCommonItemEntityProperties(barItem, request);
			return barItem;
		}
	}

	private Item mapCommonItemEntityProperties(Item item, ItemRequest request) {
		if (request.getId() != null) {
			item.setId(request.getId());
		}
		item.setCategoryId(request.getCategoryId());
		if (request.getSubCategoryId() != null) {
			item.setSubCategoryId(request.getSubCategoryId());
		}
		item.setName(request.getName());
		item.setPrice(request.getPrice());
		item.setCurrency(request.getCurrency());
		if (StringUtils.isNotEmpty(request.getDescription())) {
			item.setDescription(request.getDescription());
		}
		if (StringUtils.isNotEmpty(request.getImage())) {
			item.setImage(request.getImage());
		}
		item.setAvailable(request.getAvailable());
		return item;
	}

	private ItemDto mapCommonItemDtoProperties(ItemDto item, Item itemEntity) {
		item.setId(itemEntity.getId());
		item.setCategoryId(itemEntity.getCategoryId());
		if (itemEntity.getSubCategoryId() != null) {
			item.setSubCategoryId(itemEntity.getSubCategoryId());
		}
		item.setType(itemEntity.getType().toString());
		item.setName(itemEntity.getName());
		item.setPrice(itemEntity.getPrice());
		item.setCurrency(itemEntity.getCurrency());
		if (StringUtils.isNotEmpty(itemEntity.getDescription())) {
			item.setDescription(itemEntity.getDescription());
		}
		if (StringUtils.isNotEmpty(itemEntity.getImage())) {
			item.setImage(itemEntity.getImage());
		}
		item.setAvailable(itemEntity.getAvailable());
		return item;
	}

	public List<ItemDto> mapItemEntities(List<Item> itemEntites) {
		List<ItemDto> items = new ArrayList<>();
		ItemDto item = null;
		for (Item itemEntity : itemEntites) {
			item = mapItemEntity(itemEntity);
			items.add(item);
		}
		return items;
	}

	/**
	 * Parent Item - Only Description and Image Path could be null<br>
	 * Fnb Item - Only Spicy level could be null
	 * 
	 * @param itemEntity
	 * @return
	 */
	public ItemDto mapItemEntity(Item itemEntity) {
		if (itemEntity instanceof FnbItem) {
			FnbItem fnbItemEntity = (FnbItem) itemEntity;
			FnbItemDto item = new FnbItemDto();
			item = (FnbItemDto) mapCommonItemDtoProperties(item, fnbItemEntity);
			item.setFnbItemType(fnbItemEntity.getFnbItemType());
			item.setServesFor(fnbItemEntity.getServesFor());
			item.setPreparationTime(fnbItemEntity.getPreparationTime());
			if (fnbItemEntity.getSpicyLevel() != null) {
				item.setSpicyLevel(fnbItemEntity.getSpicyLevel());
			}
			return item;
		} else {
			BarItem barItemEntity = (BarItem) itemEntity;
			BarItemDto item = new BarItemDto();
			item = (BarItemDto) mapCommonItemDtoProperties(item, barItemEntity);
			return item;
		}
	}

	public List<CategoryDto> mapCategoryEntites(List<Category> categoryEntities) {
		List<CategoryDto> categories = new ArrayList<>();
		CategoryDto category = null;
		for (Category categoryEntity : categoryEntities) {
			category = mapCategoryEntity(categoryEntity);
			categories.add(category);
		}
		return categories;
	}

	public CategoryDto mapCategoryEntity(Category categoryEntity) {
		CategoryDto category = new CategoryDto();
		category.setId(categoryEntity.getId());
		category.setMenuType(categoryEntity.getType());
		category.setName(categoryEntity.getName());
		return category;
	}
	
	public List<SubCategoryDto> mapSubCategoryEntites(List<SubCategory> subCategoryEntities) {
		List<SubCategoryDto> subCategories = new ArrayList<>();
		SubCategoryDto subCategory = null;
		for (SubCategory subCategoryEntity : subCategoryEntities) {
			subCategory = mapSubCategoryEntity(subCategoryEntity);
			subCategories.add(subCategory);
		}
		return subCategories;
	}

	public SubCategoryDto mapSubCategoryEntity(SubCategory subCategoryEntity) {
		SubCategoryDto category = new SubCategoryDto();
		category.setId(subCategoryEntity.getId());
		category.setCategoryId(subCategoryEntity.getCategoryId());
		category.setMenuType(subCategoryEntity.getType());
		category.setName(subCategoryEntity.getName());
		return category;
	}
}
