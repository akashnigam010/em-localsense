package in.cw.sense.api.bo.kot.dto;

import java.util.List;

import in.cw.sense.api.bo.table.dto.OrderDto;

public class KotDto {
	private Integer id;
	private String tableNumber;
	private List<Integer> billIds;
	private OrderDto order;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
	}

	public List<Integer> getBillIds() {
		return billIds;
	}

	public void setBillIds(List<Integer> billIds) {
		this.billIds = billIds;
	}

	public OrderDto getOrder() {
		return order;
	}

	public void setOrder(OrderDto order) {
		this.order = order;
	}
}
