package es.urjc.tfm.scheduly.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.tfm.scheduly.dtos.MessageRequestDto;
import es.urjc.tfm.scheduly.dtos.MessageResponseDto;
import es.urjc.tfm.scheduly.exceptions.OutOfDateException;
import es.urjc.tfm.scheduly.exceptions.WrongParamsException;
import es.urjc.tfm.scheduly.services.MessageService;

@Controller
@RequestMapping("/")
public class MessageWebController {
	
	@Autowired
	private MessageService messageService;
	
	public MessageWebController(MessageService messageService) {
		this.messageService = messageService;
	}

	@GetMapping("/")
	public String showMessages(Model model) {
		model.addAttribute("messages", messageService.getAllMessages());
		return "index";
	}
	
	@GetMapping("message/{id}")
	public String showMessage(Model model, @PathVariable Long id) {
		MessageResponseDto messageResponseDto = this.messageService.getMessage(id);
		model.addAttribute("message", messageResponseDto);
		return "show_message";
	}

	@PostMapping("/message/schedule")
	public String scheduleMessage(Model model, @RequestParam String messageBody,
			@RequestParam int year, @RequestParam int month, @RequestParam int day,
			@RequestParam int hour, @RequestParam int minute){
		try {
			this.messageService.scheduleMessage(new MessageRequestDto(messageBody,year,month,day,hour,minute));
		} catch (Exception e) {
			return "error";
		}
		return "scheduled_message";
	}
	
	@GetMapping("message/delete/{id}")
	public String deleteMessage(Model model, @PathVariable Long id) {
		return "delete_message";
	}

	@PostMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
    	this.messageService.deleteMessage(id);
    	return "deleted_message";
    }
}