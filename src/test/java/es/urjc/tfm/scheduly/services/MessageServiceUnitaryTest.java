package es.urjc.tfm.scheduly.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.urjc.tfm.scheduly.domain.ports.FullMessageDto;
import es.urjc.tfm.scheduly.domain.ports.MessageUseCase;
import es.urjc.tfm.scheduly.dtos.MessageRequestDto;
import es.urjc.tfm.scheduly.dtos.MessageResponseDto;
import es.urjc.tfm.scheduly.exceptions.WrongParamsException;
import es.urjc.tfm.scheduly.services.impl.MessageServiceImpl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MessageServiceUnitaryTest {

    @Mock
    private MessageUseCase messageUseCase;

    @InjectMocks
    private MessageServiceImpl messageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateMessage() {
 
    	MessageRequestDto messageRequestDto = new MessageRequestDto("Random message body");
    	FullMessageDto savedMessageDto = new FullMessageDto(1L, "Random message body", null, null);
        
        when(messageUseCase.createMessage(any(FullMessageDto.class))).thenReturn(savedMessageDto);

        MessageResponseDto messageResponseDto = messageService.createMessage(messageRequestDto);

        assertEquals(savedMessageDto.getId(), messageResponseDto.getId());
        assertEquals(savedMessageDto.getMessageBody(), messageResponseDto.getMessageBody());
        verify(messageUseCase, times(1)).createMessage(any(FullMessageDto.class));
    }

    @Test
    public void testGetMessage() {
        Long messageId = 1L;
        ZonedDateTime executionTime = ZonedDateTime.of(2024, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid"));
        LocalDateTime serverExecutionTime = executionTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        FullMessageDto fullMessageDto = new FullMessageDto(messageId, "Random message body", executionTime, serverExecutionTime);
        
        when(messageUseCase.findById(messageId)).thenReturn(Optional.of(fullMessageDto));

        MessageResponseDto messageResponseDto = messageService.getMessage(messageId);

        assertEquals(fullMessageDto.getId(), messageResponseDto.getId());
        assertEquals(fullMessageDto.getMessageBody(), messageResponseDto.getMessageBody());
        verify(messageUseCase, times(1)).findById(messageId);
    }
    
    @Test
    public void testGetAllMessage() {
        List<FullMessageDto> fullMessageDtoList = new ArrayList<>();
        Long messageId1 = 1L;
        ZonedDateTime executionTime1 = ZonedDateTime.of(2024, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid"));
        LocalDateTime serverExecutionTime1 = executionTime1.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        FullMessageDto fullMessageDto1 = new FullMessageDto(messageId1,"Random message body number 1", executionTime1, serverExecutionTime1);
        Long messageId2 = 2L;
        ZonedDateTime executionTime2 = ZonedDateTime.of(2024, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid"));
        LocalDateTime serverExecutionTime2 = executionTime1.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        FullMessageDto fullMessageDto2 = new FullMessageDto(messageId2,"Random message body number 2", executionTime2, serverExecutionTime2);
        fullMessageDtoList.add(fullMessageDto1);
        fullMessageDtoList.add(fullMessageDto2);
        
        
        when(messageUseCase.findAll()).thenReturn(fullMessageDtoList);

        List<MessageResponseDto> messageResponseDto = messageService.getAllMessages();

        assertEquals(fullMessageDto1.getId(), messageResponseDto.get(0).getId());
        assertEquals(fullMessageDto1.getMessageBody(), messageResponseDto.get(0).getMessageBody());
        assertEquals(fullMessageDto2.getId(), messageResponseDto.get(1).getId());
        assertEquals(fullMessageDto2.getMessageBody(), messageResponseDto.get(1).getMessageBody());
        
    }
    
    @Test
    public void testScheduleMessage() throws WrongParamsException {
 
    	MessageRequestDto messageRequestDto = new MessageRequestDto("Random message body", 2024, 9, 24, 10, 10);
        ZonedDateTime executionTime = ZonedDateTime.of(2024, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid"));
        LocalDateTime serverExecutionTime = executionTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
		
    	FullMessageDto savedMessageDto = new FullMessageDto(1L, "Random message body", executionTime, serverExecutionTime);
        
        when(messageUseCase.createMessage(any(FullMessageDto.class))).thenReturn(savedMessageDto);

        MessageResponseDto messageResponseDto = messageService.scheduleMessage(messageRequestDto);

        assertEquals(savedMessageDto.getId(), messageResponseDto.getId());
        assertEquals(savedMessageDto.getMessageBody(), messageResponseDto.getMessageBody());
    }
    
    @Test
    public void testScheduleMessageWrongParamsException() throws WrongParamsException {
 
        assertThrows(WrongParamsException.class, () -> {
        	messageService.scheduleMessage(new MessageRequestDto(null, 2024, 9, 24, 10, 10));
        });
        
        assertThrows(WrongParamsException.class, () -> {
        	messageService.scheduleMessage(new MessageRequestDto("Random message body", 202, 9, 24, 10, 10));
        });
        
    }
}