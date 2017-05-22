package info.ns01.thymeleaf_editor.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.ns01.thymeleaf_editor.exceptions.ConversionError;

import java.io.IOException;

public class JsonUtils {
    
    public static JsonNode convertStringToJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(json);
        } catch (IOException err) {
            throw new ConversionError(String.format("Unable to covert string into Json: %s", err.getMessage()), err);
        }
    }
    
    public static Object convertNodeToOrdinaryObject(JsonNode node) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.treeToValue(node, Object.class);
        } catch (JsonProcessingException err) {
            throw new ConversionError(String.format("Unable to convert JsonNode to Object: %s", err.getMessage()), err);
        }
    }
    
}
