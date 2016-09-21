package in.cw.sense.app.support;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import cwf.dbhelper.SenseContext;
import cwf.helper.exception.BusinessException;
import cwf.helper.type.GenericErrorCodeType;
import in.cw.sense.api.bo.support.dto.ContactSupportDto;
import in.cw.sense.api.bo.support.dto.MessageDto;
import in.cw.sense.api.bo.support.entity.ContactSupportEntity;
import in.cw.sense.api.bo.support.entity.Message;
import in.cw.sense.api.bo.support.request.MessageIdRequest;
import in.cw.sense.api.bo.support.request.SendMessageRequest;
import in.cw.sense.app.support.mapper.SupportMapper;

@Repository
public class SupportDao {
	@Autowired SenseContext context;
	@Autowired SupportMapper mapper;
	@Autowired MongoTemplate senseMongoTemplate;
	
	private static final Logger LOG = Logger.getLogger(SupportDao.class);
	private static final String ID = "_id";
	private static final String MESSAGE_SEQ = "message_seq";
	
	public void setSenseMongoTemplate(MongoTemplate senseMongoTemplate) {
		this.senseMongoTemplate = senseMongoTemplate;
	}

	public List<MessageDto> getMessages() throws BusinessException {
		try {
			return mapper.mapEntitiesToDtos(senseMongoTemplate.findAll(Message.class));
		} catch (Exception e) {
			LOG.error("Exception occured while fetching all bill details", e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}
	
	public List<MessageDto> deleteMessage(MessageIdRequest request) throws BusinessException {
		try {
			Query findQuery = Query.query(Criteria.where(ID).is(request.getId()));
			senseMongoTemplate.findAndRemove(findQuery, Message.class);
			return getMessages();
		} catch (Exception e) {
			LOG.error("Exception occured while deleting message", e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}
	
	public List<MessageDto> saveMessage(SendMessageRequest request) throws BusinessException {
		try {
			Message message = mapper.mapMessageEntityDetails(request);
			int messageId = (int) context.getNextSequenceId(MESSAGE_SEQ);
			message.setId(messageId);
			senseMongoTemplate.save(message);
			return getMessages();
		} catch (Exception e) {
			LOG.error("Exception occured while saving message", e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}

	public ContactSupportDto getContactSupportInfo() throws BusinessException {
		try {
			return mapper.mapContactSupportEntityToDto(senseMongoTemplate.findAll(ContactSupportEntity.class).get(0));
		} catch (Exception e) {
			LOG.error("Exception occured while fetching Contact Support Information", e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}

	public void markMessageAsRead(MessageIdRequest request) throws BusinessException {
		try {
			Message message = senseMongoTemplate.findById(request.getId(), Message.class);
			message.setIsNew(false);
			senseMongoTemplate.save(message);
		} catch (Exception e) {
			LOG.error("Exception occured while deleting message", e);
			if (e instanceof BusinessException) {
				throw e;
			}
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR, e.getMessage());
		}
	}

}
