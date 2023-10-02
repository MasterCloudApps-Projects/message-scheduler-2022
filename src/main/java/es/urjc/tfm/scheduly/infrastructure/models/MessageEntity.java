package es.urjc.tfm.scheduly.infrastructure.models;

import java.time.LocalDateTime;
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

	private LocalDateTime serverExecutionTime;

	private boolean messageDispatched;
	
	public MessageEntity() {
	}
	public MessageEntity(Long id, String messageBody, ZonedDateTime executionTime, LocalDateTime serverExecutionTime, boolean messageDispatched) {
    	this.id = id;
		this.messageBody = messageBody;
        this.executionTime = executionTime;
        this.serverExecutionTime = serverExecutionTime;
        this.messageDispatched = messageDispatched;
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

	public LocalDateTime getServerExecutionTime() {
        return serverExecutionTime;
    }
    public void setServerExecutionTime(LocalDateTime serverExecutionTime) {
        this.serverExecutionTime = serverExecutionTime;
    }

	public boolean isMessageDispatched() {
		return messageDispatched;
	}
	public void setMessageDispatched(boolean messageDispatched) {
		this.messageDispatched = messageDispatched;
	}
}
