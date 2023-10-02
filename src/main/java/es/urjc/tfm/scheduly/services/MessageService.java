package es.urjc.tfm.scheduly.services;

import java.util.List;

import es.urjc.tfm.scheduly.dtos.MessageRequestDto;
import es.urjc.tfm.scheduly.dtos.MessageResponseDto;
import es.urjc.tfm.scheduly.exceptions.OutOfDateException;
import es.urjc.tfm.scheduly.exceptions.WrongParamsException;

public interface MessageService {

	public MessageResponseDto createMessage(MessageRequestDto message);

	public MessageResponseDto getMessage(Long id);
	
	public List<MessageResponseDto> getAllMessages();

	public void deleteMessage(Long id);

	public MessageResponseDto scheduleMessage(MessageRequestDto messageRequestDto) throws WrongParamsException, OutOfDateException;
	 
}