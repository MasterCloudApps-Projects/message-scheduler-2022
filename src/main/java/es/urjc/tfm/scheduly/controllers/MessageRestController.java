package es.urjc.tfm.scheduly.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.urjc.tfm.scheduly.domain.Message;
import es.urjc.tfm.scheduly.services.MessageService;

@RestController
@RequestMapping("/api/message")
public class MessageRestController {

	private MessageService messageService;

	public MessageRestController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable Long id) {
        Message message = messageService.getMessage(id);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);
    }
}