package es.urjc.tfm.scheduly.domain.ports;

import java.io.IOException;

import com.slack.api.methods.SlackApiException;

public interface ComunicationService {

	public void sendMessage(String message) throws IOException, SlackApiException;
	
}
