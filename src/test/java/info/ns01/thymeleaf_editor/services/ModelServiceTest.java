package info.ns01.thymeleaf_editor.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
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
    public void shouldExtractObjectVariables() throws IOException {
        //given
        String model = resourceAsString("valid_model.txt");
        JsonNode var1 =
                JsonUtils.convertStringToJson("{'x':1,'y':'abc','z':{'q':'NN', 'w':222, 'e':{'a':'a'}}}"
                        .replace("'", "\""));
        JsonNode var2 = JsonUtils.convertStringToJson("{'var1':100, 'var2':'KKK'}".replace("'", "\""));
        JsonNode var3 = JsonUtils.convertStringToJson("{'a':33, 'b':{'f':'x'}}".replace("'", "\""));
        Map<String, Object> expectedResult = ImmutableMap.of("aaa", JsonUtils.convertNodeToOrdinaryObject(var1),
                                                             "bbb", JsonUtils.convertNodeToOrdinaryObject(var2),
                                                             "ccc", JsonUtils.convertNodeToOrdinaryObject(var3));
        
        //when
        Map<String, Object> result = modelService.extractVariables(model);
        
        //then
        assertThat(result).isEqualTo(expectedResult);
    }
    
    @Test
    public void shouldExtractStringVariables() throws IOException {
        //given
        String model = resourceAsString("string_model.txt");
        
        //when
        Map<String, Object> result = modelService.extractVariables(model);
        
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get("var1")).isEqualTo("ABCD");
        assertThat(result.get("var2")).isEqualTo("QWERTY");
    }
    
    @Test
    public void shouldExtractNumericVariables() throws IOException {
        //given
        String model = resourceAsString("numeric_model.txt");
    
        //when
        Map<String, Object> result = modelService.extractVariables(model);
        
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get("var1")).isEqualTo(123);
        assertThat(result.get("var2")).isEqualTo(12.01);
    }
    
    @Test
    public void shouldExtractCollectionVariables() throws IOException {
        //given
        String model = resourceAsString("collection_model.txt");
        
        //when
        Map<String, Object> result = modelService.extractVariables(model);
        
        //then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get("var1")).isEqualTo(ImmutableList.of("aa", "bb", "cc"));
        assertThat(result.get("var2")).isEqualTo(ImmutableList.of(123, 100, 99));
        assertThat(result.get("var3")).isEqualTo(ImmutableList.of(12, "abc", true));
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
