package info.ns01.thymeleaf_editor.pages;

import info.ns01.thymeleaf_editor.models.enums.FlashMessageType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FormPage extends BaseWebPage {

    private static final String EDITOR_PAGE_ADDRESS="http://localhost/editor";

    @FindBy(id = "model_field")
    private WebElement modelField;

    @FindBy(id = "template_field")
    private WebElement templateField;

    @FindBy(id = "template_mode")
    private WebElement modeSelectList;

    private Select selectMode;

    @FindBy(id = "generate_button")
    private WebElement generateButton;

    @FindBy(id = "render_page_link")
    private WebElement renderPageLink;

    @FindBy(id = "result_field")
    private WebElement resultField;

    @FindBy(className = "alert")
    private List<WebElement> alertMessages;

    public static FormPage to(WebDriver driver) {

        driver.get(EDITOR_PAGE_ADDRESS);
        FormPage result = PageFactory.initElements(driver, FormPage.class);
        result.setDriver(driver);
        return result;
    }

    public WebElement getModelField() {
        return modelField;
    }

    public WebElement getTemplateField() {
        return templateField;
    }

    @SuppressWarnings("WeakerAccess")
    public Select getModeSelectList() {
        if (selectMode == null) {
            selectMode = new Select(modeSelectList);
        }
        return selectMode;
    }

    public WebElement getGenerateButton() {
        return generateButton;
    }

    public WebElement getRenderPageLink() {
        return renderPageLink;
    }

    public WebElement getResultField() {
        return resultField;
    }

    public List<WebElement> getAlertMessages() {
        return alertMessages;
    }

    public List<String> getSelectModeOptions() {
        return getSelectOptionsAsStrings(getModeSelectList().getOptions());
    }

    public FormPage submitAndGetResultPage(String template, String mode, String model) {
        if (model != null) {
            modelField.sendKeys(model);
        }
        if (template != null) {
            templateField.sendKeys(template);
        }
        if (mode != null) {
            getModeSelectList().selectByValue(mode);
        }
        generateButton.click();
        FormPage result = PageFactory.initElements(driver, FormPage.class);
        result.setDriver(driver);
        return result;
    }

    public List<WebElement> getAlertMessages(FlashMessageType type) {
        return alertMessages
                .stream()
                .filter(alert -> getClasses(alert).contains(String.format("alert-%s", type.toString())))
                .collect(Collectors.toList());
    }

    public boolean containsAlertWithText(List<WebElement> alerts, String text) {
        for (WebElement alert : alerts) {
            if (!getClasses(alert).contains("alert")) {
                throw new RuntimeException(String.format("%s is not alert", alert));
            }
            WebElement message = alert.findElement(By.className("alert_message"));
            if (message.getText().equals(text)) {
                return true;
            }
        }
        return false;
    }

    private List<String> getClasses(WebElement element) {
        return Arrays.asList(element.getAttribute("class").split("\\s+"));
    }
}
