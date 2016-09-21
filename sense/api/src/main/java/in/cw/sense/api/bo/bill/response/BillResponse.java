package in.cw.sense.api.bo.bill.response;

import java.util.ArrayList;
import java.util.List;

import in.cw.sense.api.bo.bill.dto.BillDto;
import in.cw.sense.api.bo.response.GenericResponse;

@SuppressWarnings("rawtypes")
public class BillResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<BillDto> bills;

	public List<BillDto> getBills() {
		if (bills == null) {
			bills = new ArrayList<>();
		}
		return bills;
	}

	public void setBills(List<BillDto> bills) {
		this.bills = bills;
	}

}
