package info.ns01.thymeleaf_editor.services;

import java.util.Map;

public interface ModelService {
    
    Map<String, Object> extractVariables(String model);
    
}
