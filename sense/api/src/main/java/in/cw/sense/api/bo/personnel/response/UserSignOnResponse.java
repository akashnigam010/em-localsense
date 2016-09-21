package in.cw.sense.api.bo.personnel.response;

import in.cw.sense.api.bo.personnel.dto.LoggedInUser;
import in.cw.sense.api.bo.response.GenericResponse;

@SuppressWarnings("rawtypes")
public class UserSignOnResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private String token;
	private LoggedInUser user;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LoggedInUser getUser() {
		return user;
	}

	public void setUser(LoggedInUser user) {
		this.user = user;
	}
}
