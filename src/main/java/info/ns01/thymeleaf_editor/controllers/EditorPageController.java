package info.ns01.thymeleaf_editor.controllers;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import info.ns01.thymeleaf_editor.exceptions.InvalidModelException;
import info.ns01.thymeleaf_editor.exceptions.InvalidUserInputException;
import info.ns01.thymeleaf_editor.models.FlashMessage;
import info.ns01.thymeleaf_editor.models.TemplateForm;
import info.ns01.thymeleaf_editor.models.enums.FlashMessageType;
import info.ns01.thymeleaf_editor.services.ModelService;
import info.ns01.thymeleaf_editor.services.TemplateService;
import info.ns01.thymeleaf_editor.utils.Utils;
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
import java.util.Collections;
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
        model.addAttribute("extraScripts", ImmutableList.of("/static/js/render.js"));
        return "editor/form";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String handleForm(@Valid TemplateForm templateForm, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        String result;
        try {
            validateForm(bindingResult, templateForm);
            Map<String, Object> variables = getModelVariables(templateForm.getModel());
            result = getResult(templateForm, variables);
        } catch (InvalidUserInputException err) {
            redirectAttributes.addFlashAttribute(STANDARD_FLASH_ATTRIBUTE_NAME, err.getMessages());
            redirectAttributes.addFlashAttribute(templateForm);
            return "redirect:/editor";
        }

        redirectAttributes.addFlashAttribute(templateForm);
        redirectAttributes.addFlashAttribute("result", result);
        return "redirect:/editor";
    }
    
    private Map<String, Object> getModelVariables(String model) {
        if (Strings.isNullOrEmpty(model)) {
            return Collections.emptyMap();
        }
        try {
            return modelService.extractVariables(model);
        } catch (InvalidModelException err) {
            logger.info("Invalid model: {}, error: {}", err.getModel(), err.getError(), err);
            throw new InvalidUserInputException("Invalid model",
                    ImmutableList.of(new FlashMessage(FlashMessageType.DANGER,
                            "Invalid model. See usage below")));
        }
    }

    private void validateForm(BindingResult bindingResult, TemplateForm templateForm) {
        validateBindingResult(bindingResult);
        validateSupportedTemplateMode(templateForm);
    }

    private void validateBindingResult(BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return;
        }
        List<FlashMessage> messages = new ArrayList<>();
        for (ObjectError error : bindingResult.getAllErrors()) {
            messages.add(new FlashMessage(FlashMessageType.WARNING, error.getDefaultMessage()));
        }
        throw new InvalidUserInputException("Error during processing form", messages);
    }

    private void validateSupportedTemplateMode(TemplateForm templateForm) {
        if (!Utils.getStandardTemplateModesNames().contains(templateForm.getMode())) {
            throw new InvalidUserInputException("Invalid template mode",
                    ImmutableList.of(new FlashMessage(FlashMessageType.WARNING, "Invalid template mode")));
        }
    }

    private String getResult(TemplateForm templateForm, Map<String, Object> variables) {
        try {
            return templateService.processTemplate(templateForm.getTemplate(), variables, templateForm.getMode());
        } catch (TemplateInputException err) {
            String flashMessage = err.getMessage() + "; " + err.getCause().getMessage();
            throw new InvalidUserInputException("Unable to process template",
                    ImmutableList.of(new FlashMessage(FlashMessageType.DANGER, flashMessage)));
        }
    }
    
}
