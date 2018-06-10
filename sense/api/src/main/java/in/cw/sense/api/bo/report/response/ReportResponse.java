package in.cw.sense.api.bo.report.response;

import java.util.ArrayList;
import java.util.List;

import in.cw.sense.api.bo.report.dto.DailySale;
import in.cw.sense.api.bo.response.GenericResponse;

@SuppressWarnings("rawtypes")
public class ReportResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	List<DailySale> sales;

	public List<DailySale> getSales() {
		if (sales == null) {
			sales = new ArrayList<>();
		}
		return sales;
	}

	public void setSales(List<DailySale> sales) {
		this.sales = sales;
	}
}
