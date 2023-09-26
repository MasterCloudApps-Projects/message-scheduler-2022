package es.urjc.tfm.scheduly.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.tfm.scheduly.dtos.MessageRequestDto;
import es.urjc.tfm.scheduly.dtos.MessageResponseDto;
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
	
	public MessageResponseDto createMessage(MessageRequestDto message) {
		MessageEntity messageEntity = new MessageEntity( message.getMessageBody());
		messageEntity = messageRepository.save(messageEntity);
		return new MessageResponseDto(messageEntity.getId(),messageEntity.getMessageBody());
	}

	public MessageResponseDto getMessage(Long id) {
		MessageEntity messageEntity = messageRepository.findById(id).orElseThrow();
		return new MessageResponseDto(messageEntity.getId(),messageEntity.getMessageBody());
	}

}