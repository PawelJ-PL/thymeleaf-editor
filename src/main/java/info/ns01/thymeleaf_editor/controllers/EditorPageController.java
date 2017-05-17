package info.ns01.thymeleaf_editor.controllers;

import info.ns01.thymeleaf_editor.models.TemplateForm;
import info.ns01.thymeleaf_editor.services.TemplateService;
import info.ns01.thymeleaf_editor.services.impl.InMemoryTemplateResolver;
import info.ns01.thymeleaf_editor.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired
    private TemplateService templateService;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String editorForm(TemplateForm templateForm) {
        return "editor/form";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String handleForm(@Valid TemplateForm templateForm, BindingResult bindingResult) {
        return "redirect:/editor";
    }
    
}
