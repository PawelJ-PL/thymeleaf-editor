package info.ns01.thymeleaf_editor.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

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

    public static FormPage to(WebDriver driver) {
        driver.get(EDITOR_PAGE_ADDRESS);
        return PageFactory.initElements(driver, FormPage.class);
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

    public List<String> getSelectModeOptions() {
        return getSelectOptionsAsStrings(getModeSelectList().getOptions());
    }
}
