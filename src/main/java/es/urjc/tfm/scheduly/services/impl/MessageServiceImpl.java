package es.urjc.tfm.scheduly.services.impl;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

	public List<MessageResponseDto> getAllMessages() {
		return this.messageUseCase.findAll()
				.stream().map(message -> this.mapper.map(message, MessageResponseDto.class))
                .collect(Collectors.toList());
	}

	public void deleteMessage(Long id) {
		this.messageUseCase.deleteMessage(id);
	}

	@Override
	public MessageResponseDto scheduleMessage(MessageRequestDto messageRequestDto) {
		FullMessageDto fullMessageDto = new FullMessageDto(messageRequestDto.getMessageBody(), ZonedDateTime.of(
				messageRequestDto.getYear(), 
				messageRequestDto.getMonth(), 
				messageRequestDto.getDay(), 
				messageRequestDto.getHour(), 
				messageRequestDto.getMinute(),
				0, 0, ZoneId.of("Europe/Madrid")));
		
		return  mapper.map(
				messageUseCase.createMessage(fullMessageDto),
				MessageResponseDto.class);
	}
}