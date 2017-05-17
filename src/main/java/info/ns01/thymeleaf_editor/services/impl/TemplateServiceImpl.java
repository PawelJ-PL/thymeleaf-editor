package info.ns01.thymeleaf_editor.services.impl;

import info.ns01.thymeleaf_editor.services.TemplateEnginesRegistry;
import info.ns01.thymeleaf_editor.services.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Service
public class TemplateServiceImpl implements TemplateService {
    
    @Autowired
    private TemplateEnginesRegistry templateEnginesRegistry;
    
    @Override
    public String processTemplate(String template, String mode) {
        
        TemplateEngine engine = templateEnginesRegistry.getTemplateEngineForMode(mode);
    
        Context context = new Context(Locale.getDefault());
        
        return engine.process(template, context);
        
    }
}
