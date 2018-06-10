package in.cw.sense.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cwf.helper.ResponseHelper;
import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.setting.request.SettingRequest;
import in.cw.sense.api.bo.setting.response.SettingResponse;
import in.cw.sense.app.service.validation.SettingValidator;
import in.cw.sense.app.setting.SettingDelegate;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "/setting")
public class SettingService {
	@Autowired
	ResponseHelper helper;
	@Autowired
	SettingDelegate delegate;
	@Autowired
	SettingValidator validator;

	@RequestMapping(value = "/getCloudSettings", method = RequestMethod.GET, headers = "Accept=application/json")
	public SettingResponse getCloudSettings() {
		SettingResponse response = new SettingResponse();
		try {
			response = delegate.getCloudSettings();
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/saveCloudSettings", method = RequestMethod.POST, headers = "Accept=application/json")
	public SettingResponse saveCloudSettings(@RequestBody SettingRequest request) {
		SettingResponse response = new SettingResponse();
		try {
			validator.validate(request);
			response = delegate.saveCloudSettings(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
