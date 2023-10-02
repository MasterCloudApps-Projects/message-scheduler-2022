package es.urjc.tfm.scheduly.infrastructure;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.urjc.tfm.scheduly.infrastructure.models.MessageEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@SpringJUnitConfig
@Testcontainers
public class MessageRepositoryIntegrationTest { 

    @Autowired
    private MessageJpaRepository messageRepository;

    @LocalServerPort
    private int port;
    
    @Container
    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.22")
            .withDatabaseName("mysql")
            .withUsername("root")
            .withPassword("password");

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl); 
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }
    
    @BeforeAll
    public static void setUp() {
        mysqlContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        mysqlContainer.stop();
    }

    @Test
    public void saveAndDeleteMessageTest() throws JsonMappingException, JsonProcessingException {
        
		String responseJson = "{\"messageBody\": \"some random text\",\"executionTime\": \"9999-09-09T09:09:00+02:00\",\"serverExecutionTime\": \"2023-09-21T19:21:00\",\"messageDispatched\": false}";
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        MessageEntity message = objectMapper.readValue(responseJson,  MessageEntity.class);
        MessageEntity retrievedMessage = messageRepository.save(message);

        assertEquals(message.getMessageBody(), retrievedMessage.getMessageBody());
        assertNotNull(retrievedMessage.getId());
        messageRepository.deleteById(retrievedMessage.getId());
        
    }
    
    @Test
    public void saveFindByIdAndDeleteMessageTest() throws JsonMappingException, JsonProcessingException {
        
		String responseJson = "{\"messageBody\": \"some random text\",\"executionTime\": \"9999-09-09T09:09:00+02:00\",\"serverExecutionTime\": \"2023-09-21T19:21:00\",\"messageDispatched\": false}";
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        MessageEntity message = objectMapper.readValue(responseJson,  MessageEntity.class);
        message = messageRepository.save(message);
        
        MessageEntity retrievedMessage = messageRepository.findById(message.getId()).orElse(null);
        assertEquals(message.getMessageBody(), retrievedMessage.getMessageBody());
        
        messageRepository.deleteById(retrievedMessage.getId());
        MessageEntity retrievedMessagePostDelete = messageRepository.findById(message.getId()).orElse(null);
        assertNull(retrievedMessagePostDelete);
    }
    
    @Test
    public void saveFindAllAndDeleteMessageTest() throws JsonMappingException, JsonProcessingException {
        
		String responseJson = "{\"messageBody\": \"some random text\",\"executionTime\": \"9999-09-09T09:09:00+02:00\",\"serverExecutionTime\": \"2023-09-21T19:21:00\",\"messageDispatched\": false}";
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        MessageEntity message1 = objectMapper.readValue(responseJson,  MessageEntity.class);
        message1 = messageRepository.save(message1);
        MessageEntity message2 = objectMapper.readValue(responseJson,  MessageEntity.class);
        message2 = messageRepository.save(message2);
        
        List<MessageEntity> retrievedMessageList = messageRepository.findAll();
        assertEquals(message1.getId(), retrievedMessageList.get(0).getId());
        assertEquals(message2.getId(), retrievedMessageList.get(1).getId());
        
        messageRepository.deleteById(message1.getId());
        messageRepository.deleteById(message2.getId());
        
        MessageEntity retrievedMessagePostDelete1 = messageRepository.findById(message1.getId()).orElse(null);
        assertNull(retrievedMessagePostDelete1);
        MessageEntity retrievedMessagePostDelete2 = messageRepository.findById(message2.getId()).orElse(null);
        assertNull(retrievedMessagePostDelete2);
    }
}