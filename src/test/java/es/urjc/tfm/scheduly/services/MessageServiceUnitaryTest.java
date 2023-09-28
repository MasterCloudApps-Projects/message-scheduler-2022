package es.urjc.tfm.scheduly.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.urjc.tfm.scheduly.domain.ports.FullMessageDto;
import es.urjc.tfm.scheduly.domain.ports.MessageUseCase;
import es.urjc.tfm.scheduly.dtos.MessageRequestDto;
import es.urjc.tfm.scheduly.dtos.MessageResponseDto;
import es.urjc.tfm.scheduly.services.impl.MessageServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    	FullMessageDto savedMessageDto = new FullMessageDto(1L, "Random message body");
        
        when(messageUseCase.createMessage(any(FullMessageDto.class))).thenReturn(savedMessageDto);

        MessageResponseDto messageResponseDto = messageService.createMessage(messageRequestDto);

        assertEquals(savedMessageDto.getId(), messageResponseDto.getId());
        assertEquals(savedMessageDto.getMessageBody(), messageResponseDto.getMessageBody());
        verify(messageUseCase, times(1)).createMessage(any(FullMessageDto.class));
    }

    @Test
    public void testGetMessage() {
        Long messageId = 1L;
        FullMessageDto fullMessageDto = new FullMessageDto(messageId, "Random message body");
        
        when(messageUseCase.findById(messageId)).thenReturn(Optional.of(fullMessageDto));

        MessageResponseDto messageResponseDto = messageService.getMessage(messageId);

        assertEquals(fullMessageDto.getId(), messageResponseDto.getId());
        assertEquals(fullMessageDto.getMessageBody(), messageResponseDto.getMessageBody());
        verify(messageUseCase, times(1)).findById(messageId);
    }
}