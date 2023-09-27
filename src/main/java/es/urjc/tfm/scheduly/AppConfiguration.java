package es.urjc.tfm.scheduly;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.urjc.tfm.scheduly.domain.ports.MessageRepository;
import es.urjc.tfm.scheduly.domain.ports.MessageUseCase;
import es.urjc.tfm.scheduly.domain.usecases.MessageUseCaseImpl;

@Configuration
public class AppConfiguration {

	@Bean
    public MessageUseCase messageUseCase(MessageRepository messageRepository) {
    	return new MessageUseCaseImpl(messageRepository);
    }
}
