package es.urjc.tfm.scheduly.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.tfm.scheduly.domain.Message;
import es.urjc.tfm.scheduly.infrastructure.MessageRepository;
import es.urjc.tfm.scheduly.infrastructure.models.MessageEntity;
import es.urjc.tfm.scheduly.services.MessageService;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	private MessageRepository messageRepository;
	
	public MessageServiceImpl(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	public Message createMessage(Message message) {
		MessageEntity messageEntity = new MessageEntity( message.getMessageBody());
		messageEntity = messageRepository.save(messageEntity);
		return new Message(messageEntity.getId(),messageEntity.getMessageBody());
	}

	public Message getMessage(Long id) {
		MessageEntity messageEntity = messageRepository.findById(id).orElseThrow();
		return new Message(messageEntity.getId(),messageEntity.getMessageBody());
	}

}