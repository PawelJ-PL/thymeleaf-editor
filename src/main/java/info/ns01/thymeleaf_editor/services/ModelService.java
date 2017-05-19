package info.ns01.thymeleaf_editor.services;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public interface ModelService {
    
    Map<String, JsonNode> extractVariables(String model);
    
}
