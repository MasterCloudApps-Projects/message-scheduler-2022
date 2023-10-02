package es.urjc.tfm.scheduly.services.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import es.urjc.tfm.scheduly.exceptions.NotSentMessageException;
import es.urjc.tfm.scheduly.services.SlackService;

@Service
public class SlackServiceImpl implements SlackService {
	@Value("${slack.oauth.token}")
	String token;
	@Value("${slack.channel}")
    String channelId; 

    public SlackServiceImpl() {}

	public SlackServiceImpl(String token, String channel) {
		this.token = token;
		this.channelId = channel;
	}

	public String sendMessage(String messageText) throws IOException, SlackApiException, NotSentMessageException {
		Slack slack = Slack.getInstance();
        MethodsClient methods = slack.methods(token);
	    ChatPostMessageRequest messageRequest = ChatPostMessageRequest.builder()
	            .channel(channelId)
	            .text(messageText)
	            .build();
	
	    ChatPostMessageResponse messageResponse = methods.chatPostMessage(messageRequest);
	    if (messageResponse.isOk()) {
	        return "Message sent successfully: " + messageResponse.getMessage().getText();
	    }  else throw new NotSentMessageException(messageResponse.getError());
	    
    }
}

	

