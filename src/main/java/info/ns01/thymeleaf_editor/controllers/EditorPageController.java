package info.ns01.thymeleaf_editor.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import info.ns01.thymeleaf_editor.exceptions.InvalidModelException;
import info.ns01.thymeleaf_editor.models.FlashMessage;
import info.ns01.thymeleaf_editor.models.TemplateForm;
import info.ns01.thymeleaf_editor.models.enums.FlashMessageType;
import info.ns01.thymeleaf_editor.services.ModelService;
import info.ns01.thymeleaf_editor.services.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.exceptions.TemplateInputException;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static info.ns01.thymeleaf_editor.utils.Constants.STANDARD_FLASH_ATTRIBUTE_NAME;

@Controller
@RequestMapping("/editor")
public class EditorPageController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private TemplateService templateService;
    
    @Autowired
    private ModelService modelService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String editorForm(TemplateForm templateForm, Model model) {
        return "editor/form";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String handleForm(@Valid TemplateForm templateForm, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        List<FlashMessage> messages = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                messages.add(new FlashMessage(FlashMessageType.WARNING, error.getDefaultMessage()));
            }
            redirectAttributes.addFlashAttribute(STANDARD_FLASH_ATTRIBUTE_NAME, messages);
            return "redirect:/editor";
        }
    
        try {
            Map<String, JsonNode> variables = modelService.extractVariables(templateForm.getModel());
        } catch (InvalidModelException err) {
            logger.info("Invalid model: {}, error: {}", err.getModel(), err.getError(), err);
            messages.add(new FlashMessage(FlashMessageType.DANGER, "Invalid model. See usage below"));
            redirectAttributes.addFlashAttribute(STANDARD_FLASH_ATTRIBUTE_NAME, messages);
            return "redirect:/editor";
        }

        String result;
        try {
            result = templateService.processTemplate(templateForm.getTemplate(), "HTML5");
        } catch (TemplateInputException err) {
            messages.add(new FlashMessage(FlashMessageType.DANGER, err.getMessage()
                    + "; "
                    + err.getCause().getMessage()));
            redirectAttributes.addFlashAttribute(STANDARD_FLASH_ATTRIBUTE_NAME, messages);
            return "redirect:/editor";
        }

        redirectAttributes.addFlashAttribute(templateForm);
        redirectAttributes.addFlashAttribute("result", result);
        return "redirect:/editor";
    }
    
}
