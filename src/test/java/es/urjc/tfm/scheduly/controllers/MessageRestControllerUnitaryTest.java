package es.urjc.tfm.scheduly.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import es.urjc.tfm.scheduly.dtos.MessageRequestDto;
import es.urjc.tfm.scheduly.dtos.MessageResponseDto;
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
        MessageResponseDto messageResponseDto = new MessageResponseDto(messageId, "Random message body");

        when(messageService.getMessage(messageId)).thenReturn(messageResponseDto);

        mockMvc.perform(get("/api/message/" + messageId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.messageBody").value(messageResponseDto.getMessageBody()));
    }

    @Test
    @DisplayName("create a message and verify its existence")
    public void createMessageTest() throws Exception {
        String messageRequestDtoContent = "{\"messageBody\": \"Random message body\"}";
        MessageResponseDto messageResponseDto = new MessageResponseDto(1L, "Random message body");

        when(messageService.createMessage(any(MessageRequestDto.class))).thenReturn(messageResponseDto);

        mockMvc.perform(post("/api/message/")
               .contentType(MediaType.APPLICATION_JSON)
               .content(messageRequestDtoContent))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.messageBody").value(messageResponseDto.getMessageBody()));
    }
    
    @Test
    @DisplayName("deletes a message")
    public void deleteMessageTest() throws Exception {
        Long messageId = 1L;
        doNothing().when(messageService).deleteMessage(messageId);

        mockMvc.perform(delete("/api/message/" + messageId))
               .andExpect(status().isNoContent());
    }
    
}