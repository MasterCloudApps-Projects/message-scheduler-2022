package es.urjc.tfm.scheduly.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.urjc.tfm.scheduly.domain.ports.FullMessageDto;
import es.urjc.tfm.scheduly.infrastructure.adapters.MessageJpaRepositoryAdapter;
import es.urjc.tfm.scheduly.infrastructure.models.MessageEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
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
        MessageEntity expectedMessage = new MessageEntity(messageId,"Random message body", ZonedDateTime.of(2023, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid")));
        when(messageJpaRepository.findById(messageId)).thenReturn(Optional.of(expectedMessage));

        Optional<FullMessageDto> result = messageJpaRepositoryAdapter.findById(messageId);

        assertEquals(expectedMessage.getMessageBody(), result.get().getMessageBody());
        assertEquals(expectedMessage.getExecutionTime(), result.get().getExecutionTime());
    }

    @Test
    public void findAllTest() {
        Long messageId = 1L;
        Long messageId2 = 2L;
        List<MessageEntity> entityList = new ArrayList<>();
        MessageEntity expectedMessage1 = new MessageEntity(messageId,"Random message body number 1", ZonedDateTime.of(2023, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid")));
        MessageEntity expectedMessage2 = new MessageEntity(messageId2,"Random message body number 2", ZonedDateTime.of(2023, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid")));
        entityList.add(expectedMessage1);
        entityList.add(expectedMessage2);

        when(messageJpaRepository.findAll()).thenReturn(entityList);

        List<FullMessageDto> result = messageJpaRepositoryAdapter.findAll();

        assertEquals(expectedMessage1.getId(), result.get(0).getId());
        assertEquals(expectedMessage2.getId(), result.get(1).getId());
        assertEquals(expectedMessage1.getMessageBody(), result.get(0).getMessageBody());
        assertEquals(expectedMessage2.getMessageBody(), result.get(1).getMessageBody());
        assertEquals(expectedMessage1.getExecutionTime(), result.get(0).getExecutionTime());
        assertEquals(expectedMessage2.getExecutionTime(), result.get(1).getExecutionTime());
    }

    @Test
    public void saveTest() {
    	FullMessageDto messageToSaveDto = new FullMessageDto("Random message body");
    	MessageEntity messageToSave = new MessageEntity(1L,"Random message body", ZonedDateTime.of(2023, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid")));
        when(messageJpaRepository.save(any(MessageEntity.class))).thenReturn(messageToSave);

        FullMessageDto result = messageJpaRepositoryAdapter.save(messageToSaveDto);
        assertEquals(messageToSave.getId(), result.getId());
        assertEquals(messageToSave.getMessageBody(), result.getMessageBody());
        assertEquals(messageToSave.getExecutionTime(), result.getExecutionTime());
    }
}
