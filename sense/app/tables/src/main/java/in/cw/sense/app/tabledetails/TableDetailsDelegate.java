package in.cw.sense.app.tabledetails;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.bill.entity.OrderEntity;
import in.cw.sense.api.bo.bill.entity.OrderUnit;
import in.cw.sense.api.bo.table.dto.ItemDto;
import in.cw.sense.api.bo.table.dto.TableDto;
import in.cw.sense.api.bo.table.entity.Table;
import in.cw.sense.api.bo.table.entity.TableSeatingArea;
import in.cw.sense.api.bo.table.request.AddOrderRequest;
import in.cw.sense.api.bo.table.request.AddSeatingAreaRequest;
import in.cw.sense.api.bo.table.request.AddTableDetailsRequest;
import in.cw.sense.api.bo.table.request.SeatingAreaIdRequest;
import in.cw.sense.api.bo.table.request.TableIdRequest;
import in.cw.sense.api.bo.table.request.ViewOrderSummaryRequest;
import in.cw.sense.api.bo.table.response.OrderSummaryResponse;
import in.cw.sense.api.bo.table.response.TableDetailsResponse;
import in.cw.sense.api.bo.table.type.TableStatusType;
import in.cw.sense.app.tabledetails.mapper.TableDetailsMapper;
import in.cw.sense.app.tabledetails.type.TableDetailsErrorCodeType;

@Service
public class TableDetailsDelegate {

	@Autowired TablesDetailsDao dao;
	@Autowired TableDetailsMapper mapper;

	public TableDetailsResponse loadDashboardData() throws BusinessException {
		TableDetailsResponse response = new TableDetailsResponse();
		List<Table> tableDetails = dao.getAllTableDetails();
		mapper.mapDashboardData(tableDetails, getAllTableSeatingAreaDetails(), response);
		return response;
	}
	
	public TableDetailsResponse getAllTableDetails() throws BusinessException {
		TableDetailsResponse response = new TableDetailsResponse();
		List<Table> tableDetails = dao.getAllTableDetails();
		mapper.map(tableDetails, getAllTableSeatingAreaDetails(), response);
		return response;
	}

	public TableDetailsResponse addEditSeatingArea(AddSeatingAreaRequest request) throws BusinessException {
		TableDetailsResponse response = new TableDetailsResponse();
		List<Table> tableDetails = dao.addEditSeatingArea(request);
		mapper.map(tableDetails, getAllTableSeatingAreaDetails(), response);
		return response;
	}

	public TableDetailsResponse addEditTableDetails(AddTableDetailsRequest request) throws BusinessException {
		TableDetailsResponse response = new TableDetailsResponse();
		List<Table> tableDetails = dao.addEditTableDetails(request);
		mapper.map(tableDetails, getAllTableSeatingAreaDetails(), response);
		return response;
	}

	public TableDetailsResponse deleteSeatingArea(SeatingAreaIdRequest request) throws BusinessException {
		TableDetailsResponse response = new TableDetailsResponse();
		List<Table> tableDetails = dao.deleteSeatingArea(request);
		mapper.map(tableDetails, getAllTableSeatingAreaDetails(), response);
		return response;
	}

	public TableDetailsResponse deleteTableDetails(TableIdRequest request) throws BusinessException {
		TableDetailsResponse response = new TableDetailsResponse();
		List<Table> tableDetails = dao.deleteTableDetails(request);
		mapper.map(tableDetails, getAllTableSeatingAreaDetails(), response);
		return response;
	}

	public OrderSummaryResponse addEditOrderToTable(AddOrderRequest request) throws BusinessException {
		OrderSummaryResponse response = new OrderSummaryResponse();
		Table tables = dao.addOrderToTable(request);
		List<OrderEntity> orderEntities = tables.getOrders();
		mapper.mapOrderEntityToResponse(orderEntities, response, tables.getTableNumber());
		response.setTableId(request.getTableId());
		return response;
	}

	public OrderSummaryResponse viewOrderSummary(ViewOrderSummaryRequest request) throws BusinessException {
		OrderSummaryResponse response = new OrderSummaryResponse();
		Table tableDetails = getOneTableDetails(request.getTableId());
		if (tableDetails == null) {
			throw new BusinessException(TableDetailsErrorCodeType.TABLE_NOT_FOUND);
		}
		List<OrderEntity> orderEntities = tableDetails.getOrders();
		mapper.mapOrderEntityToResponse(orderEntities, response, tableDetails.getTableNumber());
		response.setTableId(tableDetails.getId());
		return response;
	}

	public List<OrderEntity> getOrdersForATable(Integer tableId) throws BusinessException {
		return dao.getOrdersForTable(tableId);
	}
	
	public Table getOneTableDetails(Integer tableId) throws BusinessException {
		return dao.getOneTableDetails(tableId);
	}
	
	public List<TableSeatingArea> getAllTableSeatingAreaDetails() throws BusinessException {
		return dao.getAllSeatingAreaDetails();
	}
	
	public void updateTableWithBillDetails(Integer tableId, Integer billId, Boolean flag) 
			throws BusinessException {
		dao.updateTableWithBillDetails(tableId, billId, flag);
	}
	
	public List<TableDto> getAllOpenTables() throws BusinessException {
		return dao.getAllOpenTablesWithOrders();
	}

	public void openTable(TableIdRequest request) throws BusinessException {
		dao.openTable(request);
	}

	public void cancelTable(TableIdRequest request) throws BusinessException {
		dao.cancelTable(request);
	}
	
	public List<OrderUnit> mapOrderItemsToOrderUnitEntity(List<ItemDto> orderItems) {
		return dao.mapOrderItemsToOrderUnitEntity(orderItems);
	}
	
	public void closeTable(Integer tableId, Integer billId) throws BusinessException {
		try {
			Table table = dao.getOneTableDetails(tableId);
			if (table == null) {
				throw new BusinessException(TableDetailsErrorCodeType.TABLE_NOT_FOUND);
			}
			backUpTableKotOrders(table.getTableNumber(), billId, table.getOrders());
			table.setBillIds(Collections.EMPTY_LIST);
			table.setIsBillDirty(Boolean.FALSE);
			table.setStatus(TableStatusType.VACANT);
			table.setOrders(Collections.EMPTY_LIST);
			dao.saveTable(table);
		} finally {
		}
	}

	public void backUpTableKotOrders(String tableNumber, Integer billId, List<OrderEntity> orders) {
		dao.backUpTableKotOrders(tableNumber, billId, orders);
	}
}
