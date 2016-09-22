package in.cw.sense.api.bo.table.response;

import java.util.List;

import in.cw.sense.api.bo.response.GenericResponse;
import in.cw.sense.api.bo.table.dto.Order;

@SuppressWarnings("rawtypes")
public class OrderSummaryResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private Integer tableId;
	private String tableNumber;
	private List<Order> orderDetails;

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

	public String getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
	}

	public List<Order> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<Order> orderDetails) {
		this.orderDetails = orderDetails;
	}
}
