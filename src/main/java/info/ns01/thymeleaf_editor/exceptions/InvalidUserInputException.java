package info.ns01.thymeleaf_editor.exceptions;

import info.ns01.thymeleaf_editor.models.FlashMessage;

import java.util.List;

public class InvalidUserInputException extends RuntimeException {

    private List<FlashMessage> messages;

    public InvalidUserInputException(List<FlashMessage> messages) {
        this.messages = messages;
    }

    public InvalidUserInputException(String message, List<FlashMessage> messages) {
        super(message);
        this.messages = messages;
    }

    public InvalidUserInputException(String message, Throwable cause, List<FlashMessage> messages) {
        super(message, cause);
        this.messages = messages;
    }

    public InvalidUserInputException(Throwable cause, List<FlashMessage> messages) {
        super(cause);
        this.messages = messages;
    }

    public InvalidUserInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<FlashMessage> messages) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.messages = messages;
    }

    public List<FlashMessage> getMessages() {
        return messages;
    }
}
