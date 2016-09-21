package in.cw.sense.api.bo.personnel.request;

public class UserSignOnRequest {
	private String accessCode;
	private String pin;

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
}
