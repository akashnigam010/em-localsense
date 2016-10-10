package in.cw.sense.app.report;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cwf.date.format.DateFormatType;
import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.bill.dto.BillDto;
import in.cw.sense.api.bo.bill.request.SearchBillRequest;
import in.cw.sense.api.bo.bill.response.SearchBillResponse;
import in.cw.sense.api.bo.bill.type.BillStatusType;
import in.cw.sense.api.bo.report.dto.DailySale;
import in.cw.sense.api.bo.report.response.ReportResponse;
import in.cw.sense.app.bill.BillDelegate;

@Service
public class ReportDelegate {
	@Autowired
	BillDelegate billDelegate;

	private SimpleDateFormat formatter_DD_MM_YYYY = new SimpleDateFormat(DateFormatType.DD_MM_YYYY.getByFormat());
	private SimpleDateFormat formatter_DD_MM = new SimpleDateFormat(DateFormatType.DD_MM.getByFormat());

	public ReportResponse generateSalesReport(SearchBillRequest request) throws BusinessException, ParseException {
		ReportResponse response = new ReportResponse();
		List<String> billStatuses = new ArrayList<>();
		billStatuses.add(BillStatusType.SETTLED.getStatus());
		SearchBillResponse billResponse = billDelegate.searchBills(request, billStatuses);
		response.setSales(getDailySalesFromBills(billResponse.getSettledBills()));
		return response;
	}

	private List<DailySale> getDailySalesFromBills(List<BillDto> bills) throws ParseException {
		Map<String, List<BillDto>> map = new HashMap<>();
		for (BillDto bill : bills) {
			String billDate = formatter_DD_MM_YYYY.format(bill.getSettledDateTime());
			List<BillDto> billsPerDate = map.get(billDate);
			if (billsPerDate == null) {
				billsPerDate = new ArrayList<>();
			}
			billsPerDate.add(bill);
			map.put(billDate, billsPerDate);
		}
		return calculateTotalSale(map);
	}

	private List<DailySale> calculateTotalSale(Map<String, List<BillDto>> map) throws ParseException {
		List<DailySale> sales = new ArrayList<>();
		for (Entry<String, List<BillDto>> e : map.entrySet()) {
			DailySale saleDto = new DailySale();
			saleDto.setDate(formatter_DD_MM_YYYY.parse(e.getKey()));
			saleDto.setDateString(formatter_DD_MM.format(saleDto.getDate()));
			saleDto.setTotalSales(findTotalOfAllBills(e.getValue()));
			sales.add(saleDto);
		}
		Collections.sort(sales);
		return sales;
	}

	private BigDecimal findTotalOfAllBills(List<BillDto> bills) {
		BigDecimal total = BigDecimal.ZERO;
		for (BillDto bill : bills) {
			total = total.add(bill.getGrandTotal());
		}
		return total;
	}

}
