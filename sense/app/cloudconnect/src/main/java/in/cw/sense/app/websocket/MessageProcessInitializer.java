package in.cw.sense.app.websocket;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import in.cw.csense.app.message.element.Message;
import in.cw.csense.app.message.element.MessageElement;
import in.cw.csense.app.message.element.MessageElementAdaptor;

@Component
public class MessageProcessInitializer {
	@Autowired LocalMessageProcessorImpl messageProcessor;

	public void process(String message, Session session) {
		Message messageObj = getMessageObjectFromJson(message);
		messageObj.getMessageElement().accept(messageProcessor, session);
	}

	private Message getMessageObjectFromJson(String message) {
		GsonBuilder builder = new GsonBuilder().registerTypeAdapter(MessageElement.class, new MessageElementAdaptor());
		Gson gson = builder.create();
		return gson.fromJson(message, Message.class);
	}
}
