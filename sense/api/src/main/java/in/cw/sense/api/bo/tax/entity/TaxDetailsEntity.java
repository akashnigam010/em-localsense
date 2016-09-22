package in.cw.sense.api.bo.tax.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "tax")
public class TaxDetailsEntity {
	@Id
	private Integer id;

	@Field("taxType")
	private String taxType;

	@Field("fnbTax")
	private BigDecimal fnbTax;

	@Field("liquorTax")
	private BigDecimal liquorTax;

	@Field("taxName")
	private String taxName;

	public String getTaxName() {
		return taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	public TaxDetailsEntity() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public void setFnbTax(BigDecimal foodTax) {
		this.fnbTax = foodTax;
	}

	public BigDecimal getLiquorTax() {
		return liquorTax;
	}

	public void setLiquorTax(BigDecimal liquorTax) {
		this.liquorTax = liquorTax;
	}

}