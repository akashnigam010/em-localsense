package in.cw.sense.app.tabledetails.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import in.cw.sense.api.bo.bill.entity.OrderEntity;
import in.cw.sense.api.bo.bill.entity.OrderUnit;
import in.cw.sense.api.bo.table.dto.Item;
import in.cw.sense.api.bo.table.dto.Order;
import in.cw.sense.api.bo.table.dto.SeatingAreaDto;
import in.cw.sense.api.bo.table.dto.TableDto;
import in.cw.sense.api.bo.table.entity.Table;
import in.cw.sense.api.bo.table.entity.TableSeatingArea;
import in.cw.sense.api.bo.table.response.OrderSummaryResponse;
import in.cw.sense.api.bo.table.response.TableDetailsResponse;
import in.cw.sense.api.bo.table.type.TableStatusType;

@Service
public class TableDetailsMapper {

	public void map(List<Table> tableDetails, List<TableSeatingArea> seatingAreaDetails,
			TableDetailsResponse response) {
		List<SeatingAreaDto> seatingAreaDtos = new ArrayList<>();
		for (TableSeatingArea seatingArea : seatingAreaDetails) {
			SeatingAreaDto seatingAreaDto = mapSeatingAreaEntityToDto(seatingArea);
			for (Table table : tableDetails) {
				if (seatingArea.getId().equals(table.getSeatingArea().getId())) {
					TableDto tableDto = mapTableEntityToDto(table);
					seatingAreaDto.getTables().add(tableDto);
				}
			}
			seatingAreaDtos.add(seatingAreaDto);
		}
		response.setSeatingAreas(seatingAreaDtos);
	}

	public void mapDashboardData(List<Table> tableDetails, List<TableSeatingArea> seatingAreaDetails,
			TableDetailsResponse response) {
		List<SeatingAreaDto> seatingAreaDtos = new ArrayList<>();
		for (TableSeatingArea seatingArea : seatingAreaDetails) {
			SeatingAreaDto seatingAreaDto = mapSeatingAreaEntityToDto(seatingArea);
			for (Table table : tableDetails) {
				if (seatingArea.getId().equals(table.getSeatingArea().getId())) {
					TableDto tableDto = mapTableEntityToDto(table);
					seatingAreaDto.getTables().add(tableDto);
				}
			}
			if (seatingAreaDto.getTables().size() != 0) {
				seatingAreaDtos.add(seatingAreaDto);
			}
		}
		response.setSeatingAreas(seatingAreaDtos);
	}

	private SeatingAreaDto mapSeatingAreaEntityToDto(TableSeatingArea seatingArea) {
		SeatingAreaDto seatingAreaDto = new SeatingAreaDto();
		seatingAreaDto.setId(seatingArea.getId());
		seatingAreaDto.setName(seatingArea.getSeatingAreaName());
		return seatingAreaDto;
	}

	public TableDto mapTableEntityToDto(Table table) {
		TableDto tableDto = new TableDto();
		tableDto.setCovers(table.getCovers());
		tableDto.setId(table.getId());
		tableDto.setTableNumber(table.getTableNumber());
		tableDto.setStatus(table.getStatus());
		tableDto.setHasOrders(!(table.getOrders().isEmpty()));
		return tableDto;
	}

	public void mapOrderEntityToResponse(List<OrderEntity> orderEntities, OrderSummaryResponse response,
			String tableNumber) {
		List<Order> addOrders = new ArrayList<>();
		for (OrderEntity order : orderEntities) {
			Order addOrder = new Order();
			addOrder.setId(order.getId());
			List<Item> items = new ArrayList<>();
			mapOrderEntityToItems(order, items);
			addOrder.setItems(items);
			addOrders.add(addOrder);
		}
		response.setOrderDetails(addOrders);
		response.setTableNumber(tableNumber);
	}

	private void mapOrderEntityToItems(OrderEntity order, List<Item> items) {
		for (OrderUnit orderUnit : order.getOrderUnits()) {
			Item item = new Item();
			item.setId(orderUnit.getItemId());
			item.setName(orderUnit.getItemName());
			item.setPrice(orderUnit.getPrice());
			item.setQuantity(orderUnit.getQuantity());
			item.setType(orderUnit.getType());
			item.setValue(orderUnit.getValue());
			items.add(item);
		}
	}

	public List<TableDto> map(List<Table> entities) {
		List<TableDto> dtos = new ArrayList<>();
		for (Table entity : entities) {
			dtos.add(mapTableEntityToDto(entity));
		}
		return dtos;
	}

	public Table resetTable(Table table) {
		table.setBillIds(java.util.Collections.emptyList());
		table.setIsBillDirty(false);
		table.setCovers(null);
		table.setOrders(null);
		table.setStatus(TableStatusType.VACANT);
		return table;
	}
}