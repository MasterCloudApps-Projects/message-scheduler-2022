package es.urjc.tfm.scheduly.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.urjc.tfm.scheduly.infrastructure.adapters.MessageJpaRepositoryAdapter;
import es.urjc.tfm.scheduly.infrastructure.models.MessageEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MessageJpaRepositoryUnitaryTest {

    @Mock
    private MessageJpaRepository messageJpaRepository;

    private MessageJpaRepositoryAdapter messageJpaRepositoryAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); 
        messageJpaRepositoryAdapter = new MessageJpaRepositoryAdapter(messageJpaRepository);
    }

    @Test
    public void findByIdTest() {
        Long messageId = 1L;
        MessageEntity expectedMessage = new MessageEntity(messageId,"Random message body");
        when(messageJpaRepository.findById(messageId)).thenReturn(Optional.of(expectedMessage));

        Optional<MessageEntity> result = messageJpaRepositoryAdapter.findById(messageId);

        assertEquals(expectedMessage, result.orElse(null));
    }

    @Test
    public void saveTest() {
    	MessageEntity messageToSave = new MessageEntity("Random message body");
        when(messageJpaRepository.save(messageToSave)).thenReturn(messageToSave);

        MessageEntity result = messageJpaRepositoryAdapter.save(messageToSave);

        assertEquals(messageToSave, result);
    }
}
