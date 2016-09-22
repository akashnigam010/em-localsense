package in.cw.sense.api.bo.tax.request;

import java.math.BigDecimal;

public class TaxDetailsRequest {
	private Integer id;
	private String taxName;
	private BigDecimal fnbTax;
	private BigDecimal liquorTax;
	private String taxType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaxName() {
		return taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	public BigDecimal getFnbTax() {
		return fnbTax;
	}

	public void setFnbTax(BigDecimal fnbTax) {
		this.fnbTax = fnbTax;
	}

	public BigDecimal getLiquorTax() {
		return liquorTax;
	}

	public void setLiquorTax(BigDecimal liquorTax) {
		this.liquorTax = liquorTax;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

}
