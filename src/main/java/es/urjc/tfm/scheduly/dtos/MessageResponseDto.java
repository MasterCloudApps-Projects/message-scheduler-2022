package es.urjc.tfm.scheduly.dtos;

public class MessageResponseDto {

	private Long id;
	private String messageBody;
	
	public MessageResponseDto() {}
		
	public MessageResponseDto(Long id, String messageBody) {
		super();
		this.id = id;
		this.messageBody = messageBody;
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
	
	

}