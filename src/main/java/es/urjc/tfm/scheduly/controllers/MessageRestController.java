package es.urjc.tfm.scheduly.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.urjc.tfm.scheduly.dtos.MessageRequestDto;
import es.urjc.tfm.scheduly.dtos.MessageResponseDto;
import es.urjc.tfm.scheduly.services.MessageService;

@RestController
@RequestMapping("/api/message")
public class MessageRestController {

	private MessageService messageService;

	public MessageRestController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<?> getMessage(@PathVariable Long id) {
        MessageResponseDto messageResponseDto;
		try {
			messageResponseDto = this.messageService.getMessage(id);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(messageResponseDto);
    }

	@GetMapping("/")
	public List<MessageResponseDto> getAllMessages() {
		return this.messageService.getAllMessages();
	}
	
    @PostMapping("/")
    public ResponseEntity<MessageResponseDto> createMessage(@RequestBody MessageRequestDto message) {
    	MessageResponseDto createdMessage = messageService.createMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);
    }

	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
    	this.messageService.deleteMessage(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Message deleted properly");
    }
	
	@PostMapping("/schedule")
    public ResponseEntity<MessageResponseDto> scheduleMessage(@RequestBody MessageRequestDto message)throws Exception {
    	MessageResponseDto scheduledMessage = messageService.scheduleMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduledMessage);
    }
}