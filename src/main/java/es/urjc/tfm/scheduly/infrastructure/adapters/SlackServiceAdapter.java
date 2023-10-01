package es.urjc.tfm.scheduly.infrastructure.adapters;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.slack.api.methods.SlackApiException;

import es.urjc.tfm.scheduly.domain.ports.ComunicationService;
import es.urjc.tfm.scheduly.services.SlackService;

@Component
public class SlackServiceAdapter implements ComunicationService{

	@Autowired
	private SlackService slackService;
	
	public SlackServiceAdapter(SlackService slackService) {
		this.slackService = slackService;
	}

	@Override
	public void sendMessage(String message) throws IOException, SlackApiException {
		this.slackService.sendMessage(message);
	}

	
	
}