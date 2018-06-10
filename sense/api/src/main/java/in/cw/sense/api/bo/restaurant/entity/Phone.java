package in.cw.sense.api.bo.restaurant.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public class Phone {
	@Field("phone1")
	private String phone1;

	@Field("phone2")
	private String phone2;

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
}
