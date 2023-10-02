package es.urjc.tfm.scheduly.infrastructure.adapters;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.urjc.tfm.scheduly.domain.ports.FullMessageDto;
import es.urjc.tfm.scheduly.domain.ports.MessageRepository;
import es.urjc.tfm.scheduly.infrastructure.models.MessageEntityMongo;
import es.urjc.tfm.scheduly.infrastructure.MessageMongoRepository;

@Component
public class MessageMongoRepositoryAdapter implements MessageRepository{

	@Autowired
	private MessageMongoRepository messageMongoRepository;
	
	private ModelMapper mapper;
	
	public MessageMongoRepositoryAdapter(MessageMongoRepository messageMongoRepository, ModelMapper mapper) {
		this.messageMongoRepository = messageMongoRepository;
		this.mapper = mapper;
	}

	@Override
	public Optional<FullMessageDto> findById(Long id) {
		return messageMongoRepository.findById(id)
				.map(message -> mapper.map(message, FullMessageDto.class));
	}

	@Override
	public List<FullMessageDto> findAll() {
		return messageMongoRepository.findAll()
				.stream()
				.map(messageEntity -> mapper.map(messageEntity, FullMessageDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public FullMessageDto save(FullMessageDto fullMessageDto) {
		if(fullMessageDto.getId() == null){fullMessageDto.setId(System.currentTimeMillis());}
		return mapper.map(
				this.messageMongoRepository.save(mapper.map(fullMessageDto,MessageEntityMongo.class)),
				FullMessageDto.class);
	}

	@Override
	public void deleteById(Long id) {
		this.messageMongoRepository.deleteById(id);
	}
	
}