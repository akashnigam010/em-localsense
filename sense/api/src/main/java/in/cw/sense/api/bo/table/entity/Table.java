package in.cw.sense.api.bo.table.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import in.cw.sense.api.bo.bill.entity.OrderEntity;
import in.cw.sense.api.bo.table.type.TableStatusType;

@Document(collection = "table")
public class Table {
	@Id
	@Field
	private Integer id;
	@Field("seatingArea")
	private TableSeatingArea seatingArea;
	@Field("tableNumber")
	private String tableNumber;
	@Field("covers")
	private Integer covers;
	@Field("status")
	private TableStatusType status = TableStatusType.VACANT;
	@Field("billIds")
	private List<Integer> billIds = new ArrayList<>();
	@Field("isBillDirty")
	private Boolean isBillDirty = Boolean.FALSE;
	@Field("orders")
	private List<OrderEntity> orders;

	public Table(Integer id, TableSeatingArea seatingArea, String tableNumber, Integer covers,
			TableStatusType status) {
		super();
		this.id = id;
		this.seatingArea = seatingArea;
		this.tableNumber = tableNumber;
		this.covers = covers;
		this.status = status;
	}

	public Table() {
		super();
	}

	public Table(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TableSeatingArea getSeatingArea() {
		return seatingArea;
	}

	public void setSeatingArea(TableSeatingArea seatingArea) {
		this.seatingArea = seatingArea;
	}

	public String getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
	}

	public Integer getCovers() {
		return covers;
	}

	public void setCovers(Integer covers) {
		this.covers = covers;
	}

	public List<Integer> getBillIds() {
		return billIds;
	}

	public void setBillIds(List<Integer> billIds) {
		this.billIds = billIds;
	}

	public void addBillId(Integer billId) {
		this.getBillIds().add(billId);
	}

	public Boolean getIsBillDirty() {
		return isBillDirty;
	}

	public void setIsBillDirty(Boolean isBillDirty) {
		this.isBillDirty = isBillDirty;
	}

	public List<OrderEntity> getOrders() {
		if (orders == null) {
			return new ArrayList<>();
		}
		return orders;
	}

	public void setOrders(List<OrderEntity> orders) {
		this.orders = orders;
	}

	public TableStatusType getStatus() {
		return status;
	}

	public void setStatus(TableStatusType status) {
		this.status = status;
	}
}
