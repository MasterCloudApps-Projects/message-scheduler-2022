package es.urjc.tfm.scheduly.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.urjc.tfm.scheduly.domain.Message;

@RestController
@RequestMapping("/api/message")
public class MessageRestController {

	public MessageRestController() {
		
	}
	
	@GetMapping("/{id}")
	public Message getMessage(Long id) {
		Message newMessage = new Message(id,"Random message body");
		return newMessage;
	}

	@PostMapping("/")
	public Message createMessage(@RequestBody Message message) {
		Message newMessage = new Message(message.getId(),message.getMessageBody());
		return newMessage;
	}
}
