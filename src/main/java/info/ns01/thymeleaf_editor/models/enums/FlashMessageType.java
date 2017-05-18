package info.ns01.thymeleaf_editor.models.enums;

public enum FlashMessageType {
    
    SUCCESS("success"),
    INFO("info"),
    WARNING("warning"),
    DANGER("danger");
    
    private String type;
    
    FlashMessageType(String messageType) {
        type = messageType;
    }
    
    @Override
    public String toString() {
        return type;
    }
}
