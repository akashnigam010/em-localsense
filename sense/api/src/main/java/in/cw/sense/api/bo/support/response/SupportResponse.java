package in.cw.sense.api.bo.support.response;

import java.util.List;

import in.cw.sense.api.bo.response.GenericResponse;
import in.cw.sense.api.bo.support.dto.MessageDto;

public class SupportResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<MessageDto> messages;
	private String contactNumber;
	private String emailAddress;

	public List<MessageDto> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageDto> messages) {
		this.messages = messages;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
