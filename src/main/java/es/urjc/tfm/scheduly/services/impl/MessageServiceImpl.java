package es.urjc.tfm.scheduly.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;

import es.urjc.tfm.scheduly.SchedulyFeatures;
import es.urjc.tfm.scheduly.domain.ports.FullMessageDto;
import es.urjc.tfm.scheduly.domain.ports.MessageUseCase;
import es.urjc.tfm.scheduly.dtos.MessageRequestDto;
import es.urjc.tfm.scheduly.dtos.MessageResponseDto;
import es.urjc.tfm.scheduly.services.MessageService;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	private MessageUseCase messageUseCase;
	
	@Autowired
	private FeatureManager featureManager;

	private ScheduledExecutorService executorService;
	
	private ModelMapper mapper;

	public MessageServiceImpl() {
		this.mapper = new ModelMapper();
        executorService = Executors.newScheduledThreadPool(1);
    }
	
	public MessageServiceImpl(MessageUseCase messageUseCase) {
		this.messageUseCase = messageUseCase;
		this.mapper = new ModelMapper();
		executorService = Executors.newScheduledThreadPool(1);
	}

	public MessageResponseDto createMessage(MessageRequestDto message) {
		return mapper.map(
				messageUseCase.createMessage(
						mapper.map(message,FullMessageDto.class)),
				MessageResponseDto.class);
	}

	public MessageResponseDto getMessage(Long id) {
		return mapper.map(
				messageUseCase.findById(id).orElseThrow(),
				MessageResponseDto.class);
	}

	public List<MessageResponseDto> getAllMessages() {
		return this.messageUseCase.findAll()
				.stream().map(message -> this.mapper.map(message, MessageResponseDto.class))
                .collect(Collectors.toList());
	}

	public void deleteMessage(Long id) {
		this.messageUseCase.deleteMessage(id);
	}

	@Override
	public MessageResponseDto scheduleMessage(MessageRequestDto messageRequestDto) {
		FullMessageDto fullMessageDto = new FullMessageDto(messageRequestDto.getMessageBody(), ZonedDateTime.of(
				messageRequestDto.getYear(), 
				messageRequestDto.getMonth(), 
				messageRequestDto.getDay(), 
				messageRequestDto.getHour(), 
				messageRequestDto.getMinute(),
				0, 0, ZoneId.of("Europe/Madrid")));
		if(featureManager!=null&&featureManager.isActive(SchedulyFeatures.SCHEDULE_MESSAGE_SCHEDULER)) {
			LocalDateTime executionTime = LocalDateTime.of(messageRequestDto.getYear(), 
					messageRequestDto.getMonth(), 
					messageRequestDto.getDay(), 
					messageRequestDto.getHour(), 
					messageRequestDto.getMinute(),0);
			Runnable task = () -> {
				System.out.println("Message sent from scheduler: " + messageRequestDto.getMessageBody());
			};
			
		    long initialWait = executionTime.toInstant(ZoneOffset.UTC).toEpochMilli() - System.currentTimeMillis();
		    if (initialWait >= 0) {
				executorService.schedule(task, initialWait, TimeUnit.MILLISECONDS);
			}

		}
		return mapper.map(
				messageUseCase.createMessage(fullMessageDto),
				MessageResponseDto.class);
		

		
	}
}