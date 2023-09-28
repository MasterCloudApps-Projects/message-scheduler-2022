package es.urjc.tfm.scheduly.domain.ports;

import java.util.Optional;

public interface MessageRepository {
	
	public Optional<FullMessageDto> findById(Long id);

	public FullMessageDto save(FullMessageDto fullMessageDto);

	public void deleteById(Long id);
	
}
