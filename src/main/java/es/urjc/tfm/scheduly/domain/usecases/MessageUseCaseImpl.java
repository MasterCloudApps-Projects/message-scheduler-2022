package es.urjc.tfm.scheduly.domain.usecases;

import java.util.List;
import java.util.Optional;

import es.urjc.tfm.scheduly.domain.ports.FullMessageDto;
import es.urjc.tfm.scheduly.domain.ports.MessageRepository;
import es.urjc.tfm.scheduly.domain.ports.MessageUseCase;

public class MessageUseCaseImpl implements MessageUseCase{

	private MessageRepository messageRepository;

	public MessageUseCaseImpl(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	public Optional<FullMessageDto> findById(Long id){
		return messageRepository.findById(id);
	}

	@Override
	public List<FullMessageDto> findAll() {
		return this.messageRepository.findAll();
	}

	@Override
	public FullMessageDto createMessage(FullMessageDto fullMessageDto) {
		return messageRepository.save(fullMessageDto);
	}

	@Override
	public void deleteMessage(Long id ) {
		this.messageRepository.deleteById(id);
	}

	@Override
	public FullMessageDto updateMessageDispatched(Long id) {
		FullMessageDto fullMessageDto = this.messageRepository.findById(id).orElseThrow();
        if (!fullMessageDto.isMessageDispatched()) {
        	fullMessageDto.setMessageDispatched(true);
        	fullMessageDto = this.messageRepository.save(fullMessageDto);
        }
        return fullMessageDto;
	}
}