package info.ns01.thymeleaf_editor.exceptions;

public class InvalidModelException extends RuntimeException {
    
    private String model;
    
    private String error;
    
    private static final String MESSAGE_TEMPLATE = "Unable to parse model %s";
    
    public InvalidModelException(String model, String error) {
        super(String.format(MESSAGE_TEMPLATE, error));
        this.model = model;
        this.error = error;
    }
    
    public InvalidModelException(String model, Throwable cause) {
        super(String.format(MESSAGE_TEMPLATE, cause.getMessage()), cause);
        this.model = model;
        this.error = cause.getMessage();
    }
    
    public InvalidModelException(String model, String error, Throwable cause) {
        super(String.format(MESSAGE_TEMPLATE, error), cause);
        this.model = model;
        this.error = error;
    }
    
    public String getModel() {
        return model;
    }
    
    public String getError() {
        return error;
    }
}
