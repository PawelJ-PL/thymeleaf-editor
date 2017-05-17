package info.ns01.thymeleaf_editor.services;

import org.thymeleaf.TemplateEngine;

public interface TemplateEnginesRegistry {
    
    TemplateEngine getTemplateEngineForMode(String mode);
    
    boolean registerTemplateEngineFor(String mode);
    
}
