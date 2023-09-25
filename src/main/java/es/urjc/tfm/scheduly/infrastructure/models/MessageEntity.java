package es.urjc.tfm.scheduly.infrastructure.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MessageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private String messageBody;

	public MessageEntity() {
	}

	public MessageEntity(String messageBody) {
		this.messageBody = messageBody;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public Object getMessageBody() {
		return messageBody;
	}
	
	
}
