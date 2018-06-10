package in.cw.sense.api.bo.setting.request;

import in.cw.sense.api.bo.setting.dto.CloudConnectDto;

public class SettingRequest {
	private CloudConnectDto cloudConnect;

	public CloudConnectDto getCloudConnect() {
		return cloudConnect;
	}

	public void setCloudConnect(CloudConnectDto cloudConnect) {
		this.cloudConnect = cloudConnect;
	}
}
