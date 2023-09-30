package es.urjc.tfm.scheduly.domain.ports;

import java.util.List;
import java.util.Optional;

public interface MessageRepository {
	
	public Optional<FullMessageDto> findById(Long id);

	public List<FullMessageDto> findAll();

	public FullMessageDto save(FullMessageDto fullMessageDto);

	public void deleteById(Long id);
	
}
