package in.cw.sense.api.bo.bill.response;

import in.cw.sense.api.bo.bill.dto.BillDto;
import in.cw.sense.api.bo.response.GenericResponse;

@SuppressWarnings("rawtypes")
public class SettleBillResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private BillDto bill;

	public BillDto getBill() {
		return bill;
	}

	public void setBill(BillDto bill) {
		this.bill = bill;
	}
}
