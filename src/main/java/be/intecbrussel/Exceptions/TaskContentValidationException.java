package be.intecbrussel.exceptions;

public class TaskContentValidationException extends RuntimeException {
    public TaskContentValidationException(String message) {
        super(message);
    }
}
