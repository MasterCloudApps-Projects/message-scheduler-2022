package es.urjc.tfm.scheduly.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.urjc.tfm.scheduly.domain.ports.FullMessageDto;
import es.urjc.tfm.scheduly.infrastructure.adapters.MessageMongoRepositoryAdapter;
import es.urjc.tfm.scheduly.infrastructure.models.MessageEntityMongo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MessageMongoRepositoryUnitaryTest {

    @Mock
    private MessageMongoRepository messageMongoRepository;

    private MessageMongoRepositoryAdapter messageMongoRepositoryAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); 
        messageMongoRepositoryAdapter = new MessageMongoRepositoryAdapter(messageMongoRepository);
    }

    @Test
    public void findByIdTest() {
        Long messageId = 1L;
        ZonedDateTime executionTime = ZonedDateTime.of(2025, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid"));
    	LocalDateTime serverExecutionTime = executionTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    	
        MessageEntityMongo expectedMessage = new MessageEntityMongo(messageId,"Random message body", executionTime, serverExecutionTime, false);
        when(messageMongoRepository.findById(messageId)).thenReturn(Optional.of(expectedMessage));

        Optional<FullMessageDto> result = messageMongoRepositoryAdapter.findById(messageId);

        assertEquals(expectedMessage.getMessageBody(), result.get().getMessageBody());
        assertEquals(expectedMessage.getExecutionTime(), result.get().getExecutionTime());
    }

    @Test
    public void findAllTest() {
        Long messageId = 1L;
        Long messageId2 = 2L;
        List<MessageEntityMongo> entityList = new ArrayList<>();
        
        ZonedDateTime executionTime1 = ZonedDateTime.of(2025, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid"));
    	LocalDateTime serverExecutionTime1 = executionTime1.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    	ZonedDateTime executionTime2 = ZonedDateTime.of(2024, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid"));
    	LocalDateTime serverExecutionTime2 = executionTime2.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
		
        MessageEntityMongo expectedMessage1 = new MessageEntityMongo(messageId,"Random message body number 1", executionTime1, serverExecutionTime1, false);
        MessageEntityMongo expectedMessage2 = new MessageEntityMongo(messageId2,"Random message body number 2", executionTime2, serverExecutionTime2, false);
        entityList.add(expectedMessage1);
        entityList.add(expectedMessage2);

        when(messageMongoRepository.findAll()).thenReturn(entityList);

        List<FullMessageDto> result = messageMongoRepositoryAdapter.findAll();

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
    	ZonedDateTime executionTime = ZonedDateTime.of(2023, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid"));
    	LocalDateTime serverExecutionTime = executionTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
		
    	MessageEntityMongo messageToSave = new MessageEntityMongo(1L,"Random message body",executionTime,serverExecutionTime, false);
        when(messageMongoRepository.save(any(MessageEntityMongo.class))).thenReturn(messageToSave);

        FullMessageDto result = messageMongoRepositoryAdapter.save(messageToSaveDto);
        assertEquals(messageToSave.getId(), result.getId());
        assertEquals(messageToSave.getMessageBody(), result.getMessageBody());
        assertEquals(messageToSave.getExecutionTime(), result.getExecutionTime());
    }

}
