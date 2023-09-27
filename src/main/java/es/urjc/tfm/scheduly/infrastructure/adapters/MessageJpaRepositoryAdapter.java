package es.urjc.tfm.scheduly.infrastructure.adapters;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.urjc.tfm.scheduly.domain.ports.MessageRepository;
import es.urjc.tfm.scheduly.infrastructure.MessageJpaRepository;
import es.urjc.tfm.scheduly.infrastructure.models.MessageEntity;

@Component
public class MessageJpaRepositoryAdapter implements MessageRepository{

	@Autowired
	private MessageJpaRepository messageJpaRepository;
	
	public MessageJpaRepositoryAdapter(MessageJpaRepository messageJpaRepository) {
		this.messageJpaRepository = messageJpaRepository;
	}

	@Override
	public Optional<MessageEntity> findById(Long id) {
		return this.messageJpaRepository.findById(id);
	}

	@Override
	public MessageEntity save(MessageEntity messageEntity) {
		
		return this.messageJpaRepository.save(messageEntity);
	}
	
}