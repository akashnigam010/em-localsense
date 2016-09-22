package in.cw.sense.api.bo.table.request;

import java.util.List;

import in.cw.sense.api.bo.table.dto.Item;

public class AddOrderRequest {
	private Integer orderId;
	private Integer tableId;
	private List<Item> items;

	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getTableId() {
		return tableId;
	}
	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
}
