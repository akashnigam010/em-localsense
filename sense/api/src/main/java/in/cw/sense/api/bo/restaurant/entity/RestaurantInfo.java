package in.cw.sense.api.bo.restaurant.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import in.cw.sense.api.bo.restaurant.type.BarInfoType;

@Document(collection = "restaurant_info")
public class RestaurantInfo {
	@Id
	@Field
	private Integer id;
	
	@Field("name")
	private String name;

	@Field("tagline")
	private String tagline;

	@Field("barInfoType")
	private BarInfoType barInfoType;

	@Field("address")
	private Address address;

	@Field("phone")
	private Phone phone;

	@Field("additionalDetails")
	private AdditionalDetails additionalDetails;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public BarInfoType getBarInfoType() {
		return barInfoType;
	}

	public void setBarInfoType(BarInfoType barInfoType) {
		this.barInfoType = barInfoType;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public AdditionalDetails getAdditionalDetails() {
		return additionalDetails;
	}

	public void setAdditionalDetails(AdditionalDetails additionalDetails) {
		this.additionalDetails = additionalDetails;
	}
}
