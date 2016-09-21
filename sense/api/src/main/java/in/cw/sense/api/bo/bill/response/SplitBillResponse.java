package in.cw.sense.api.bo.bill.response;

import in.cw.sense.api.bo.bill.dto.BillDto;
import in.cw.sense.api.bo.response.GenericResponse;

@SuppressWarnings("rawtypes")
public class SplitBillResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private BillDto billOne;
	private BillDto billTwo;

	public BillDto getBillOne() {
		return billOne;
	}

	public void setBillOne(BillDto billOne) {
		this.billOne = billOne;
	}

	public BillDto getBillTwo() {
		return billTwo;
	}

	public void setBillTwo(BillDto billTwo) {
		this.billTwo = billTwo;
	}
}
