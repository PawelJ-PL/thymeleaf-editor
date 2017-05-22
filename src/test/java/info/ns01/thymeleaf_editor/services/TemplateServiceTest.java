package info.ns01.thymeleaf_editor.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import info.ns01.thymeleaf_editor.utils.JsonUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.exceptions.TemplateInputException;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TemplateServiceTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Autowired
    private TemplateService templateService;
    
    @Test
    public void shouldProcessBasicTemplate() {
        //given
        String template = "<div th:text='TestData'>ABCDE</div>".replace("'", "\"");
        String mode = StandardTemplateModeHandlers.HTML5.getTemplateModeName();
        String expectedData = "<div>TestData</div>";
        
        //when
        String result = templateService.processTemplate(template, mode);
        
        //then
        assertThat(result).isEqualTo(expectedData);
    }
    
    @Test
    public void shouldThrowExceptionOnInvalidHtml() {
        //given
        String template = "<div th:text='TestData'>ABCDE".replace("'", "\"");
        String mode = StandardTemplateModeHandlers.HTML5.getTemplateModeName();
        
        //when
        thrown.expect(TemplateInputException.class);
        String result = templateService.processTemplate(template, mode);
    }
    
    @Test
    public void shouldEvaluateVariableFromJsonNodeObjectModel() throws JsonProcessingException{
        //given
        String template = "<div th:text='${person.lastName}'>ABCDE</div>";
        String mode = StandardTemplateModeHandlers.HTML5.getTemplateModeName();
        ObjectNode var1 = JsonNodeFactory.instance.objectNode();
        var1.put("firstName", "John");
        var1.put("lastName", "Smith");
        var1.put("age", 34);
        Map<String, Object> model = ImmutableMap.of("person", JsonUtils.convertNodeToOrdinaryObject(var1));
        String expectedData = "<div>Smith</div>";
        
        //when
        String result = templateService.processTemplate(template, model, mode);
        
        //then
        assertThat(result).isEqualTo(expectedData);
    }
    
    @Test
    public void shouldEvaluateVariableFromRealObjectModel() {
        //given
        String template = "<div th:text='${address.city}'>ABCDE</div><div th:text='${address.postalCode}'>QWERTY</div>";
        String mode = StandardTemplateModeHandlers.HTML5.getTemplateModeName();
        TestObject var1 = new TestObject("Spandauer Str.", "Berlin", 10178);
        Map<String, Object> model = ImmutableMap.of("address", var1);
        String expectedResult = "<div>Berlin</div><div>10178</div>";
        
        //when
        String result = templateService.processTemplate(template, model, mode);
        
        //then
        assertThat(result).isEqualTo(expectedResult);
    }
    
    @Test
    public void shouldEvaluateStringVariableModel() {
        //given
        String template = "<div th:text='${var1}'>ABCDE</div>";
        String mode = StandardTemplateModeHandlers.HTML5.getTemplateModeName();
        String var1 = "testString";
        Map<String, Object> model = ImmutableMap.of("var1", var1);
        String expectedResult = "<div>testString</div>";
        
        //when
        String result = templateService.processTemplate(template, model, mode);
        
        //then
        assertThat(result).isEqualTo(expectedResult);
    }
    
    @Test
    public void shouldEvaluateNumericVariableModel() {
        //given
        String template = "<div th:text='${var1}'>ABCDE</div>";
        String mode = StandardTemplateModeHandlers.HTML5.getTemplateModeName();
        Double var1 = 12.01;
        Map<String, Object> model = ImmutableMap.of("var1", var1);
        String expectedResult = "<div>12.01</div>";
        
        //when
        String result = templateService.processTemplate(template, model, mode);
        
        //then
        assertThat(result).isEqualTo(expectedResult);
    }
    
    @Test
    public void shouldEvaluateBooleanVariableModel() {
        //given
        String template = "<div th:text='${var1}'>ABCDE</div>";
        String mode = StandardTemplateModeHandlers.HTML5.getTemplateModeName();
        Map<String, Object> model = ImmutableMap.of("var1", true);
        String expectedResult = "<div>true</div>";
        
        //when
        String result = templateService.processTemplate(template, model, mode);
        
        //then
        assertThat(result).isEqualTo(expectedResult);
    }
    
    @Test
    public void shouldEvaluateCollectionVariableModel() {
        //given
        String template = "<ul><li th:each='elem : ${var1}' th:text='${elem}' /></ul>";
        String mode = StandardTemplateModeHandlers.HTML5.getTemplateModeName();
        List<String> var1 = ImmutableList.of("AAA", "BBB", "CCC");
        Map<String, Object> model = ImmutableMap.of("var1", var1);
        String expectedResult = "<ul><li>AAA</li><li>BBB</li><li>CCC</li></ul>";
        
        //when
        String result = templateService.processTemplate(template, model, mode);
        
        //then
        assertThat(result).isEqualTo(expectedResult);
    }
    
}


class TestObject {
    
    private String street;
    
    private String city;
    
    private Integer postalCode;
    
    TestObject(String street, String city, Integer postalCode) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }
    
    TestObject() {
    }
    
    public String getStreet() {
        return street;
    }
    
    public String getCity() {
        return city;
    }
    
    public Integer getPostalCode() {
        return postalCode;
    }
}
