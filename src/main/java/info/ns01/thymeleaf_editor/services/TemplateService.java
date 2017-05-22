package info.ns01.thymeleaf_editor.services;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public interface TemplateService {
    
    String processTemplate(String template, Map<String, Object> variables, String mode);
    
    String processTemplate(String template, String mode);
    
}
