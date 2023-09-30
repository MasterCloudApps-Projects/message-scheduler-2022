package es.urjc.tfm.scheduly.infrastructure.models;

import java.time.ZonedDateTime;

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

	private ZonedDateTime executionTime;

	public MessageEntity() {
	}
	public MessageEntity(Long id, String messageBody, ZonedDateTime executionTime) {
		this.id = id;
		this.messageBody = messageBody;
		this.executionTime = executionTime;
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
	public String getMessageBody() {
		return messageBody;
	}
	
	public ZonedDateTime getExecutionTime() {
        return executionTime;
    }
    public void setExecutionTime(ZonedDateTime executionTime) {
        this.executionTime = executionTime;
    }
}
