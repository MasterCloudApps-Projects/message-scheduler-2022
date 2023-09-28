package es.urjc.tfm.scheduly.domain.ports;

public class FullMessageDto {

	private Long id;
	private String messageBody;
	public FullMessageDto() {
	}
	
	public FullMessageDto(Long id, String messageBody) {
		super();
        this.id = id;
		this.messageBody = messageBody;
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

}
