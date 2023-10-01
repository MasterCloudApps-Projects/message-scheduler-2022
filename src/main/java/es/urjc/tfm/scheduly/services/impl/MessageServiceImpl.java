package es.urjc.tfm.scheduly.services.impl;

import java.io.IOException;
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

import com.slack.api.methods.SlackApiException;

import es.urjc.tfm.scheduly.domain.ports.ComunicationUseCase;
import es.urjc.tfm.scheduly.domain.ports.FullMessageDto;
import es.urjc.tfm.scheduly.domain.ports.MessageUseCase;
import es.urjc.tfm.scheduly.dtos.MessageRequestDto;
import es.urjc.tfm.scheduly.dtos.MessageResponseDto;
import es.urjc.tfm.scheduly.exceptions.WrongParamsException;
import es.urjc.tfm.scheduly.services.MessageService;

@Service
public class MessageServiceImpl implements MessageService{

	private MessageUseCase messageUseCase;

	private ComunicationUseCase comunicationUseCase;
	
	private ScheduledExecutorService executorService;
	
	private ModelMapper mapper;

	public MessageServiceImpl() {
		this.mapper = new ModelMapper();
        executorService = Executors.newScheduledThreadPool(1);
    }
	
	@Autowired
	public MessageServiceImpl(MessageUseCase messageUseCase, ComunicationUseCase comunicationUseCase) {
		this.messageUseCase = messageUseCase;
		this.comunicationUseCase = comunicationUseCase;
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
	public MessageResponseDto scheduleMessage(MessageRequestDto messageRequestDto) throws WrongParamsException {
		FullMessageDto fullMessageDto = generateFullMessageDto(messageRequestDto);
		
		MessageResponseDto messageResponseDto = mapper.map(
				messageUseCase.createMessage(fullMessageDto),
				MessageResponseDto.class);
		Runnable task = () -> {
				try {
					this.comunicationUseCase.sendMessage(messageResponseDto.getMessageBody());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SlackApiException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
			
		    long initialWait = fullMessageDto.getServerExecutionTime().toInstant(ZoneOffset.UTC).toEpochMilli() - System.currentTimeMillis();
		    if (initialWait >= 0) {
				executorService.schedule(task, initialWait, TimeUnit.MILLISECONDS);
			}

		return messageResponseDto;
	}

	private ZonedDateTime calculeExecutionDate(MessageRequestDto messageRequestDto) {
		return ZonedDateTime.of(
				messageRequestDto.getYear(), 
				messageRequestDto.getMonth(), 
				messageRequestDto.getDay(), 
				messageRequestDto.getHour(), 
				messageRequestDto.getMinute(),
				0, 0, ZoneId.of("Europe/Madrid")
		   );
	}

	private boolean rightParams(MessageRequestDto messageRequestDto) {
		return  messageRequestDto.getMessageBody() != null && 
				messageRequestDto.getMessageBody()instanceof String &&
				messageRequestDto.getYear() > 2000 &&
				messageRequestDto.getMonth() >= 1 && messageRequestDto.getMonth() <= 12 &&
				messageRequestDto.getDay() >= 1 && messageRequestDto.getDay() <= 31 &&
				messageRequestDto.getHour() >= 0 && messageRequestDto.getHour() <= 23 &&
				messageRequestDto.getMinute() >= 0 && messageRequestDto.getMinute() <= 59 ;
	}

	private FullMessageDto generateFullMessageDto(MessageRequestDto messageRequestDto) throws WrongParamsException{
		ZonedDateTime executionTimeDate = this.calculeExecutionDate(messageRequestDto);
		LocalDateTime serverExecutionTime = executionTimeDate.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
		if (!rightParams(messageRequestDto)) {
			throw new WrongParamsException();
		}
		return new FullMessageDto(messageRequestDto.getMessageBody(), executionTimeDate, serverExecutionTime);
	}
}