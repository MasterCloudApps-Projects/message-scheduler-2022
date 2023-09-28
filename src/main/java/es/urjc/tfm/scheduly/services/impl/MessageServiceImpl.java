package es.urjc.tfm.scheduly.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.tfm.scheduly.domain.ports.FullMessageDto;
import es.urjc.tfm.scheduly.domain.ports.MessageUseCase;
import es.urjc.tfm.scheduly.dtos.MessageRequestDto;
import es.urjc.tfm.scheduly.dtos.MessageResponseDto;
import es.urjc.tfm.scheduly.services.MessageService;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	private MessageUseCase messageUseCase;
	
	public MessageServiceImpl(MessageUseCase messageUseCase) {
		this.messageUseCase = messageUseCase;
	}

	public MessageResponseDto createMessage(MessageRequestDto message) {
		FullMessageDto fullMessageDto = new FullMessageDto(message.getMessageBody());
		fullMessageDto = messageUseCase.createMessage(fullMessageDto);
		return new MessageResponseDto(fullMessageDto.getId(),fullMessageDto.getMessageBody());
	}

	public MessageResponseDto getMessage(Long id) {
		FullMessageDto fullMessageDto = messageUseCase.findById(id).orElseThrow();
		return new MessageResponseDto(fullMessageDto.getId(),fullMessageDto.getMessageBody());
	}

	public void deleteMessage(Long id) {
		this.messageUseCase.deleteMessage(id);
	}
}