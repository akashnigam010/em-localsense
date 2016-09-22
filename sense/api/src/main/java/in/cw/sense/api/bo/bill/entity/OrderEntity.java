package in.cw.sense.api.bo.bill.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

public class OrderEntity {
	@Field("id")
	private Integer id;
	@Field("orderUnits")
	private List<OrderUnit> orderUnits;
	@Field("createdDateTime")
	private Date createdDateTime;
	@Field("createdDateTimeString")
	private String createdDateTimeString;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<OrderUnit> getOrderUnits() {
		if (orderUnits == null) {
			return new ArrayList<>();
		}
		return orderUnits;
	}

	public void setOrderUnits(List<OrderUnit> orderUnits) {
		this.orderUnits = orderUnits;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getCreatedDateTimeString() {
		return createdDateTimeString;
	}

	public void setCreatedDateTimeString(String createdDateTimeString) {
		this.createdDateTimeString = createdDateTimeString;
	}
}
