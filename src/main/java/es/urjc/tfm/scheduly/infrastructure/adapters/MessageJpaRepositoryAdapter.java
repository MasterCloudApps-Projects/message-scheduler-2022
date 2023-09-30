package es.urjc.tfm.scheduly.infrastructure.adapters;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
	
	private ModelMapper mapper;
	
	public MessageJpaRepositoryAdapter(MessageJpaRepository messageJpaRepository) {
		this.messageJpaRepository = messageJpaRepository;
		this.mapper = new ModelMapper();
	}

	@Override
	public Optional<FullMessageDto> findById(Long id) {
		return Optional.of(mapper.map(
				this.messageJpaRepository.findById(id).orElseThrow(),
				FullMessageDto.class));
	}

	@Override
	public List<FullMessageDto> findAll() {
		return messageJpaRepository.findAll()
				.stream()
				.map(messageEntity -> mapper.map(messageEntity, FullMessageDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public FullMessageDto save(FullMessageDto fullMessageDto) {
		return mapper.map(
				this.messageJpaRepository.save(
						mapper.map(fullMessageDto,MessageEntity.class)),
				FullMessageDto.class);
	}
	
	@Override
	public void deleteById(Long id) {	
		this.messageJpaRepository.deleteById(id);
	}

	
	
}