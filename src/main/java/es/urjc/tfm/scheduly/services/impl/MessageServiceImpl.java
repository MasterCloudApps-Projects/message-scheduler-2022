package es.urjc.tfm.scheduly.services.impl;

import org.springframework.stereotype.Service;

import es.urjc.tfm.scheduly.domain.Message;
import es.urjc.tfm.scheduly.services.MessageService;

@Service
public class MessageServiceImpl implements MessageService{

	public Message createMessage(Message message) {
		message.setId((long) 1);
		return message;
	}

	public Message findById(Long id) {
		Message message = new Message((long) 1,"some random message");
		return message;
	}

}