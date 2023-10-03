package es.urjc.tfm.scheduly.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
}