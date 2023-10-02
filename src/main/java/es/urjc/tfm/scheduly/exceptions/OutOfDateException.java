package es.urjc.tfm.scheduly.exceptions;

public class OutOfDateException extends Exception {
    public OutOfDateException() {
        super("Message can´t be scheduled because the date has already passed");
    }
}
