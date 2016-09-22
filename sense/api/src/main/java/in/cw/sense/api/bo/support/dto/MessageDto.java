package in.cw.sense.api.bo.support.dto;

import java.io.Serializable;
import java.util.Date;

public class MessageDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String to;
	private String from;
	private String dateIso;
	private String date;
	private Boolean isNew;
	private String subject;
	private String messageBody;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getDateIso() {
		return dateIso;
	}

	public void setDateIso(String dateIso) {
		this.dateIso = dateIso;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
