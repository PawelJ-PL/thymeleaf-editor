package info.ns01.thymeleaf_editor.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import info.ns01.thymeleaf_editor.exceptions.ConversionError;
import info.ns01.thymeleaf_editor.exceptions.InvalidModelException;
import info.ns01.thymeleaf_editor.services.ModelService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static info.ns01.thymeleaf_editor.utils.JsonUtils.convertStringToJson;

@Service
public class ModelServiceImpl implements ModelService {
    
    @Override
    public Map<String, JsonNode> extractVariables(String model) {
        return getJsonVariablesMap(model);
    }
    
    private Map<String, JsonNode> getJsonVariablesMap(String model) {
        try {
            return getKeyValuePair(model)
                    .entrySet()
                    .stream()
                    .unordered()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                                              entry -> convertStringToJson(entry.getValue())));
        } catch (ConversionError err) {
            throw new InvalidModelException(model, err);
        }
    }
    
    private Map<String, String> getKeyValuePair(String model) {
        try {
            return splitVariables(model)
                    .stream()
                    .unordered()
                    .collect(Collectors.toMap(var -> var.split("=", 2)[0],
                                              x -> x.split("=", 2)[1]));
        } catch (ArrayIndexOutOfBoundsException err) {
            throw new InvalidModelException(model, "ArrayIndexOutOfBoundsException: " + err.getMessage(), err);
        }
    }
    
    private List<String> splitVariables(String model) {
        return Arrays.asList(model.split("\\s*(\\r?\\n)+\\s*"));
    }
}
