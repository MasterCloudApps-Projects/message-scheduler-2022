package es.urjc.tfm.scheduly.domain.ports;

import java.time.ZonedDateTime;

public class FullMessageDto {

	private Long id;
	private String messageBody;
	private ZonedDateTime executionTime;
	public FullMessageDto() {
	}
	
	public FullMessageDto(Long id, String messageBody, ZonedDateTime executionTimeDate) {
		super();
        this.id = id;
		this.messageBody = messageBody;
		this.executionTime = executionTimeDate;
	}
	
	public FullMessageDto(String messageBody, ZonedDateTime executionTimeDate) {
		this.messageBody = messageBody;
		this.executionTime = executionTimeDate;
	}

	public FullMessageDto(String messageBody) {
		this.messageBody = messageBody;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public ZonedDateTime getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(ZonedDateTime executionTime) {
		this.executionTime = executionTime;
	}
}
