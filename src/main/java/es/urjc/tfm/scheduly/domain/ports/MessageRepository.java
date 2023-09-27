package es.urjc.tfm.scheduly.domain.ports;

import java.util.Optional;

import es.urjc.tfm.scheduly.infrastructure.models.MessageEntity;

public interface MessageRepository {
	
	public Optional<MessageEntity> findById(Long id);

	public MessageEntity save(MessageEntity messageEntity);
	
}
