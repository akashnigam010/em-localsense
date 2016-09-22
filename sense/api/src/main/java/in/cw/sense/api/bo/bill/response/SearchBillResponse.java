package in.cw.sense.api.bo.bill.response;

import java.util.ArrayList;
import java.util.List;

import in.cw.sense.api.bo.bill.dto.BillDto;
import in.cw.sense.api.bo.response.GenericResponse;
import in.cw.sense.api.bo.table.dto.TableDto;

@SuppressWarnings("rawtypes")
public class SearchBillResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<TableDto> openTables;
	private List<BillDto> settledBills;

	public List<TableDto> getOpenTables() {
		if (openTables == null) {
			openTables = new ArrayList<>();
		}
		return openTables;
	}

	public void setOpenTables(List<TableDto> openTables) {
		this.openTables = openTables;
	}

	public List<BillDto> getSettledBills() {
		return settledBills;
	}

	public void setSettledBills(List<BillDto> settledBills) {
		this.settledBills = settledBills;
	}
}
