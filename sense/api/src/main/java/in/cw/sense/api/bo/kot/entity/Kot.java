package in.cw.sense.api.bo.kot.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import in.cw.sense.api.bo.bill.entity.OrderEntity;

@Document(collection = "kot")
public class Kot {
	@Id
	private Integer id;

	@Field("tableNumber")
	private String tableNumber;

	@Field("billIds")
	private List<Integer> billIds;

	@Field("order")
	private OrderEntity order;

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
		if (billIds == null) {
			return new ArrayList<>();
		}

		return billIds;
	}

	public void setBillIds(List<Integer> billIds) {
		this.billIds = billIds;
	}

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
	}
}
