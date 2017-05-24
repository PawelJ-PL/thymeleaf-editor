package info.ns01.thymeleaf_editor.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class FormPage  {

    private static final String EDITOR_PAGE_ADDRESS="http://localhost/editor";

    public static FormPage to(WebDriver driver) {
        driver.get(EDITOR_PAGE_ADDRESS);
        return PageFactory.initElements(driver, FormPage.class);
    }

}
