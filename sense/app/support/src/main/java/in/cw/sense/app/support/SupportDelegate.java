package in.cw.sense.app.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cwf.helper.exception.BusinessException;
import in.cw.sense.api.bo.support.dto.ContactSupportDto;
import in.cw.sense.api.bo.support.request.MessageIdRequest;
import in.cw.sense.api.bo.support.request.SendMessageRequest;
import in.cw.sense.api.bo.support.response.SupportResponse;
import in.cw.sense.app.support.mapper.SupportMapper;

@Service
public class SupportDelegate {
	@Autowired
	SupportDao dao;
	@Autowired
	SupportMapper mapper;

	public SupportResponse getMessages() throws BusinessException {
		SupportResponse response = new SupportResponse();
		response.setMessages(dao.getMessages());
		//TODO: Keep Contact Support Info in Cache
		ContactSupportDto contactSupport = dao.getContactSupportInfo();
		response.setContactNumber(contactSupport.getContactNumber());
		response.setEmailAddress(contactSupport.getEmail());
		return response;
	}
	
	public SupportResponse deleteMessage(MessageIdRequest request) throws BusinessException {
		SupportResponse response = new SupportResponse();
		response.setMessages(dao.deleteMessage(request));
		//TODO: Keep Contact Support Info in Cache
		ContactSupportDto contactSupport = dao.getContactSupportInfo();
		response.setContactNumber(contactSupport.getContactNumber());
		response.setEmailAddress(contactSupport.getEmail());
		return response;
	}
	
	public SupportResponse saveMessage(SendMessageRequest request) throws BusinessException {
		SupportResponse response = new SupportResponse();
		//TODO: implement logic to send message to cloud
		response.setMessages(dao.saveMessage(request));
		//TODO: Keep Contact Support Info in Cache
		ContactSupportDto contactSupport = dao.getContactSupportInfo();
		response.setContactNumber(contactSupport.getContactNumber());
		response.setEmailAddress(contactSupport.getEmail());
		return response;
	}

	public void markMessageAsRead(MessageIdRequest request) throws BusinessException {
		dao.markMessageAsRead(request);
	}
}
