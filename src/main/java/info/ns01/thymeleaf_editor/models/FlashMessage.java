package info.ns01.thymeleaf_editor.models;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import info.ns01.thymeleaf_editor.models.enums.FlashMessageType;

import java.io.Serializable;

public class FlashMessage implements Serializable {

    private FlashMessageType type;

    private String message;

    public FlashMessage() {
    }

    public FlashMessage(FlashMessageType type, String message) {
        this.type = type;
        this.message = message;
    }

    public FlashMessageType getType() {
        return type;
    }

    public String getTypeAsString() {
        return type.toString();
    }

    public void setType(FlashMessageType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FlashMessage)) {
            return false;
        }
        FlashMessage that = (FlashMessage) o;
        return type == that.type
                && Objects.equal(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, message);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("message", message)
                .toString();
    }
}
