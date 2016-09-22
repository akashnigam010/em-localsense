package in.cw.sense.api.bo.table.request;

public class AddTableDetailsRequest {
	private Integer id;
	private String tableNumber;
	private Integer coverCapacity;
	private Integer seatingAreaId;

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

	public Integer getCoverCapacity() {
		return coverCapacity;
	}

	public void setCoverCapacity(Integer coverCapacity) {
		this.coverCapacity = coverCapacity;
	}

	public Integer getSeatingAreaId() {
		return seatingAreaId;
	}

	public void setSeatingAreaId(Integer seatingAreaId) {
		this.seatingAreaId = seatingAreaId;
	}
}
