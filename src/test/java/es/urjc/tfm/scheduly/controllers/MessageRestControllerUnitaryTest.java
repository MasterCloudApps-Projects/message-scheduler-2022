package es.urjc.tfm.scheduly.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import es.urjc.tfm.scheduly.domain.Message;
import es.urjc.tfm.scheduly.services.MessageService;
import es.urjc.tfm.scheduly.services.impl.MessageServiceImpl;

public class MessageRestControllerUnitaryTest {

    private MessageRestController messageRestController;
    private MessageService messageService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        messageService = mock(MessageServiceImpl.class);
        messageRestController = new MessageRestController(messageService);
        mockMvc = MockMvcBuilders.standaloneSetup(messageRestController).build();
    }

    @Test
    @DisplayName("return a message if it exists")
    public void getMessageTest() throws Exception {
        Long messageId = 1L;
        Message expectedMessage = new Message(messageId, "Random message body");

        when(messageService.getMessage(messageId)).thenReturn(expectedMessage);

        mockMvc.perform(get("/api/message/" + messageId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.messageBody").value(expectedMessage.getMessageBody()));
    }

    @Test
    @DisplayName("create a message and verify its existence")
    public void createMessageTest() throws Exception {
        String requestMessage = "{\"messageBody\": \"Random message body\"}";
        Message responseMessage = new Message(1L, "Random message body");

        when(messageService.createMessage(any(Message.class))).thenReturn(responseMessage);

        mockMvc.perform(post("/api/message/")
               .contentType(MediaType.APPLICATION_JSON)
               .content(requestMessage))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.messageBody").value(responseMessage.getMessageBody()));
    }
    
}