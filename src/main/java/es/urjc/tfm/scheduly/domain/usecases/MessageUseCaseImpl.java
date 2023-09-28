package es.urjc.tfm.scheduly.domain.usecases;

import java.util.Optional;

import es.urjc.tfm.scheduly.domain.ports.FullMessageDto;
import es.urjc.tfm.scheduly.domain.ports.MessageRepository;
import es.urjc.tfm.scheduly.domain.ports.MessageUseCase;

public class MessageUseCaseImpl implements MessageUseCase{

	private MessageRepository messageRepository;

	public MessageUseCaseImpl(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	public Optional<FullMessageDto> findById(Long id){
		return messageRepository.findById(id);
	}

	@Override
	public FullMessageDto createMessage(FullMessageDto fullMessageDto) {
		return messageRepository.save(fullMessageDto);
	}
	
	
}