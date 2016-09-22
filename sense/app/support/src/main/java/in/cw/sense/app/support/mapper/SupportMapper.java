package in.cw.sense.app.support.mapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cwf.date.format.DateFormatType;
import cwf.util.CwfDayUtil;
import cwf.util.CwfTimeUtil;
import in.cw.sense.api.bo.support.dto.ContactSupportDto;
import in.cw.sense.api.bo.support.dto.MessageDto;
import in.cw.sense.api.bo.support.entity.ContactSupportEntity;
import in.cw.sense.api.bo.support.entity.Message;
import in.cw.sense.api.bo.support.request.SendMessageRequest;
import in.cw.sense.api.bo.support.type.EntityType;

@Component
public class SupportMapper {

	@Autowired
	CwfTimeUtil timeUtil;
	@Autowired
	CwfDayUtil dayUtil;

	public List<MessageDto> mapEntitiesToDtos(List<Message> entities) {
		List<MessageDto> dtos = new ArrayList<>();
		for (Message entity : entities) {
			dtos.add(mapEntityToDto(entity));
		}
		return dtos;
	}

	public MessageDto mapEntityToDto(Message entity) {
		MessageDto dto = new MessageDto();
		dto.setId(entity.getId());
		Calendar date = timeUtil.toCalendar(entity.getDate());
		dto.setDateIso(dayUtil.formatDate(date, DateFormatType.ISO_DATE_FORMAT.getByFormat()));
		dto.setDate(dayUtil.formatDate(date, DateFormatType.DD_MM_YYYY.getByFormat()));
		dto.setTo(entity.getTo());
		dto.setFrom(entity.getFrom());
		dto.setIsNew(entity.getIsNew());
		dto.setSubject(entity.getSubject());
		dto.setMessageBody(entity.getMessageBody());
		return dto;
	}

	public Message mapMessageEntityDetails(SendMessageRequest request) {
		Message entity = new Message();
		entity.setDate(new Date());
		entity.setTo(EntityType.SUPPORT_CENTER.getDesc());
		entity.setFrom(EntityType.RESTAURANT.getDesc());
		entity.setIsNew(false);
		entity.setSubject(request.getSubject());
		entity.setMessageBody(request.getMessageBody());
		return entity;
	}

	public ContactSupportDto mapContactSupportEntityToDto(ContactSupportEntity entity) {
		ContactSupportDto dto = new ContactSupportDto();
		dto.setContactNumber(entity.getContactNumber());
		dto.setEmail(entity.getEmail());
		return dto;
	}

}
