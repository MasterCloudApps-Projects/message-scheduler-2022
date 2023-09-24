package es.urjc.tfm.scheduly.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.slack.api.methods.SlackApiException;

import es.urjc.tfm.scheduly.services.impl.SlackServiceImpl;

public class SlackServiceUnitaryTest {

    private SlackService slackService;
    private String channel = "C05TRPS0V28";
    
    @Test
    public void testSendMessage_Success() throws IOException, SlackApiException {
       
            slackService = new SlackServiceImpl(System.getenv("SLACK_TOKEN_TEST"), channel);
        
            String result = slackService.sendMessage("Test message");

            assertEquals("Message sent successfully: Test message", result);
       
    	
    }
 
    @Test
    public void testSendMessage_Failure() throws IOException, SlackApiException {
    	slackService = new SlackServiceImpl("wrong_token", channel);

        String result = slackService.sendMessage("Test message");

        assertEquals("Message sending failed: invalid_auth", result);
    }
}