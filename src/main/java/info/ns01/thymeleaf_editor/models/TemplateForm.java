package info.ns01.thymeleaf_editor.models;

import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class TemplateForm {

    @NotNull
    @NotEmpty(message = "Template may not be empty")
    private String template;
    
    private String model;

    @NotNull(message = "Mode may not be null")
    @NotEmpty(message = "Mode may not be empty")
    private String mode;

    public TemplateForm(String template, String model, String mode) {
        this.template = template;
        this.model = model;
        this.mode = mode;
    }

    public TemplateForm() {
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TemplateForm that = (TemplateForm) o;
        return Objects.equals(template, that.template)
                && Objects.equals(mode, that.mode)
                && Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(template, mode, model);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("template", template)
                .add("model", model)
                .add("mode", mode)
                .toString();
    }
}
