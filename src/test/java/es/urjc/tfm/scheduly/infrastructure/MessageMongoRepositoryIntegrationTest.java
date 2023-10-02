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
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import es.urjc.tfm.scheduly.infrastructure.models.MessageEntityMongo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@SpringJUnitConfig
@Testcontainers
public class MessageMongoRepositoryIntegrationTest { 

    @Autowired
    private MessageMongoRepository messageRepository;

    @Container
    private static final MongoDBContainer mongoContainer = new MongoDBContainer("mongo:4.4.6").withExposedPorts(27017);

    private static MongoClient mongoClient;

    
    @LocalServerPort
    private int port;
    
    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoContainer::getReplicaSetUrl);
    }
    
    @BeforeAll
    public static void setUp() {
        mongoContainer.start();
        String connectionString = mongoContainer.getReplicaSetUrl();
        mongoClient = MongoClients.create(connectionString);  
    }

    @AfterAll
    public static void tearDown() {
        mongoClient.close();
        mongoContainer.stop();
    }

    @Test
    public void saveAndDeleteMessageTest() throws JsonMappingException, JsonProcessingException {
        
		String responseJson = "{\"id\": 1,\"messageBody\": \"some random text\",\"executionTime\": \"9999-09-09T09:09:00+02:00\",\"serverExecutionTime\": \"2023-09-21T19:21:00\",\"messageDispatched\": false}";
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        MessageEntityMongo message = objectMapper.readValue(responseJson,  MessageEntityMongo.class);
        MessageEntityMongo retrievedMessage = messageRepository.save(message);

        assertEquals(message.getMessageBody(), retrievedMessage.getMessageBody());
        assertNotNull(retrievedMessage.getId());
        messageRepository.deleteById(retrievedMessage.getId());
        
    }
    
    @Test
    public void saveFindByIdAndDeleteMessageTest() throws JsonMappingException, JsonProcessingException {
        
		String responseJson = "{\"id\": 1, \"messageBody\": \"some random text\",\"executionTime\": \"9999-09-09T09:09:00+02:00\",\"serverExecutionTime\": \"2023-09-21T19:21:00\",\"messageDispatched\": false}";
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        MessageEntityMongo message = objectMapper.readValue(responseJson,  MessageEntityMongo.class);
        message = messageRepository.save(message);
        
        MessageEntityMongo retrievedMessage = messageRepository.findById(message.getId()).orElse(null);
        assertEquals(message.getMessageBody(), retrievedMessage.getMessageBody());
        
        messageRepository.deleteById(retrievedMessage.getId());
        MessageEntityMongo retrievedMessagePostDelete = messageRepository.findById(message.getId()).orElse(null);
        assertNull(retrievedMessagePostDelete);
    }
    
    @Test
    public void saveFindAllAndDeleteMessageTest() throws JsonMappingException, JsonProcessingException {
    	 
		String responseJson1 = "{\"id\": 1, \"messageBody\": \"some random text\",\"executionTime\": \"9999-09-09T09:09:00+02:00\",\"serverExecutionTime\": \"2023-09-21T19:21:00\",\"messageDispatched\": false}";
		String responseJson2 = "{\"id\": 2, \"messageBody\": \"some random text\",\"executionTime\": \"9999-09-09T09:09:00+02:00\",\"serverExecutionTime\": \"2023-09-21T19:21:00\",\"messageDispatched\": false}";
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        MessageEntityMongo message1 = objectMapper.readValue(responseJson1,  MessageEntityMongo.class);
        message1 = messageRepository.save(message1);
        MessageEntityMongo message2 = objectMapper.readValue(responseJson2,  MessageEntityMongo.class);
        message2 = messageRepository.save(message2);
        
        List<MessageEntityMongo> retrievedMessageList = messageRepository.findAll();
        assertEquals(message1.getId(), retrievedMessageList.get(0).getId());
        assertEquals(message2.getId(), retrievedMessageList.get(1).getId());
        
        messageRepository.deleteById(message1.getId());
        messageRepository.deleteById(message2.getId());
        
        MessageEntityMongo retrievedMessagePostDelete1 = messageRepository.findById(message1.getId()).orElse(null);
        assertNull(retrievedMessagePostDelete1);
        MessageEntityMongo retrievedMessagePostDelete2 = messageRepository.findById(message2.getId()).orElse(null);
        assertNull(retrievedMessagePostDelete2);
    }
}