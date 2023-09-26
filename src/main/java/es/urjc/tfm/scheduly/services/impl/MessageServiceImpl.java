package es.urjc.tfm.scheduly.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;

import es.urjc.tfm.scheduly.infrastructure.MessageRepository;
import es.urjc.tfm.scheduly.SchedulyFeatures;
import es.urjc.tfm.scheduly.domain.ports.MessageUseCase;
import es.urjc.tfm.scheduly.dtos.MessageRequestDto;
import es.urjc.tfm.scheduly.dtos.MessageResponseDto;
import es.urjc.tfm.scheduly.infrastructure.models.MessageEntity;
import es.urjc.tfm.scheduly.services.MessageService;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	private FeatureManager featureManager;
	
	@Autowired
	private MessageRepository messageRepository;
	
	public MessageServiceImpl(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@Autowired
	private MessageUseCase messageUseCase;
	
	public MessageResponseDto createMessage(MessageRequestDto message) {
		MessageEntity messageEntity = new MessageEntity( message.getMessageBody());
		if(featureManager!=null&&featureManager.isActive(SchedulyFeatures.MESSAGE_USE_CASE) ) {
			messageEntity = messageUseCase.createMessage(messageEntity);
		}else {
			messageEntity = messageRepository.save(messageEntity);
		}
		return new MessageResponseDto(messageEntity.getId(),messageEntity.getMessageBody());
	}

	public MessageResponseDto getMessage(Long id) {
		MessageEntity messageEntity;
		if(featureManager!=null&&featureManager.isActive(SchedulyFeatures.MESSAGE_USE_CASE) ) {
			messageEntity = messageUseCase.findById(id).orElseThrow();
		}else{
			messageEntity = messageRepository.findById(id).orElseThrow();
		}
		return new MessageResponseDto(messageEntity.getId(),messageEntity.getMessageBody());
	}

}