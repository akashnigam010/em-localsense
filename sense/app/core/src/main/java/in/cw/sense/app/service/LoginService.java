package in.cw.sense.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cwf.adapters.email.Email;
import cwf.adapters.sms.Sms;
import cwf.adapters.vo.EmailMessage;
import cwf.adapters.vo.SmsMessage;
import cwf.helper.ResponseHelper;
import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.personnel.request.UserSignOnRequest;
import in.cw.sense.api.bo.personnel.response.BarAvailabilityResponse;
import in.cw.sense.api.bo.personnel.response.MessageCountResponse;
import in.cw.sense.api.bo.personnel.response.UserSignOnResponse;
import in.cw.sense.app.login.LoginDelegate;
import in.cw.sense.app.service.validation.LoginValidator;

@RestController
@RequestMapping(value = "/login")
public class LoginService {
	private boolean isPinSignOn;
	@Autowired
	LoginDelegate delegate;
	@Autowired
	ResponseHelper helper;
	@Autowired
	LoginValidator validator;
	@Autowired
	Email email;
	@Autowired
	Sms sms;

	@RequestMapping(value = "/signOn", method = RequestMethod.POST, headers = "Accept=application/json")
	public UserSignOnResponse signOn(@RequestBody UserSignOnRequest request) {
		UserSignOnResponse response = new UserSignOnResponse();
		isPinSignOn = false;
		try {
			validator.validateLoginService(request, isPinSignOn);
			response = delegate.login(request, isPinSignOn);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/pinSignOn", method = RequestMethod.POST, headers = "Accept=application/json")
	public UserSignOnResponse pinSignOn(@RequestBody UserSignOnRequest request) {
		UserSignOnResponse response = new UserSignOnResponse();
		isPinSignOn = true;
		try {
			validator.validateLoginService(request, isPinSignOn);
			response = delegate.login(request, isPinSignOn);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/isBarAvailable", method = RequestMethod.GET, headers = "Accept=application/json")
	public BarAvailabilityResponse isBarAvailable() {
		BarAvailabilityResponse response = new BarAvailabilityResponse();
		try {
			response = delegate.isBarAvailable();
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/getNewMessagesCount", method = RequestMethod.GET, headers = "Accept=application/json")
	public MessageCountResponse getNewMessagesCount() {
		MessageCountResponse response = new MessageCountResponse();
		try {
			response = delegate.getNewMessagesCount();
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	// TODO : Remove after verifying
	@RequestMapping(value = "/email", method = RequestMethod.GET, headers = "Accept=application/json")
	public UserSignOnResponse sample() {
		UserSignOnResponse response = new UserSignOnResponse();
		EmailMessage mail = new EmailMessage();
		mail.setToMailId("sandeepvalapi@gmail.com");
		mail.setSubject("Subject Content");
		mail.setHtmlPart("<html><h1>Welcome Message<h1></html>");
		mail.setBody("Body Message");
		mail.setFileLocation("C://Users//svalapi//Desktop//jp.pdf");
		email.send(mail);
		return helper.success(response);
	}

	// TODO : Remove after verifying
	@RequestMapping(value = "/sms", method = RequestMethod.GET, headers = "Accept=application/json")
	public UserSignOnResponse sms() {
		UserSignOnResponse response = new UserSignOnResponse();
		SmsMessage mail = new SmsMessage();
		mail.setMessage("Test Message");
		mail.setMobileNumber("9959377576");
		sms.send(mail);
		return helper.success(response);
	}
}
