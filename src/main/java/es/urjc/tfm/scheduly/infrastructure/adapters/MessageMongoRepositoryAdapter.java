package es.urjc.tfm.scheduly.infrastructure.adapters;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.urjc.tfm.scheduly.MongoIdGenerator;
import es.urjc.tfm.scheduly.domain.ports.FullMessageDto;
import es.urjc.tfm.scheduly.domain.ports.MessageRepository;
import es.urjc.tfm.scheduly.infrastructure.models.IdEntity;
import es.urjc.tfm.scheduly.infrastructure.models.MessageEntityMongo;
import es.urjc.tfm.scheduly.infrastructure.MessageMongoRepository;
import es.urjc.tfm.scheduly.infrastructure.UniqueIdRepository;

@Component
public class MessageMongoRepositoryAdapter implements MessageRepository{

	@Autowired
	private MessageMongoRepository messageMongoRepository;
	
	@Autowired
	private UniqueIdRepository uniqueIdRepository;

	private ModelMapper mapper;
	public MessageMongoRepositoryAdapter() {}
	public MessageMongoRepositoryAdapter(MessageMongoRepository messageMongoRepository) {
		this.messageMongoRepository = messageMongoRepository;
		this.mapper = new ModelMapper();
	}

	public MessageMongoRepositoryAdapter(MessageMongoRepository messageMongoRepository,
			UniqueIdRepository uniqueIdRepository) {
		this.messageMongoRepository = messageMongoRepository;
		this.uniqueIdRepository = uniqueIdRepository;
		this.mapper = new ModelMapper();
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
		if(fullMessageDto.getId() == null){
			fullMessageDto.setId(generateId());
			}
		return mapper.map(
				this.messageMongoRepository.save(mapper.map(fullMessageDto,MessageEntityMongo.class)),
				FullMessageDto.class);
	}

	@Override
	public void deleteById(Long id) {
		this.messageMongoRepository.deleteById(id);
	}
	
	protected Long generateId() {
		List<IdEntity> idList = uniqueIdRepository.findAll();
		Long newId = 1L;
		IdEntity idEntity;
		if(!idList.isEmpty()) {
			idEntity = idList.get(0);
			newId = idEntity.getUniqueId()+1;
		}else idEntity = new IdEntity();
		idEntity.setUniqueId(newId);
		uniqueIdRepository.save(idEntity);
		return newId;
	}
	
}