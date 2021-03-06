package in.cw.sense.api.bo.table.response;

import java.util.List;

import in.cw.sense.api.bo.response.GenericResponse;
import in.cw.sense.api.bo.table.dto.OrderDto;
import in.cw.sense.api.bo.table.type.TableStatusType;

@SuppressWarnings("rawtypes")
public class OrderSummaryResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private Integer tableId;
	private String tableNumber;
	private TableStatusType status;
	private List<OrderDto> orderDetails;

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

	public List<OrderDto> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDto> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public TableStatusType getStatus() {
		return status;
	}

	public void setStatus(TableStatusType status) {
		this.status = status;
	}
}
