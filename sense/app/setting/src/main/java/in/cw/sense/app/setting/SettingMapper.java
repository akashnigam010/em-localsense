package in.cw.sense.app.setting;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import cwf.helper.hash.HashUtil;
import in.cw.sense.api.bo.setting.dto.CloudConnectDto;
import in.cw.sense.api.bo.setting.dto.CloudDetailsDto;
import in.cw.sense.api.bo.setting.entity.CloudConnect;
import in.cw.sense.api.bo.setting.entity.CloudDetails;
import in.cw.sense.api.bo.setting.request.SettingRequest;

@Component
public class SettingMapper {
	
	public CloudConnectDto map(CloudConnect entity) {
		CloudConnectDto dto = new CloudConnectDto();
		dto.setRestaurantId(entity.getRestaurantId());
		dto.setPrivateKey(entity.getPrivateKey());
		dto.setPublicKey(entity.getPublicKey());
		dto.setCloudDetails(map(entity.getCloudDetails()));
		return dto;
	}

	public CloudDetailsDto map(CloudDetails entity) {
		CloudDetailsDto dto = new CloudDetailsDto();
		dto.setCloudUrl(entity.getCloudUrl());
		dto.setPublicKey(entity.getPublicKey());
		return dto;
	}

	public CloudConnect map(SettingRequest request, CloudConnect entity) throws NoSuchAlgorithmException {
		CloudConnectDto dto = request.getCloudConnect();
		entity.setRestaurantId(dto.getRestaurantId());
		entity.setPassword(HashUtil.hash(dto.getPassword()));
		entity.setPrivateKey(dto.getPrivateKey());
		entity.setPublicKey(dto.getPublicKey());
		entity.setCloudDetails(map(dto.getCloudDetails()));
		return entity;
	}
	
	public CloudDetails map(CloudDetailsDto dto) {
		CloudDetails entity = new CloudDetails();
		entity.setCloudUrl(dto.getCloudUrl());
		entity.setPublicKey(dto.getPublicKey());
		return entity;
	}

}
