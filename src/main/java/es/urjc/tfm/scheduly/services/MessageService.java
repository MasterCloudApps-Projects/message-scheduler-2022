package es.urjc.tfm.scheduly.services;

import es.urjc.tfm.scheduly.dtos.MessageRequestDto;
import es.urjc.tfm.scheduly.dtos.MessageResponseDto;

public interface MessageService {

	public MessageResponseDto createMessage(MessageRequestDto message);

	public MessageResponseDto getMessage(Long id);
}