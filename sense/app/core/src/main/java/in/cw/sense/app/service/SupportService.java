package in.cw.sense.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cwf.helper.ResponseHelper;
import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.response.StatusResponse;
import in.cw.sense.api.bo.support.request.MessageIdRequest;
import in.cw.sense.api.bo.support.request.SendMessageRequest;
import in.cw.sense.api.bo.support.response.SupportResponse;
import in.cw.sense.app.support.SupportDelegate;

@RestController
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('OWNER') or hasAuthority('MANAGER')")
@RequestMapping(value = "/support")
public class SupportService {
	@Autowired
	ResponseHelper helper;
	@Autowired
	SupportDelegate delegate;

	@RequestMapping(value = "/getSupportDetails", method = RequestMethod.GET, headers = "Accept=application/json")
	public SupportResponse getSupportDetails() {
		SupportResponse response = new SupportResponse();
		try {
			response = delegate.getMessages();
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}

	@RequestMapping(value = "/deleteMessage", method = RequestMethod.POST, headers = "Accept=application/json")
	public SupportResponse deleteMessage(@RequestBody MessageIdRequest request) {
		SupportResponse response = new SupportResponse();
		try {
			response = delegate.deleteMessage(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST, headers = "Accept=application/json")
	public SupportResponse sendMessage(@RequestBody SendMessageRequest request) {
		SupportResponse response = new SupportResponse();
		try {
			response = delegate.saveMessage(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
	
	@RequestMapping(value = "/markMessageAsRead", method = RequestMethod.POST, headers = "Accept=application/json")
	public StatusResponse markMessageAsRead(@RequestBody MessageIdRequest request) {
		StatusResponse response = new StatusResponse();
		try {
			delegate.markMessageAsRead(request);
			return helper.success(response);
		} catch (BusinessException e) {
			return helper.failure(response, e);
		}
	}
}
