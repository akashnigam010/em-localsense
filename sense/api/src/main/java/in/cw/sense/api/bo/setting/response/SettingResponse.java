package in.cw.sense.api.bo.setting.response;

import in.cw.sense.api.bo.response.GenericResponse;
import in.cw.sense.api.bo.setting.dto.CloudConnectDto;

public class SettingResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private CloudConnectDto cloudConnect;

	public CloudConnectDto getCloudConnect() {
		return cloudConnect;
	}

	public void setCloudConnect(CloudConnectDto cloudConnect) {
		this.cloudConnect = cloudConnect;
	}
}
