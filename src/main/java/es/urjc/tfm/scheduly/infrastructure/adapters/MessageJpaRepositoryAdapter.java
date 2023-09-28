package es.urjc.tfm.scheduly.infrastructure.adapters;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.urjc.tfm.scheduly.domain.ports.FullMessageDto;
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
	public Optional<FullMessageDto> findById(Long id) {
		MessageEntity messageEntity = this.messageJpaRepository.findById(id).orElseThrow();
		return Optional.of(new FullMessageDto(messageEntity.getId(),messageEntity.getMessageBody()));
	}

	@Override
	public FullMessageDto save(FullMessageDto fullMessageDto) {
		MessageEntity messageEntity = new MessageEntity(fullMessageDto.getMessageBody());
		messageEntity = this.messageJpaRepository.save(messageEntity);
		return new FullMessageDto(messageEntity.getId(),messageEntity.getMessageBody());
	}
	
}