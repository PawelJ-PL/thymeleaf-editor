package info.ns01.thymeleaf_editor.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("WeakerAccess")
public abstract class BaseWebPage {

    WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    protected List<String> getSelectOptionsAsStrings(List<WebElement> options) {
        return options.stream()
                .map(option -> {
                    assertThat(option.getTagName()).isEqualTo("option");
                    return option;
                })
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

}
