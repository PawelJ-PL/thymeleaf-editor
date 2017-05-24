package info.ns01.thymeleaf_editor;

import info.ns01.thymeleaf_editor.pages.FormPage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ApplicationRunner.class})
@TestPropertySource(properties = { "spring.mvc.static-path-pattern=/static/**" })
public class TestEditorForm {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private WebDriver driver;

    private FormPage formPage;

    @Autowired
    private Environment environment;

    @Before
    public void setUp() {
        driver = MockMvcHtmlUnitDriverBuilder.webAppContextSetup(webApplicationContext).build();
        formPage = FormPage.to(driver);
    }

    @Test
    public void shouldGetEditorForm() throws IOException {
        //given

    }

}
