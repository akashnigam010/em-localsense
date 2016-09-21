package in.cw.sense.api.bo.table.dto;

import in.cw.sense.api.bo.table.type.TableStatusType;

public class TableDto {
	private Integer id;
	private String tableNumber;
	private Integer covers;
	private TableStatusType status;
	private boolean hasOrders;

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

	public Integer getCovers() {
		return covers;
	}

	public void setCovers(Integer covers) {
		this.covers = covers;
	}

	public boolean isHasOrders() {
		return hasOrders;
	}

	public void setHasOrders(boolean hasOrders) {
		this.hasOrders = hasOrders;
	}

	public TableStatusType getStatus() {
		return status;
	}

	public void setStatus(TableStatusType status) {
		this.status = status;
	}

}
