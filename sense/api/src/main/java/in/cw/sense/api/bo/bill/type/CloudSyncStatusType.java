package in.cw.sense.api.bo.bill.type;

public enum CloudSyncStatusType {
	IN_SYNC("IN_SYNC"), 
	NOT_IN_SYNC("NOT_IN_SYNC");

	private String syncStatus;

	private CloudSyncStatusType(String syncStatus) {
		this.syncStatus = syncStatus;
	}

	public String getSyncStatus() {
		return syncStatus;
	}
}
