package in.cw.sense.api.bo.restaurant.dto;

import in.cw.sense.api.bo.restaurant.type.BarInfoType;

public class RestaurantInfoDto {
	private String name;
	private String tagline;
	private Integer barInfoType;
	private AddressDto address;
	private PhoneDto phone;
	private AdditionalDetailsDto additionalDetails;

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

	public Integer getBarInfoType() {
		return barInfoType;
	}

	public void setBarInfoType(Integer barInfoType) {
		this.barInfoType = barInfoType;
	}

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto address) {
		this.address = address;
	}

	public PhoneDto getPhone() {
		return phone;
	}

	public void setPhone(PhoneDto phone) {
		this.phone = phone;
	}

	public AdditionalDetailsDto getAdditionalDetails() {
		return additionalDetails;
	}

	public void setAdditionalDetails(AdditionalDetailsDto additionalDetails) {
		this.additionalDetails = additionalDetails;
	}
}
