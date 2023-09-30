package es.urjc.tfm.scheduly.dtos;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MessageResponseDto {

	private Long id;
	private String messageBody;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
	private ZonedDateTime executionTime;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime serverExecutionTime;
	
	public MessageResponseDto() {}
	public MessageResponseDto(Long id, String messageBody, ZonedDateTime executionTimeDate, LocalDateTime serverExecutionTime) {
		super();
		this.id = id;
		this.messageBody = messageBody;
		this.executionTime = executionTimeDate;
		this.serverExecutionTime = serverExecutionTime;
	}
	
	public MessageResponseDto(String messageBody) {
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
}