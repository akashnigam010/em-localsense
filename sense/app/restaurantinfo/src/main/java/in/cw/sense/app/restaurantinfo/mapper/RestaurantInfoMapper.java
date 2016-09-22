package in.cw.sense.app.restaurantinfo.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.cw.sense.api.bo.restaurant.dto.AdditionalDetailsDto;
import in.cw.sense.api.bo.restaurant.dto.AddressDto;
import in.cw.sense.api.bo.restaurant.dto.PhoneDto;
import in.cw.sense.api.bo.restaurant.dto.RestaurantInfoDto;
import in.cw.sense.api.bo.restaurant.entity.AdditionalDetails;
import in.cw.sense.api.bo.restaurant.entity.Address;
import in.cw.sense.api.bo.restaurant.entity.Phone;
import in.cw.sense.api.bo.restaurant.entity.RestaurantInfo;
import in.cw.sense.api.bo.restaurant.request.RestaurantInfoRequest;
import in.cw.sense.api.bo.restaurant.type.BarInfoType;

@Component
public class RestaurantInfoMapper {

	public RestaurantInfoDto mapRestaurantInfoEntityToDto(RestaurantInfo entity) {
		RestaurantInfoDto dto = new RestaurantInfoDto();
		dto.setName(entity.getName());
		if (StringUtils.isNotEmpty(entity.getTagline())) {
			dto.setTagline(entity.getTagline());
		}
		dto.setBarInfoType(entity.getBarInfoType().getCode());
		dto.setAddress(mapAddressDto(entity.getAddress()));
		dto.setPhone(mapPhoneDto(entity.getPhone()));
		dto.setAdditionalDetails(mapAdditionalDetailsDto(entity.getAdditionalDetails()));
		return dto;
	}

	public RestaurantInfo mapRestaurantInfoRequestToEntity(RestaurantInfoRequest request, RestaurantInfo entity) {
		entity.setName(request.getName());
		if (StringUtils.isNotEmpty(request.getTagline())) {
			entity.setTagline(request.getTagline());
		}
		entity.setBarInfoType(BarInfoType.getBarInfoByCode(request.getBarInfo()));
		entity.setAddress(mapAddressEntity(request));
		entity.setPhone(mapPhoneEntity(request));
		entity.setAdditionalDetails(mapAdditionalDetailsEntity(request));
		return entity;
	}

	private AddressDto mapAddressDto(Address entity) {
		AddressDto dto = new AddressDto();
		dto.setAddressLine1(entity.getAddressLine1());
		dto.setAddressLine2(entity.getAddressLine2());
		dto.setCity(entity.getCity());
		dto.setPinCode(entity.getPinCode());
		return dto;
	}

	private PhoneDto mapPhoneDto(Phone entity) {
		PhoneDto dto = new PhoneDto();
		dto.setPhone1(entity.getPhone1());
		if (StringUtils.isNotEmpty(entity.getPhone2())) {
			dto.setPhone2(entity.getPhone2());
		}
		return dto;
	}

	private AdditionalDetailsDto mapAdditionalDetailsDto(AdditionalDetails entity) {
		AdditionalDetailsDto dto = new AdditionalDetailsDto();
		if (StringUtils.isNotEmpty(entity.getServiceTaxNumber())) {
			dto.setServiceTaxNumber(entity.getServiceTaxNumber());
		}
		if (StringUtils.isNotEmpty(entity.getTinNumber())) {
			dto.setTinNumber(entity.getTinNumber());
		}
		if (StringUtils.isNotEmpty(entity.getGroup())) {
			dto.setGroup(entity.getGroup());
		}
		return dto;
	}

	private Address mapAddressEntity(RestaurantInfoRequest request) {
		Address entity = new Address();
		entity.setAddressLine1(request.getAddressLine1());
		entity.setAddressLine2(request.getAddressLine2());
		entity.setCity(request.getCity());
		entity.setPinCode(request.getPinCode());
		return entity;
	}

	private Phone mapPhoneEntity(RestaurantInfoRequest request) {
		Phone entity = new Phone();
		entity.setPhone1(request.getPhone1());
		if (StringUtils.isNotEmpty(request.getPhone2())) {
			entity.setPhone2(request.getPhone2());
		}
		return entity;
	}

	private AdditionalDetails mapAdditionalDetailsEntity(RestaurantInfoRequest request) {
		AdditionalDetails entity = new AdditionalDetails();
		if (StringUtils.isNotEmpty(request.getServiceTaxNumber())) {
			entity.setServiceTaxNumber(request.getServiceTaxNumber());
		}
		if (StringUtils.isNotEmpty(request.getTinNumber())) {
			entity.setTinNumber(request.getTinNumber());
		}
		if (StringUtils.isNotEmpty(request.getGroup())) {
			entity.setGroup(request.getGroup());
		}
		return entity;
	}
}
