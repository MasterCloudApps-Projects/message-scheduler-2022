package es.urjc.tfm.scheduly.services;

import es.urjc.tfm.scheduly.domain.Message;

public interface MessageService {

	public Message createMessage(Message message);

	public Message findById(Long id);
}
