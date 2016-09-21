package in.cw.sense.api.bo.report.dto;

import java.math.BigDecimal;
import java.util.Date;

public class DailySale {
	private Date date;
	private String dateString;
	private BigDecimal totalSales;

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}
}
