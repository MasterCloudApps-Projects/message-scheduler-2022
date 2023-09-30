package es.urjc.tfm.scheduly.services.impl;

import org.modelmapper.ModelMapper;
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
	
	private ModelMapper mapper;
	public MessageServiceImpl(MessageUseCase messageUseCase) {
		this.messageUseCase = messageUseCase;
		this.mapper = new ModelMapper();
	}

	public MessageResponseDto createMessage(MessageRequestDto message) {
		return mapper.map(
				messageUseCase.createMessage(
						mapper.map(message,FullMessageDto.class)),
				MessageResponseDto.class);
	}

	public MessageResponseDto getMessage(Long id) {
		return mapper.map(
				messageUseCase.findById(id).orElseThrow(),
				MessageResponseDto.class);
	}

	public void deleteMessage(Long id) {
		this.messageUseCase.deleteMessage(id);
	}
}