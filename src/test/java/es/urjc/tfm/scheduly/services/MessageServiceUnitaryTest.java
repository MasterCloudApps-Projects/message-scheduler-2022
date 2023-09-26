package es.urjc.tfm.scheduly.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.urjc.tfm.scheduly.dtos.MessageRequestDto;
import es.urjc.tfm.scheduly.dtos.MessageResponseDto;
import es.urjc.tfm.scheduly.infrastructure.MessageRepository;
import es.urjc.tfm.scheduly.infrastructure.models.MessageEntity;
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
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageServiceImpl messageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateMessage() {
 
    	MessageRequestDto messageRequestDto = new MessageRequestDto("Random message body");
        MessageEntity savedMessageEntity = new MessageEntity(1L, "Random message body");
        
        when(messageRepository.save(any(MessageEntity.class))).thenReturn(savedMessageEntity);

        MessageResponseDto messageResponseDto = messageService.createMessage(messageRequestDto);

        assertEquals(savedMessageEntity.getId(), messageResponseDto.getId());
        assertEquals(savedMessageEntity.getMessageBody(), messageResponseDto.getMessageBody());
        verify(messageRepository, times(1)).save(any(MessageEntity.class));
    }

    @Test
    public void testGetMessage() {
        Long messageId = 1L;
        MessageEntity messageEntity = new MessageEntity(messageId, "Random message body");
        
        when(messageRepository.findById(messageId)).thenReturn(Optional.of(messageEntity));

        MessageResponseDto messageResponseDto = messageService.getMessage(messageId);

        assertEquals(messageEntity.getId(), messageResponseDto.getId());
        assertEquals(messageEntity.getMessageBody(), messageResponseDto.getMessageBody());
        verify(messageRepository, times(1)).findById(messageId);
    }
}