package info.ns01.thymeleaf_editor.controllers;

import info.ns01.thymeleaf_editor.models.TemplateForm;
import info.ns01.thymeleaf_editor.services.impl.InMemoryTemplateResolver;
import info.ns01.thymeleaf_editor.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/editor")
public class EditorPageController {
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String editorForm(TemplateForm templateForm) {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(new InMemoryTemplateResolver(StandardTemplateModeHandlers.HTML5.getTemplateModeName()));
        Context context = new Context(Locale.getDefault());
        String template = "<div th:text=\"XDXD\">UUU</div>";
        String result = templateEngine.process(template, context);
        System.out.println("XXXXXXXXXXXXXXXXX " + result);
        return "editor/form";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String handleForm(@Valid TemplateForm templateForm, BindingResult bindingResult) {
        return "redirect:/editor";
    }
    
}
