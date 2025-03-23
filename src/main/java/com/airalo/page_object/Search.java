package com.airalo.page_object;

import static com.airalo.logger.LoggerManager.logError;

import com.airalo.util.JsExecutor;
import com.airalo.util.WebWait;
import java.util.List;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class Search {

    private final WebDriver driver;

    public Search(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[data-testid=\"search-input\"]")
    private WebElement searchInput;

    @FindBy(css = ".countries-list.position-absolute")
    private WebElement countriesListContainer;

    private static final String countriesListLoader = ".countries-list-loader span";
    private static final By searchInputLocator = By.cssSelector("[data-testid=\"search-input\"]");
    private static final By countriesList = By.cssSelector(".countries-list.position-absolute");

    public Search startCountriesSearch(String country) {
        WebWait.untilElementInteractable(searchInputLocator);
        searchInput.clear();
        searchInput.sendKeys(country);
        return this;
    }

    public Search finishCountriesSearch() {
        WebWait.scrollAndWaitUntilElementVisible(By.cssSelector(countriesListLoader));
        JsExecutor.waitForLoaderToDisappear(countriesListLoader);
        WebWait.scrollAndWaitUntilElementVisible(countriesList);
        return this;
    }

    public void openFoundCountry(String country) {
        List<WebElement> countries = countriesListContainer.findElements(
            By.cssSelector("[data-testid=\"" + country + "-name\"]"));
        if (!countries.isEmpty()) {
            countries.get(0).click();
        } else {
            logError("The country {} was not found in the list", country);
        }
    }
}
