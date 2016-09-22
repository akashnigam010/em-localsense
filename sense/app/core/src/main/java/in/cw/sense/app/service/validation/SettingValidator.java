package in.cw.sense.app.service.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.setting.dto.CloudConnectDto;
import in.cw.sense.api.bo.setting.dto.CloudDetailsDto;
import in.cw.sense.api.bo.setting.request.SettingRequest;
import in.cw.sense.app.setting.type.SettingErrorCodeType;

@Component
public class SettingValidator {

	public void validate(SettingRequest request) throws BusinessException {
		CloudConnectDto cloudConnect = request.getCloudConnect();
		if (cloudConnect == null) {
			throw new BusinessException(SettingErrorCodeType.REQUEST_VALIDATION_FAILURE);
		}
		CloudDetailsDto cloudDetails = cloudConnect.getCloudDetails();
		if (cloudDetails == null) {
			throw new BusinessException(SettingErrorCodeType.REQUEST_VALIDATION_FAILURE);
		}
		if (cloudConnect.getRestaurantId() == null || StringUtils.isEmpty(cloudConnect.getPassword())
				|| StringUtils.isEmpty(cloudConnect.getPrivateKey()) || StringUtils.isEmpty(cloudConnect.getPublicKey())
				|| StringUtils.isEmpty(cloudDetails.getCloudUrl())
				|| StringUtils.isEmpty(cloudDetails.getPublicKey())) {
			throw new BusinessException(SettingErrorCodeType.REQUEST_VALIDATION_FAILURE);
		}
	}

}
