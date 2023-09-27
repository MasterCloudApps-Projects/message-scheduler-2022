package es.urjc.tfm.scheduly.domain.usecases;

import java.util.Optional;

import es.urjc.tfm.scheduly.domain.ports.MessageRepository;
import es.urjc.tfm.scheduly.domain.ports.MessageUseCase;
import es.urjc.tfm.scheduly.infrastructure.models.MessageEntity;

public class MessageUseCaseImpl implements MessageUseCase{

	private MessageRepository messageRepository;

	public MessageUseCaseImpl(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	public Optional<MessageEntity> findById(Long id){
		return messageRepository.findById(id);
	}

	@Override
	public MessageEntity createMessage(MessageEntity messageEntity) {
		return messageRepository.save(messageEntity);
	}
	
	
}