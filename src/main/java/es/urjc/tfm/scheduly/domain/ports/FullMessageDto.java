package es.urjc.tfm.scheduly.domain.ports;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class FullMessageDto {

	private Long id;
	private String messageBody;
	private ZonedDateTime executionTime;
	private LocalDateTime serverExecutionTime;
	private boolean messageDispatched;
	
	public FullMessageDto() {
	}
	
	public FullMessageDto(Long id, String messageBody, ZonedDateTime executionTimeDate, LocalDateTime serverExecutionTime, boolean messageDispatched) {
		super();
        this.id = id;
		this.messageBody = messageBody;
		this.executionTime = executionTimeDate;
		this.serverExecutionTime = serverExecutionTime;
		this.messageDispatched = messageDispatched;
	}
	
	public FullMessageDto(String messageBody, ZonedDateTime executionTimeDate, LocalDateTime serverExecutionTime) {
		this.messageBody = messageBody;
		this.executionTime = executionTimeDate;
		this.serverExecutionTime = serverExecutionTime;
		this.messageDispatched = false;
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
