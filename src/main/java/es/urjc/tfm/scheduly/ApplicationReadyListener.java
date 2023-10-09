package es.urjc.tfm.scheduly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import es.urjc.tfm.scheduly.services.MessageService;

@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	MessageService messageService;
	
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
    	System.out.println("Scheduling already created messages");
        messageService.reScheduleAtStart();
    }
}
