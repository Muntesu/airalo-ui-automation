package com.airalo.page_object;

import com.airalo.webdriver.DriverFactory;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Getter
public abstract class BasePage {

    private final WebDriver driver;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        PageFactory.initElements(driver, this);
    }

    public String getCurrentUrl() {
        return this.driver.getCurrentUrl();
    }

}
