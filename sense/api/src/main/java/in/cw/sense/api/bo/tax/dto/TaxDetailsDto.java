package in.cw.sense.api.bo.tax.dto;

import java.math.BigDecimal;

public class TaxDetailsDto {
	private Integer id;
	private String taxName;
	private String taxType;
	private BigDecimal fnbTax;
	private BigDecimal liquorTax;

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

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
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
}
