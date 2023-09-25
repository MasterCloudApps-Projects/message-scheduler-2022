package es.urjc.tfm.scheduly.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import es.urjc.tfm.scheduly.infrastructure.models.MessageEntity;

@DataJpaTest
public class MessageRepositoryUnitaryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MessageRepository messageRepository;
    
    @Test
    public void findByIdTest() {
        MessageEntity message = new MessageEntity();
        message.setMessageBody("Hello, World!");

        MessageEntity savedMessage = entityManager.persistAndFlush(message);

        MessageEntity retrievedMessage = messageRepository.findById(savedMessage.getId()).orElse(null);

        assertNotNull(retrievedMessage);
        
        assertEquals("Hello, World!", retrievedMessage.getMessageBody());
    }
    
    @Test
    public void saveTest() {
        MessageEntity message = new MessageEntity();
        message.setMessageBody("Hello, World!");

        MessageEntity savedMessage = messageRepository.save(message);

        MessageEntity retrievedMessage = messageRepository.findById(savedMessage.getId()).orElse(null);

        assertNotNull(retrievedMessage);
        
        assertEquals("Hello, World!", retrievedMessage.getMessageBody());
    }
}
