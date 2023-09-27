package es.urjc.tfm.scheduly.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.urjc.tfm.scheduly.domain.ports.MessageUseCase;
import es.urjc.tfm.scheduly.dtos.MessageRequestDto;
import es.urjc.tfm.scheduly.dtos.MessageResponseDto;
import es.urjc.tfm.scheduly.infrastructure.models.MessageEntity;
import es.urjc.tfm.scheduly.services.MessageService;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	private MessageUseCase messageUseCase;
	
	public MessageServiceImpl(MessageUseCase messageUseCase) {
		this.messageUseCase = messageUseCase;
	}

	public MessageResponseDto createMessage(MessageRequestDto message) {
		MessageEntity messageEntity = new MessageEntity( message.getMessageBody());
		messageEntity = messageUseCase.createMessage(messageEntity);

		return new MessageResponseDto(messageEntity.getId(),messageEntity.getMessageBody());
	}

	public MessageResponseDto getMessage(Long id) {
		MessageEntity messageEntity;
		messageEntity = messageUseCase.findById(id).orElseThrow();
		
		return new MessageResponseDto(messageEntity.getId(),messageEntity.getMessageBody());
	}

}