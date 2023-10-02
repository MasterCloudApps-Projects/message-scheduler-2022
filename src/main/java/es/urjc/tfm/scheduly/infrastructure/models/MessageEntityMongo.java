package es.urjc.tfm.scheduly.infrastructure.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Document(collection = "messages") 
public class MessageEntityMongo {

    @Id 
    private Long id;

    private String messageBody;
    private ZonedDateTime executionTime;
    private LocalDateTime serverExecutionTime;
    private boolean messageDispatched;

    public MessageEntityMongo() {
    }

    public MessageEntityMongo(String messageBody) {
        this.messageBody = messageBody;
    }

    public MessageEntityMongo(String messageBody, ZonedDateTime executionTime, LocalDateTime serverExecutionTime, boolean messageDispatched) {
        this.messageBody = messageBody;
        this.executionTime = executionTime;
        this.serverExecutionTime = serverExecutionTime;
        this.messageDispatched = messageDispatched;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getExecutionTime() {
        return executionTime;
    }
    public void setExecutionTime(ZonedDateTime executionTime) {
        this.executionTime = executionTime;
    }

    public String getMessageBody() {
        return messageBody;
    }
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
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
