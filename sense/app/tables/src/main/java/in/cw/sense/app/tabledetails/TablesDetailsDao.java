package in.cw.sense.app.tabledetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import cwf.date.CwfClock;
import cwf.dbhelper.SenseContext;
import cwf.helper.exception.BusinessException;
import cwf.helper.type.GenericErrorCodeType;
import in.cw.sense.api.bo.bill.entity.OrderEntity;
import in.cw.sense.api.bo.bill.entity.OrderUnit;
import in.cw.sense.api.bo.kot.entity.Kot;
import in.cw.sense.api.bo.table.dto.ItemDto;
import in.cw.sense.api.bo.table.dto.TableDto;
import in.cw.sense.api.bo.table.entity.Table;
import in.cw.sense.api.bo.table.entity.TableSeatingArea;
import in.cw.sense.api.bo.table.request.AddOrderRequest;
import in.cw.sense.api.bo.table.request.AddSeatingAreaRequest;
import in.cw.sense.api.bo.table.request.AddTableDetailsRequest;
import in.cw.sense.api.bo.table.request.SeatingAreaIdRequest;
import in.cw.sense.api.bo.table.request.TableIdRequest;
import in.cw.sense.api.bo.table.type.TableStatusType;
import in.cw.sense.app.menu.MenuDao;
import in.cw.sense.app.tabledetails.mapper.TableDetailsMapper;
import in.cw.sense.app.tabledetails.type.TableDetailsErrorCodeType;

@Repository
public class TablesDetailsDao {
	private static final Logger LOG = Logger.getLogger(TablesDetailsDao.class);
	@Autowired SenseContext context;
	@Autowired TableDetailsMapper mapper;
	@Autowired MenuDao menuDao;
	@Autowired CwfClock clock;
	@Autowired MongoTemplate senseMongoTemplate;

	private static final String SEATING_AREA_SEQ = "table_seating_area_seq";
	private static final String TABLE_DETAILS_SEQ = "table_seq";
	private static final String ORDER_SEQ = "order_seq";
	private static final String ID = "_id";
	private static final String STATUS = "status";
	private static final String KOT_SEQ = "kot_seq";

	public void setSenseMongoTemplate(MongoTemplate senseMongoTemplate) {
		this.senseMongoTemplate = senseMongoTemplate;
	}
	
