package in.cw.sense.api.bo.table.type;

public enum TableStatusType {
	OCCUPIED("OCCUPIED"), 
	VACANT("VACANT");

	private String status;

	private TableStatusType(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
