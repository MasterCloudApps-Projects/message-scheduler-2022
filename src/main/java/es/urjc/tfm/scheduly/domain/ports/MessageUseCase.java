package es.urjc.tfm.scheduly.domain.ports;

import java.util.Optional;

public interface MessageUseCase {

	public Optional<FullMessageDto> findById(Long id);

	public FullMessageDto createMessage(FullMessageDto fullMessageDto);

	public void deleteMessage(Long id);
	
}
