package es.urjc.tfm.scheduly;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.urjc.tfm.scheduly.domain.ports.ComunicationService;
import es.urjc.tfm.scheduly.domain.ports.ComunicationUseCase;
import es.urjc.tfm.scheduly.domain.ports.MessageRepository;
import es.urjc.tfm.scheduly.domain.ports.MessageUseCase;
import es.urjc.tfm.scheduly.domain.usecases.ComunicationUseCaseImpl;
import es.urjc.tfm.scheduly.domain.usecases.MessageUseCaseImpl;
import es.urjc.tfm.scheduly.infrastructure.MessageJpaRepository;
import es.urjc.tfm.scheduly.infrastructure.adapters.MessageJpaRepositoryAdapter;
import es.urjc.tfm.scheduly.infrastructure.adapters.SlackServiceAdapter;
import es.urjc.tfm.scheduly.services.SlackService;

@Configuration
public class AppConfiguration {

	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	@Bean
    public MessageUseCase messageUseCase(MessageRepository messageRepository) {
    	return new MessageUseCaseImpl(messageRepository);
    }

	@Bean
    public MessageRepository messageRepository(MessageJpaRepository messageJpaRepository) {
    	return new MessageJpaRepositoryAdapter(messageJpaRepository);
    }
	
	@Bean
    public ComunicationUseCase comunicationUseCase(ComunicationService comunicationService) {
    	return new ComunicationUseCaseImpl(comunicationService);
    }

	@Bean
    public ComunicationService comunicationService(SlackService slackService) {
    	return new SlackServiceAdapter(slackService);
    }
}
