package es.urjc.tfm.scheduly.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import es.urjc.tfm.scheduly.domain.Message;
import es.urjc.tfm.scheduly.services.MessageService;

@WebMvcTest(MessageRestController.class)
public class MessageRestControllerUnitaryTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private MessageService messageService;
	
	@Test
	@DisplayName("returns a message if exists")
    public void getMessageTest() throws Exception {
    	Message result = new Message(1L,"Random message body");
		mvc.perform(get("/api/message/"+result.getId())
		  )
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$.messageBody", equalTo(result.getMessageBody())));
    }
	
	@Test
	@DisplayName("creates a message and verify that exists")
    public void createMessageTest() throws Exception {
		Message result = new Message("Random message body");
		
		mvc.perform(post("/api/message/")
				.contentType(MediaType.APPLICATION_JSON)
                .content("{ \"messageBody\": \"Random message body\"}"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.messageBody").value(result.getMessageBody()));	
    }
	
}

