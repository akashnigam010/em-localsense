package in.cw.sense.app.setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.setting.dto.CloudConnectDto;
import in.cw.sense.api.bo.setting.request.SettingRequest;
import in.cw.sense.api.bo.setting.response.SettingResponse;

@Service
public class SettingDelegate {
	@Autowired
	SettingDao dao;
	@Autowired
	SettingMapper mapper;
	
	public SettingResponse getCloudSettings() throws BusinessException {
		SettingResponse response = new SettingResponse();
		CloudConnectDto cloudConnectDto = dao.getCloudSettings();
		response.setCloudConnect(cloudConnectDto);
		return response;
	}

	public SettingResponse saveCloudSettings(SettingRequest request) throws BusinessException {
		SettingResponse response = new SettingResponse();
		CloudConnectDto cloudConnectDto = dao.saveCloudSettings(request);
		response.setCloudConnect(cloudConnectDto);
		return response;
	}
}
