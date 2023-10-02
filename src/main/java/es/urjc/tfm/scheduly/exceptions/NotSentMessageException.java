package es.urjc.tfm.scheduly.exceptions;

public class NotSentMessageException extends Exception {
    public NotSentMessageException(String error) {
        super("Message could not be sent due:" + error);
    }
}