	public List<Table> getAllTableDetails() throws BusinessException {
		try {
			List<Table> tableDetails = senseMongoTemplate.findAll(Table.class);
			return (tableDetails == null) ? new ArrayList<>() : tableDetails;
		} catch (Exception e) {
			LOG.error("Exception occured while fetching all table details: ", e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}

	public List<TableSeatingArea> getAllSeatingAreaDetails() throws BusinessException {
		try {
			List<TableSeatingArea> seatingAreaDetails = senseMongoTemplate.findAll(TableSeatingArea.class);
			return (seatingAreaDetails == null) ? new ArrayList<>() : seatingAreaDetails;
		} catch (Exception e) {
			LOG.error("Exception occured while fetching all seating area details: ", e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}

	public List<Table> addEditSeatingArea(AddSeatingAreaRequest request) throws BusinessException {
		try {
			Query findQuery = Query.query(Criteria.where("id").is(request.getId()));
			TableSeatingArea seatingArea = senseMongoTemplate.findOne(findQuery, TableSeatingArea.class);
			if (seatingArea == null) {
				int seqId = (int) context.getNextSequenceId(SEATING_AREA_SEQ);
				TableSeatingArea newSeatingArea = new TableSeatingArea(seqId, request.getName());
				senseMongoTemplate.save(newSeatingArea);
			} else {
				seatingArea.setSeatingAreaName(request.getName());
				senseMongoTemplate.save(seatingArea);
			}
			return getAllTableDetails();
		} catch (Exception e) {
			LOG.error("Exception occured while adding new seating area", e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}

	public List<Table> addEditTableDetails(AddTableDetailsRequest request) throws BusinessException {
		try {
			if (request.getId() != null) {
				// update table
				Table table = senseMongoTemplate.findById(request.getId(), Table.class);
				if (table != null) {
					table.setCovers(request.getCoverCapacity());
					table.setTableNumber(request.getTableNumber());
					senseMongoTemplate.save(table);
				} else {
					throw new BusinessException(TableDetailsErrorCodeType.TABLE_NOT_FOUND);
				}
			} else {
				// add new VACANT table
				TableSeatingArea seatingArea = 
						senseMongoTemplate.findById(request.getSeatingAreaId(), TableSeatingArea.class);
				if (seatingArea != null) {
					int seqId = (int) context.getNextSequenceId(TABLE_DETAILS_SEQ);
					Table newTable = new Table(seqId, seatingArea, request.getTableNumber(), request.getCoverCapacity(),
							TableStatusType.VACANT);
					senseMongoTemplate.save(newTable);
				} else {
					throw new BusinessException(TableDetailsErrorCodeType.SEATING_AREA_NOT_FOUND);
				}
			}
			return getAllTableDetails();
		} catch (Exception e) {
			LOG.error("Exception occured while adding new table details: ", e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}

	public List<Table> deleteSeatingArea(SeatingAreaIdRequest request) throws BusinessException {
		try {
			Query findQuery = Query.query(Criteria.where(ID).is(request.getSeatingAreaId()));
			TableSeatingArea seatingArea = senseMongoTemplate.findOne(findQuery, TableSeatingArea.class);
			if (seatingArea == null) {
				throw new BusinessException(TableDetailsErrorCodeType.SEATING_AREA_NOT_FOUND);
			}
			Query findTableQuery = Query.query(Criteria.where("seatingArea._id").is(request.getSeatingAreaId()));
			List<Table> tables = senseMongoTemplate.find(findTableQuery, Table.class);
			// Checking if any tables are assigned to this particular seating
			// area or not
			if (tables == null || tables.size() == 0) {
				senseMongoTemplate.remove(findQuery, TableSeatingArea.class);
			} else {
				for (Table table : tables) {
					if (table.getOrders() != null && !table.getOrders().isEmpty()) {
						throw new BusinessException(TableDetailsErrorCodeType.CANT_DELETE_TABLE_WITH_ORDERS_ASSIGNED);
					}
				}
				senseMongoTemplate.remove(findQuery, TableSeatingArea.class);
			}
			return getAllTableDetails();
		} catch (Exception e) {
			LOG.error("Exception occured while deleting seating area: ", e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}

	public List<Table> deleteTableDetails(TableIdRequest request) throws BusinessException {
		try {
			Query findQuery = Query.query(Criteria.where(ID).is(request.getTableId()));
			Table tables = senseMongoTemplate.findOne(findQuery, Table.class);
			if (tables != null) {
				if (tables.getOrders() != null && tables.getOrders().size() != 0) {
					throw new BusinessException(TableDetailsErrorCodeType.CANT_DELETE_TABLE_WITH_ORDERS_ASSIGNED);
				}
				senseMongoTemplate.findAllAndRemove(findQuery, Table.class);
			} else {
				throw new BusinessException(TableDetailsErrorCodeType.TABLE_NOT_FOUND);
			}
			return getAllTableDetails();
		} catch (Exception e) {
			LOG.error("Exception occured while deleting table details: ", e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}

	public Table addOrderToTable(AddOrderRequest request) throws BusinessException {
		List<OrderEntity> orders = new ArrayList<>();
		try {
			Query query = Query.query(Criteria.where(ID).is(request.getTableId()));
			Table table = senseMongoTemplate.findOne(query, Table.class);
			if (table == null) {
				throw new BusinessException(TableDetailsErrorCodeType.TABLE_NOT_FOUND);
			}
			if (table.getOrders().isEmpty()) {
				orders.add(addANewOrder(request));
			} else {
				orders = table.getOrders();
				if (request.getOrderId() == null) {
					orders.add(addANewOrder(request));
				} else {
					for (OrderEntity orderEntity : orders) {
						if (orderEntity.getId().equals(request.getOrderId())) {
							List<OrderUnit> orderUnits = mapOrderItemsToOrderUnitEntity(request.getItems());
							orderEntity.setOrderUnits(orderUnits);
							break;
						}
					}
				}
			}
			senseMongoTemplate.updateFirst(query, getUpdate(orders), Table.class);
			table.setOrders(orders);
			return table;
		} catch (Exception e) {
			LOG.error("Exception occured while adding order to a table with id:" + request.getTableId(), e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}

	private OrderEntity addANewOrder(AddOrderRequest request) {
		int seqId = (int) context.getNextSequenceId(ORDER_SEQ);
		OrderEntity order = new OrderEntity();
		List<OrderUnit> orderUnits = mapOrderItemsToOrderUnitEntity(request.getItems());
		order.setOrderUnits(orderUnits);
		order.setId(seqId);
		order.setCreatedDateTime(clock.getCurrentDate());
		order.setCreatedDateTimeString(order.getCreatedDateTime().toString());
		return order;
	}

	private Update getUpdate(List<OrderEntity> orders) {
		Update update = new Update();
		update.set("orders", orders);
		update.set("tableStatus", TableStatusType.OCCUPIED);
		update.set("isBillDirty", Boolean.TRUE);
		return update;
	}

	public List<OrderUnit> mapOrderItemsToOrderUnitEntity(List<ItemDto> orderItems) {
		List<OrderUnit> orderUnits = new ArrayList<>();
		// Here itemsFromCache has Item entity from Menu
		List<in.cw.sense.api.bo.menu.entity.Item> itemsFromCache = menuDao.getAllMenuItems();
		// Here tableOrderRequestItem is Item Dto from Table - addOrder request
		for (ItemDto tableOrderRequestItem : orderItems) {
			for (in.cw.sense.api.bo.menu.entity.Item itemFromCache : itemsFromCache) {
				if (tableOrderRequestItem.getId() == itemFromCache.getId()) {
					OrderUnit orderUnit = new OrderUnit();
					orderUnit.setItemId(itemFromCache.getId());
					orderUnit.setItemName(itemFromCache.getName());
					orderUnit.setPrice(itemFromCache.getPrice());
					orderUnit.setQuantity(tableOrderRequestItem.getQuantity());
					orderUnit.setType(itemFromCache.getType());
					orderUnit.setValue(
							itemFromCache.getPrice().multiply(new BigDecimal(tableOrderRequestItem.getQuantity())));
					orderUnits.add(orderUnit);
					break;
				}
			}
		}

		return orderUnits;
	}

	public List<OrderEntity> getOrdersForTable(Integer tableId) throws BusinessException {
		Table tableDetails = getOneTableDetails(tableId);
		return tableDetails.getOrders();
	}

	public Table getOneTableDetails(Integer tableId) throws BusinessException {
		try {
			return senseMongoTemplate.findById(tableId, Table.class);
		} catch (Exception e) {
			LOG.error("Exception occured while fetching table details for tableId: " + tableId, e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}

	public void updateTableWithBillDetails(Integer tableId, Integer billId, Boolean flag) throws BusinessException {
		try {
			Table table = senseMongoTemplate.findById(tableId, Table.class);
			if (table.getBillIds() == null || table.getBillIds().isEmpty()) {
				List<Integer> billIds = new ArrayList<>();
				billIds.add(billId);
				table.setBillIds(billIds);
			} else {
				if (!table.getBillIds().contains(billId)) {
					table.getBillIds().add(billId);
				}
			}
			table.setIsBillDirty(flag);
			senseMongoTemplate.save(table);
		} catch (Exception e) {
			LOG.error("Exception occured while fetching table details for tableId: " + tableId, e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}

	public List<TableDto> getAllOpenTables() throws BusinessException {
		try {
			Query findQuery = Query.query(Criteria.where(STATUS).is(TableStatusType.OCCUPIED.getStatus()));
			List<Table> entities = senseMongoTemplate.find(findQuery, Table.class);
			return mapper.map(entities);
		} catch (Exception e) {
			LOG.error("Exception occured while fetching all open status tables" + e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}

	public void openTable(TableIdRequest request) throws BusinessException {
		try {
			Table table = senseMongoTemplate.findById(request.getTableId(), Table.class);
			table.setStatus(TableStatusType.OCCUPIED);
			table.setCovers(request.getCovers());
			senseMongoTemplate.save(table);
		} catch (Exception e) {
			LOG.error("Exception occured while opening table" + e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		} finally {
			senseMongoTemplate.getDb().getMongo().close();
		}
	}

	public void cancelTable(TableIdRequest request) throws BusinessException {
		try {
			Table table = senseMongoTemplate.findById(request.getTableId(), Table.class);
			table = mapper.resetTable(table);
			senseMongoTemplate.save(table);
		} catch (Exception e) {
			LOG.error("Exception occured while cancelling table" + e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}

	public TableDto getTableInformation(Integer tableId) throws BusinessException {
		try {
			Table table = senseMongoTemplate.findById(tableId, Table.class);
			if (table == null) {
				throw new BusinessException(TableDetailsErrorCodeType.TABLE_NOT_FOUND);
			}
			return mapper.mapTableEntityToDto(table);
		} catch (Exception e) {
			LOG.error("Exception occured while fetching table information" + e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}

	public Table saveTable(Table table) {
		senseMongoTemplate.save(table);
		return table;
	}

	public void backUpTableKotOrders(String tableNumber, Integer billId, List<OrderEntity> orders) {
		Kot kot = null;
		for (OrderEntity order : orders) {
			kot = new Kot();
			kot.setId(order.getId());
			kot.setTableNumber(tableNumber);
			kot.setBillIds(Collections.singletonList(billId));
			kot.setOrder(order);
			senseMongoTemplate.save(kot);
		}
	}
}