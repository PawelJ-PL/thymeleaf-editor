package info.ns01.thymeleaf_editor.exceptions;

public class ConversionError extends RuntimeException {
    public ConversionError() {
    }
    
    public ConversionError(String message) {
        super(message);
    }
    
    public ConversionError(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ConversionError(Throwable cause) {
        super(cause);
    }
    
    public ConversionError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
