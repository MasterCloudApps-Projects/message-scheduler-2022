package es.urjc.tfm.scheduly.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.urjc.tfm.scheduly.domain.Message;
import es.urjc.tfm.scheduly.services.impl.MessageServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

public class MessageServiceTest {

    private MessageService messageService;

    @BeforeEach
    public void setUp() {
        messageService = new MessageServiceImpl();
    }

    @Test
    public void testCreateMessage() {
        Message inputMessage = new Message(null, "some random message");

        Message result = messageService.createMessage(inputMessage);

        assertNotNull(result.getId());

    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Message result = messageService.findById(id);

        assertEquals(id, result.getId());

    }
}
