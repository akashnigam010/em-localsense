package in.cw.sense.api.bo.table.request;

public class TableDetailsRequest {
	private String tableNumber;
	private int coverCapacity;
	private int seatingArea;
	private String tableStatus;

	public String getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
	}

	public int getCoverCapacity() {
		return coverCapacity;
	}

	public void setCoverCapacity(int coverCapacity) {
		this.coverCapacity = coverCapacity;
	}

	public int getSeatingArea() {
		return seatingArea;
	}

	public void setSeatingArea(int seatingArea) {
		this.seatingArea = seatingArea;
	}

	public String getTableStatus() {
		return tableStatus;
	}

	public void setTableStatus(String tableStatus) {
		this.tableStatus = tableStatus;
	}
}
