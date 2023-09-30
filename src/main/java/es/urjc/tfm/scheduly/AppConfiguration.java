package es.urjc.tfm.scheduly;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.urjc.tfm.scheduly.domain.ports.MessageRepository;
import es.urjc.tfm.scheduly.domain.ports.MessageUseCase;
import es.urjc.tfm.scheduly.domain.usecases.MessageUseCaseImpl;
import es.urjc.tfm.scheduly.infrastructure.MessageJpaRepository;
import es.urjc.tfm.scheduly.infrastructure.adapters.MessageJpaRepositoryAdapter;

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
	
}
