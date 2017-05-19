package info.ns01.thymeleaf_editor.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;
import info.ns01.thymeleaf_editor.exceptions.InvalidModelException;
import info.ns01.thymeleaf_editor.services.impl.ModelServiceImpl;
import info.ns01.thymeleaf_editor.utils.JsonUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelServiceTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    private ModelService modelService;
    
    @Before
    public void setUp() {
        modelService = new ModelServiceImpl();
    }
    
    @Test
    public void shouldExtractVariables() throws IOException {
        //given
        String model = resourceAsString("valid_model.txt");
        JsonNode var1 = JsonUtils.convertStringToJson("{'x':1,'y':'abc','z':{'q':'NN', 'w':222, 'e':{'a':'a'}}}".replace("'", "\""));
        JsonNode var2 = JsonUtils.convertStringToJson("{'var1':100, 'var2':'KKK'}".replace("'", "\""));
        JsonNode var3 = JsonUtils.convertStringToJson("{'a':33, 'b':{'f':'x'}}".replace("'", "\""));
        Map<String, JsonNode> expectedResult = ImmutableMap.of("aaa", var1, "bbb", var2, "ccc", var3);
        
        //when
        Map<String, JsonNode> result = modelService.extractVariables(model);
        
        //then
        assertThat(result).isEqualTo(expectedResult);
    }
    
    @Test
    public void shouldThrowExceptionWhenModelOrdinaryString() {
        //given
        String model = "ABC";
        
        //when
        thrown.expect(InvalidModelException.class);
        modelService.extractVariables(model);
    }
    
    @Test
    public void shouldThrowExceptionWhenNonJsonValue() {
        //given
        String model = "XX=YY";
        
        //when
        thrown.expect(InvalidModelException.class);
        modelService.extractVariables(model);
    }
    
    @Test
    public void shouldThrowExceptionOnInvalidJsonModel() throws IOException {
        //given
        String model = resourceAsString("invalid_quoted_json_model.txt");
        
        //when
        thrown.expect(InvalidModelException.class);
        modelService.extractVariables(model);
    }
    
    private String resourceAsString(String resourceName) throws IOException {
        String dir = "services/" + this.getClass().getSimpleName() + "/";
        String resource = dir + resourceName;
        URL url = Resources.getResource(resource);
        return Resources.toString(url, StandardCharsets.UTF_8);
    }
    
}
