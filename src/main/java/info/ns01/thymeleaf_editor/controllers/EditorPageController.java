package info.ns01.thymeleaf_editor.controllers;

import info.ns01.thymeleaf_editor.models.TemplateForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/editor")
public class EditorPageController {
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String editorForm(TemplateForm templateForm) {
        return "editor/form";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String handleForm(@Valid TemplateForm templateForm, BindingResult bindingResult) {
        return "redirect:/editor";
    }
    
}
