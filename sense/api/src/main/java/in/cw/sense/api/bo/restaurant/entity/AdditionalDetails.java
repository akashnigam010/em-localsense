package in.cw.sense.api.bo.restaurant.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public class AdditionalDetails {
	@Field("serviceTaxNumber")
	private String serviceTaxNumber;
	
	@Field("tinNumber")
	private String tinNumber;
	
	@Field("group")
	private String group;
	
	public String getServiceTaxNumber() {
		return serviceTaxNumber;
	}
	public void setServiceTaxNumber(String serviceTaxNumber) {
		this.serviceTaxNumber = serviceTaxNumber;
	}
	public String getTinNumber() {
		return tinNumber;
	}
	public void setTinNumber(String tinNumber) {
		this.tinNumber = tinNumber;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	
}
