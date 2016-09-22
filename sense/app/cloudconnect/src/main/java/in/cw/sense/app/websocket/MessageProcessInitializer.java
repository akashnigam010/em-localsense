package in.cw.sense.app.websocket;

import javax.websocket.Session;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import in.cw.csense.app.message.element.Message;
import in.cw.csense.app.message.element.MessageElement;
import in.cw.csense.app.message.element.MessageElementAdaptor;
import in.cw.csense.app.message.processor.MessageProcessorImpl;

@Component
public class MessageProcessInitializer {

	public void process(String message, Session session) {
		Message messageObj = getMessageObjectFromJson(message);
		final MessageProcessorImpl messageProcessor = new MessageProcessorImpl();
		messageObj.getMessageElement().accept(messageProcessor, session);
		
	}

	private Message getMessageObjectFromJson(String message) {
		 GsonBuilder builder = new GsonBuilder().registerTypeAdapter(MessageElement.class, new MessageElementAdaptor());
	     Gson gson = builder.create();
	     
	     return gson.fromJson(message, Message.class);
	}
}
