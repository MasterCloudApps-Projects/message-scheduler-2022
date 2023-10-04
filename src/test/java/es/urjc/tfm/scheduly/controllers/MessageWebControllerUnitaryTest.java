package es.urjc.tfm.scheduly.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

public class MessageWebControllerUnitaryTest {

    private MessageWebController messageWebController;
    private MessageService messageService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        messageService = mock(MessageServiceImpl.class);
        messageWebController = new MessageWebController(messageService);
        mockMvc = MockMvcBuilders.standaloneSetup(messageWebController).build();
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

        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(view().name("index"))
               .andExpect(model().attribute("messages",MessageResponseDtoList));
    }
    
    @Test
    @DisplayName("return the message if exists")
    public void getMessageTest() throws Exception {
        
        List<MessageResponseDto> MessageResponseDtoList = new ArrayList<>();
        Long messageId1 = 1L;
        ZonedDateTime executionTime1 =ZonedDateTime.of(2023, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid"));
        LocalDateTime serverExecutionTime1 = executionTime1.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        MessageResponseDto messageResponseDto1 = new MessageResponseDto(messageId1,"Random message body number 1", executionTime1, serverExecutionTime1, true);
        MessageResponseDtoList.add(messageResponseDto1);
        
        when(messageService.getMessage(any(Long.class))).thenReturn(messageResponseDto1);

        mockMvc.perform(get("/message/"+ messageResponseDto1.getId()))
               .andExpect(status().isOk())
               .andExpect(view().name("show_message"))
               .andExpect(model().attribute("message",messageResponseDto1));
    }

    @Test
    @DisplayName("schedule a new message")
    public void schedulesMessageTest() throws Exception {
        
        ZonedDateTime executionTime =ZonedDateTime.of(2099, 9, 24, 17, 46, 0, 0, ZoneId.of("Europe/Madrid"));
        LocalDateTime serverExecutionTime = executionTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
		
        MessageResponseDto messageResponseDto = new MessageResponseDto(1L, "Random message body", executionTime, serverExecutionTime, false);
        
        when(messageService.scheduleMessage(any(MessageRequestDto.class))).thenReturn(messageResponseDto);

        mockMvc.perform(post("/message/schedule")
        .param("messageBody", messageResponseDto.getMessageBody())
        .param("year", String.valueOf(2099))
        .param("month", String.valueOf(9))
        .param("day", String.valueOf(24))
        .param("hour", String.valueOf(17))
        .param("minute", String.valueOf(46)))
        .andExpect(status().isOk())
        .andExpect(view().name("scheduled_message"));
    }
    /*
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
    }*/
}