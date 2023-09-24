package es.urjc.tfm.scheduly.services;

import java.io.IOException;
import com.slack.api.methods.SlackApiException;


public interface SlackService{

	public String sendMessage(String messageText) throws IOException, SlackApiException;
}

	

