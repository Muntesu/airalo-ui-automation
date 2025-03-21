package com.airalo.page_object;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class NavigationMenu {

    private final WebDriver driver;

    public NavigationMenu(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[data-testid=\"nav-item-partner-with-us\"]")
    private WebElement partnerWithUs;

    @FindBy(css = "[data-testid=\"nav-item-about-us\"]")
    private WebElement aboutUs;

    @FindBy(css = "[data-testid=\"nav-item-login\"")
    private WebElement loginItem;

    // TODO find a generic locator for different currencies
    @FindBy(css = "[data-testid=\"$ USD-header-language\"")
    private WebElement currency;

    // TODO find a generic locator for different languages
    @FindBy(css = "[data-testid=\"English-header-language\"")
    private WebElement language;

    public boolean isPartnerWithUsDisplayed() {
        return partnerWithUs.isDisplayed();
    }

    public boolean isAboutUsDisplayed() {
        return aboutUs.isDisplayed();
    }

    public boolean isLoginItemDisplayed() {
        return loginItem.isDisplayed();
    }

    public boolean isCurrencyDisplayed() {
        return currency.isDisplayed();
    }

    public boolean isLanguageNavigationDisplayed() {
        return language.isDisplayed();
    }

}
