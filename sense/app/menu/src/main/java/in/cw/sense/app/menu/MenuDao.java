package in.cw.sense.app.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import cwf.dbhelper.SenseContext;
import cwf.dbhelper.cache.AppCache;
import cwf.dbhelper.cache.Cache;
import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.menu.entity.Category;
import in.cw.sense.api.bo.menu.entity.Item;
import in.cw.sense.api.bo.menu.entity.SubCategory;
import in.cw.sense.api.bo.menu.request.CategoryRequest;
import in.cw.sense.api.bo.menu.request.ItemRequest;
import in.cw.sense.api.bo.menu.request.SubCategoryRequest;
import in.cw.sense.api.bo.menu.type.MenuType;
import in.cw.sense.api.type.CacheNameType;
import in.cw.sense.api.type.CachedValuesType;
import in.cw.sense.app.menu.mapper.MenuMapper;

@Repository
public class MenuDao {
	@Autowired MenuMapper mapper;
	@Autowired AppCache appCache;
	@Autowired SenseContext context;
	@Autowired MongoTemplate senseMongoTemplate;

	public void setSenseMongoTemplate(MongoTemplate senseMongoTemplate) {
		this.senseMongoTemplate = senseMongoTemplate;
	}

	private static final String CATEGORY_SEQ = "category_seq";
	private static final String SUB_CATEGORY_SEQ = "sub_category_seq";
	private static final String ITEM_SEQ = "item_seq";
	private static final String ID = "_id";
	private static final String CATEGORY_ID = "categoryId";
	private static final String SUB_CATEGORY_ID = "subCategoryId";
	private static final String TYPE = "type";
	
	private static final String LOCAL_MENU_CACHE = CacheNameType.LOCAL_MENU_CACHE.getCacheName();
	private static final String MENU_ITEMS = CachedValuesType.MENU_ITEMS.getCachedValues();
	
	private  Cache<Object, Object> cache;
	
	@SuppressWarnings("unchecked")
	public List<Item> getAllMenuItems() {
		cache = appCache.getCache(LOCAL_MENU_CACHE);
		if (cache.get(MENU_ITEMS) == null) {
			List<Item> items = senseMongoTemplate.findAll(Item.class);
			cache.getAndPut(MENU_ITEMS, (items == null) ? new ArrayList<>() : items);
		}
		return (List<Item>) cache.get(MENU_ITEMS);
	}

	/**
	 * returns list of items
	 * 
	 * @param type
	 *            - filter items list based on type
	 * @returns all items if null is passed in param
	 * @throws BusinessException
	 */
	public List<Item> getItems(MenuType type) throws BusinessException {
		List<Item> itemsFromCache = getAllMenuItems();
		List<Item> itemsAfterFilter = new ArrayList<>();
		try {
			if (type != null) {
				for (Item item : itemsFromCache) {
					if (type == item.getType()) {
						itemsAfterFilter.add(item);
					}
				}
			} else {
				itemsAfterFilter = itemsFromCache;
			}
			return itemsAfterFilter;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	public Item addEditItem(ItemRequest request) {
		cache = appCache.getCache(LOCAL_MENU_CACHE);
		Item item = null;
		if (request.getId() != null) {
			// edit existing item
			item = senseMongoTemplate.findById(request.getId(), Item.class);
		} else {
			// create new item
			int id = (int) context.getNextSequenceId(ITEM_SEQ);
			request.setId(id);
		}
		item = mapper.mapRequestToItemEntity(request);
		senseMongoTemplate.save(item);
		cache.clear();
		return item;
	}

	public Integer deleteItem(ItemRequest request) {
		cache = appCache.getCache(LOCAL_MENU_CACHE);
		Query findQuery = Query.query(Criteria.where(ID).is(request.getId()));
		senseMongoTemplate.findAndRemove(findQuery, Item.class);
		cache.clear();
		return request.getId();
	}

	/**
	 * returns list of Categories
	 * 
	 * @param type
	 *            - filter category list based on type
	 * @returns all categories if null is passed in param
	 * @throws BusinessException
	 */
	public List<Category> getCategories(MenuType type) throws BusinessException {
		try {
			if (type != null) {
				Query findCategories = Query.query(Criteria.where(TYPE).is(type));
				return senseMongoTemplate.find(findCategories, Category.class);
			}
			return senseMongoTemplate.findAll(Category.class);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	public Category addEditCategory(CategoryRequest request) {
		cache = appCache.getCache(LOCAL_MENU_CACHE);
		Category category = null;
		if (request.getId() != null) {
			// edit existing category
			category = senseMongoTemplate.findById(request.getId(), Category.class);
			category.setName(request.getName());
		} else {
			// create new category
			int id = (int) context.getNextSequenceId(CATEGORY_SEQ);
			MenuType type = MenuType.getType(request.getType());
			category = new Category(id, type, request.getName());
		}
		senseMongoTemplate.save(category);
		cache.clear();
		return category;
	}

	public Integer deleteCategory(CategoryRequest request) {
		cache = appCache.getCache(LOCAL_MENU_CACHE);
		Query findCategoryQuery = Query.query(Criteria.where(ID).is(request.getId()));
		senseMongoTemplate.findAndRemove(findCategoryQuery, Category.class);
		//TODO: these queries must be in one transaction
		Query findSubCategoryQuery = Query.query(Criteria.where(CATEGORY_ID).is(request.getId()));
		senseMongoTemplate.findAllAndRemove(findSubCategoryQuery, SubCategory.class);
		Query findItemsQuery = Query.query(Criteria.where(CATEGORY_ID).is(request.getId()));
		senseMongoTemplate.findAllAndRemove(findItemsQuery, Item.class);
		cache.clear();
		return request.getId();
	}
	
	/**
	 * returns list of Sub Categories
	 * 
	 * @param type
	 *            - filter category list based on type
	 * @returns all categories if null is passed in param
	 * @throws BusinessException
	 */
	public List<SubCategory> getSubCategories(MenuType type) throws BusinessException {
		try {
			if (type != null) {
				Query findSubCategories = Query.query(Criteria.where(TYPE).is(type));
				return senseMongoTemplate.find(findSubCategories, SubCategory.class);
			}
			return senseMongoTemplate.findAll(SubCategory.class);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	public SubCategory addEditSubCategory(SubCategoryRequest request) {
		cache = appCache.getCache(LOCAL_MENU_CACHE);
		SubCategory subCategory = null;
		if (request.getId() != null) {
			// edit existing category
			subCategory = senseMongoTemplate.findById(request.getId(), SubCategory.class);
			subCategory.setName(request.getName());
		} else {
			// create new category
			int id = (int) context.getNextSequenceId(SUB_CATEGORY_SEQ);
			MenuType type = MenuType.getType(request.getType());
			subCategory = new SubCategory(id, request.getCategoryId(), type, request.getName());
		}
		senseMongoTemplate.save(subCategory);
		cache.clear();
		return subCategory;
	}

	public Integer deleteSubCategory(SubCategoryRequest request) {
		cache = appCache.getCache(LOCAL_MENU_CACHE);
		Query findSubCategoryQuery = Query.query(Criteria.where(ID).is(request.getId()));
		senseMongoTemplate.findAndRemove(findSubCategoryQuery, SubCategory.class);
		//TODO: these queries must be in one transaction
		Query findItemsQuery = Query.query(Criteria.where(SUB_CATEGORY_ID).is(request.getId()));
		senseMongoTemplate.findAllAndRemove(findItemsQuery, Item.class);
		cache.clear();
		return request.getId();
	}
}
