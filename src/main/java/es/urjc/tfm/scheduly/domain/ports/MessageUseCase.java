package es.urjc.tfm.scheduly.domain.ports;

import java.util.Optional;

import es.urjc.tfm.scheduly.infrastructure.models.MessageEntity;

public interface MessageUseCase {

	public Optional<MessageEntity> findById(Long id);

	public MessageEntity createMessage(MessageEntity messageEntity);
	
}
