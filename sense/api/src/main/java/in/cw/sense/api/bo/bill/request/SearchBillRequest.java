package in.cw.sense.api.bo.bill.request;

import java.util.Calendar;

public class SearchBillRequest {
	private Calendar startDate;
	private Calendar endDate;
	private Integer id;

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
