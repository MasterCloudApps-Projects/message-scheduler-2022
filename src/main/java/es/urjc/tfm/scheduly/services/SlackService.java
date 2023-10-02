package es.urjc.tfm.scheduly.services;

import java.io.IOException;
import com.slack.api.methods.SlackApiException;

import es.urjc.tfm.scheduly.exceptions.NotSentMessageException;


public interface SlackService{

	public String sendMessage(String messageText) throws IOException, SlackApiException, NotSentMessageException;
}

	

