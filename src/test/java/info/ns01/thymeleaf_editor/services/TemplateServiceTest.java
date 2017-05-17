package info.ns01.thymeleaf_editor.services;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.exceptions.TemplateInputException;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;

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
    
}
