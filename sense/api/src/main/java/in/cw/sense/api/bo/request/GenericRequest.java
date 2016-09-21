package in.cw.sense.api.bo.request;

import java.io.Serializable;

public class GenericRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String loggedInUser;
	private Integer loggedInRoleId;

	public String getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(String loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public Integer getLoggedInRoleId() {
		return loggedInRoleId;
	}

	public void setLoggedInRoleId(Integer loggedInRoleId) {
		this.loggedInRoleId = loggedInRoleId;
	}
}
