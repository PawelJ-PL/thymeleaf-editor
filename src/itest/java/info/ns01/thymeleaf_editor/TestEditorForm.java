package info.ns01.thymeleaf_editor;

import info.ns01.thymeleaf_editor.models.enums.FlashMessageType;
import info.ns01.thymeleaf_editor.pages.FormPage;
import info.ns01.thymeleaf_editor.utils.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.openqa.selenium.NoSuchElementException;
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
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ApplicationRunner.class})
@TestPropertySource(properties = { "spring.mvc.static-path-pattern=/static/**" })
public class TestEditorForm {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    @After
    public void tearDown() {
        if (driver != null) {
            driver.close();
        }
    }

    @Test
    public void shouldGetEditorForm() throws IOException {
        //then
        assertThat(formPage.getTemplateField().getText()).isEmpty();
        assertThat(formPage.getTemplateField().isDisplayed()).isTrue();
        assertThat(formPage.getTemplateField().isEnabled()).isTrue();

        assertThat(formPage.getModelField().getText()).isEmpty();
        assertThat(formPage.getModelField().isDisplayed()).isTrue();
        assertThat(formPage.getModelField().isEnabled()).isTrue();

        assertThat(new HashSet<>(formPage.getSelectModeOptions())).isEqualTo(Utils.getStandardTemplateModesNames());

        assertThat(formPage.getResultField().getText()).isEmpty();
        assertThat(formPage.getResultField().getAttribute("readonly")).isEqualTo("true");

        assertThat(formPage.getAlertMessages()).isEmpty();

        thrown.expect(NoSuchElementException.class);
        thrown.expectMessage("Unable to locate element with ID: render_page_link");
        assertThat(formPage.getRenderPageLink().isDisplayed()).isFalse();
    }

    @Test
    public void shouldPostValidData() throws IOException {
        //given
        String template = "<div th:text=\"${var1}\">ABC</div>";
        String model = "var1=\"XYZ\"";
        String mode = "HTML5";
        String expectedResult = "<div>XYZ</div>";

        //when
        FormPage resultPage = formPage.submitAndGetResultPage(template, mode, model);

        //then
        assertThat(resultPage.getTemplateField().getText()).isEqualTo(template);
        assertThat(resultPage.getModelField().getText()).isEqualTo(model);
        assertThat(resultPage.getModeSelectList().getFirstSelectedOption().getText()).isEqualTo(mode);
        assertThat(resultPage.getResultField().getText()).isEqualTo(expectedResult);
        assertThat(resultPage.getRenderPageLink().isDisplayed()).isTrue();
        assertThat(resultPage.getAlertMessages()).isEmpty();
    }

    @Test
    public void shouldGenerateErrorWhenNoDataProvided() throws IOException {
        //given
        String expectedMessage = "Template may not be empty";

        //when
        FormPage resultPage = formPage.submitAndGetResultPage(null, null, null);

        //then
        assertThat(resultPage.getTemplateField().getText()).isEmpty();
        assertThat(resultPage.getModelField().getText()).isEmpty();
        assertThat(resultPage.getAlertMessages().size()).isEqualTo(1);
        assertThat(resultPage.containsAlertWithText(resultPage.getAlertMessages(FlashMessageType.WARNING),
                expectedMessage)).isTrue();
    }

    @Test
    public void shouldGenerateErrorOnInvalidModel() throws IOException {
        //given
        String template = "<div th:text=\"${var1}\">ABC</div>";
        String model = "var1";
        String mode = "HTML5";
        String expectedMessage = "Invalid model. See usage below";

        //when
        FormPage resultPage = formPage.submitAndGetResultPage(template, mode, model);

        //then
        assertThat(resultPage.getTemplateField().getText()).isEqualTo(template);
        assertThat(resultPage.getModelField().getText()).isEqualTo(model);
        assertThat(resultPage.getModeSelectList().getFirstSelectedOption().getText()).isEqualTo(mode);
        assertThat(resultPage.getAlertMessages().size()).isEqualTo(1);
        assertThat(resultPage.containsAlertWithText(resultPage.getAlertMessages(FlashMessageType.DANGER),
                expectedMessage)).isTrue();
        thrown.expect(NoSuchElementException.class);
        thrown.expectMessage("Unable to locate element with ID: render_page_link");
        assertThat(formPage.getRenderPageLink().isDisplayed()).isFalse();
        assertThat(resultPage.getAlertMessages()).isEmpty();
    }

    @Test
    public void shouldGenerateErrorOnInvalidTemplate() throws IOException {
        //given
        String template = "<div th:text=\"${var1}\">ABC";
        String model = "var1=\"XYZ\"";
        String mode = "HTML5";
        String expectedMessage = "Invalid model. See usage below";

        //when
        FormPage resultPage = formPage.submitAndGetResultPage(template, mode, model);

        //then
        assertThat(resultPage.getTemplateField().getText()).isEqualTo(template);
        assertThat(resultPage.getModelField().getText()).isEqualTo(model);
        assertThat(resultPage.getModeSelectList().getFirstSelectedOption().getText()).isEqualTo(mode);
        assertThat(resultPage.getAlertMessages().size()).isEqualTo(1);
        thrown.expect(NoSuchElementException.class);
        thrown.expectMessage("Unable to locate element with ID: render_page_link");
        assertThat(formPage.getRenderPageLink().isDisplayed()).isFalse();
        assertThat(resultPage.getAlertMessages()).isEmpty();
    }

}
