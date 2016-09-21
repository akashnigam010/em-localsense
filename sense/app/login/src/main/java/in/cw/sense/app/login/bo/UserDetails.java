package in.cw.sense.app.login.bo;

import java.io.Serializable;
import java.util.List;

public class UserDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Integer> features;
	private String role;

	public List<Integer> getFeatures() {
		return features;
	}

	public void setFeatures(List<Integer> features) {
		this.features = features;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
