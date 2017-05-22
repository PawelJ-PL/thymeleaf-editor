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

    public TemplateForm(String template, String model) {
        this.template = template;
        this.model = model;
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
                && Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(template, model);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("template", template)
                .add("model", model)
                .toString();
    }
}
