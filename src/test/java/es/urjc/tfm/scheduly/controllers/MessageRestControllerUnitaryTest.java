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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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
        ZonedDateTime executionTime =ZonedDateTime.of(2023, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid"));
        LocalDateTime serverExecutionTime = executionTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        MessageResponseDto messageResponseDto = new MessageResponseDto(messageId, "Random message body", executionTime, serverExecutionTime, true);

        when(messageService.getMessage(messageId)).thenReturn(messageResponseDto);

        mockMvc.perform(get("/api/message/" + messageId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.messageBody").value(messageResponseDto.getMessageBody()));
    }

    @Test
    @DisplayName("return all messages if exists")
    public void getAllMessageTest() throws Exception {
        
        List<MessageResponseDto> MessageResponseDtoList = new ArrayList<>();
        Long messageId1 = 1L;
        ZonedDateTime executionTime1 =ZonedDateTime.of(2023, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid"));
        LocalDateTime serverExecutionTime1 = executionTime1.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        MessageResponseDto messageResponseDto1 = new MessageResponseDto(messageId1,"Random message body number 1", executionTime1, serverExecutionTime1, true);
        Long messageId2 = 2L;
        MessageResponseDto messageResponseDto2 = new MessageResponseDto(messageId2,"Random message body number 2", null, null, false);
        MessageResponseDtoList.add(messageResponseDto1);
        MessageResponseDtoList.add(messageResponseDto2);
        
        when(messageService.getAllMessages()).thenReturn(MessageResponseDtoList);

        mockMvc.perform(get("/api/message/"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(messageResponseDto1.getId()))
               .andExpect(jsonPath("$[0].messageBody").value(messageResponseDto1.getMessageBody()))
               .andExpect(jsonPath("$[1].id").value(messageResponseDto2.getId()))
               .andExpect(jsonPath("$[1].messageBody").value(messageResponseDto2.getMessageBody()));
    }
    
    @Test
    @DisplayName("create a message and verify its existence")
    public void createMessageTest() throws Exception {
        String messageRequestDtoContent = "{\"messageBody\": \"Random message body\"}";
        ZonedDateTime executionTime =ZonedDateTime.of(2023, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid"));
        LocalDateTime serverExecutionTime = executionTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        MessageResponseDto messageResponseDto = new MessageResponseDto(1L, "Random message body", executionTime, serverExecutionTime, true);

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
    
    @Test
    @DisplayName("schedule a message")
    public void scheduleMessageTest() throws Exception {
        String messageRequestDtoContent = "{\"messageBody\":\"Random message body\","
        		+ "\"year\": 2099,"
        		+ "\"month\": 9,"
        		+ "\"day\": 24,"
        		+ "\"hour\": 17,"
        		+ "\"minute\": 46}";
        ZonedDateTime executionTime =ZonedDateTime.of(2099, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid"));
        LocalDateTime serverExecutionTime = executionTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
		
        MessageResponseDto messageResponseDto = new MessageResponseDto(1L, "Random message body", executionTime, serverExecutionTime, false);
        
        when(messageService.scheduleMessage(any(MessageRequestDto.class))).thenReturn(messageResponseDto);

        mockMvc.perform(post("/api/message/schedule")
               .contentType(MediaType.APPLICATION_JSON)
               .content(messageRequestDtoContent))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.messageBody").value(messageResponseDto.getMessageBody()));
    }
    
    @Test
    @DisplayName("scheduled a message")
    public void scheduledMessageTest() throws Exception {
        Long messageId = 1L;
        ZonedDateTime executionTime =ZonedDateTime.of(2023, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid"));
        LocalDateTime serverExecutionTime = executionTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        MessageResponseDto messageResponseDto = new MessageResponseDto(messageId, "Random message body", executionTime, serverExecutionTime, true);

        when(messageService.getMessage(messageId)).thenReturn(messageResponseDto);

        mockMvc.perform(get("/api/message/" + messageId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.messageBody").value(messageResponseDto.getMessageBody()))
               .andExpect(jsonPath("$.messageDispatched").value(messageResponseDto.isMessageDispatched()));
    }
}