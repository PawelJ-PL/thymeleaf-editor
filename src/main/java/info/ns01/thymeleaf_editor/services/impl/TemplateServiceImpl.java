package info.ns01.thymeleaf_editor.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import info.ns01.thymeleaf_editor.services.TemplateEnginesRegistry;
import info.ns01.thymeleaf_editor.services.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

@Service
public class TemplateServiceImpl implements TemplateService {
    
    @Autowired
    private TemplateEnginesRegistry templateEnginesRegistry;
    
    @Override
    public String processTemplate(String template, Map<String, Object> variables, String mode) {
        
        TemplateEngine engine = templateEnginesRegistry.getTemplateEngineForMode(mode);
    
        Context context = new Context(Locale.getDefault());
        
        context.setVariables(variables);
        
        return engine.process(template, context);
        
    }
    
    @Override
    public String processTemplate(String template, String mode) {
        return processTemplate(template, Collections.emptyMap(), mode);
    }
}
