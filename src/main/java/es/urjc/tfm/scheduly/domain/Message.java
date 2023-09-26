package es.urjc.tfm.scheduly.domain;

public class Message {

	private Long id;
	private String messageBody;
	
	public Message() {}
	public Message(Long id, String messageBody) {
		super();
		this.id = id;
		this.messageBody = messageBody;
	}
	public Message(String messageBody) {
		super();
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