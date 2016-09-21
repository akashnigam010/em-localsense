package in.cw.sense.api.bo.support.dto;

import java.io.Serializable;

public class ContactSupportDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String contactNumber;
	private String email;

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
