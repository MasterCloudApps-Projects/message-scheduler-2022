package es.urjc.tfm.scheduly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import es.urjc.tfm.scheduly.services.MessageService;

@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	MessageService messageService;
	
    @Value("${listener.enabled}")
    private boolean listenerEnabled;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
    	if(listenerEnabled){
            System.out.println("Scheduling already created messages");
            messageService.reScheduleAtStart();
        }
    }
}
