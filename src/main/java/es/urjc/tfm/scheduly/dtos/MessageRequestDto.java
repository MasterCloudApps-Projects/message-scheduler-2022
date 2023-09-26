package es.urjc.tfm.scheduly.dtos;

public class MessageRequestDto {

	private String messageBody;
	
	public MessageRequestDto() {}
	public MessageRequestDto(String messageBody) {
		super();
		this.messageBody = messageBody;
	}

	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	
	
}