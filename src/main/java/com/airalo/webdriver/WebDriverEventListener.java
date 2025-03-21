package com.airalo.webdriver;

import static com.airalo.logger.LoggerManager.logError;
import static com.airalo.logger.LoggerManager.logInfo;
import static com.airalo.util.JsExecutor.highlight;

import java.util.Objects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

class WebDriverEventListener implements WebDriverListener {

    public void beforeNavigateTo(String url, WebDriver driver) {
        logInfo("Navigating to {}", url);
    }

    public void beforeNavigateRefresh(WebDriver driver) {
        logInfo("Refreshing the page");
    }

    public void beforeClickOn(WebElement element, WebDriver driver) {
        highlight(element);
        logInfo("Clicking on {}", getElementName(element));
    }

    public void beforeChangeValueOf(WebElement element, WebDriver driver,
        CharSequence[] keysToSend) {
        highlight(element);
        logInfo("Changing value {} for {}", keysToSend, getElementName(element));
    }

    public void onException(Throwable throwable, WebDriver driver) {
        logError("Selenium runtime exception: {}", throwable.getClass().getName());
        logError("Exception message: {}", throwable.getMessage());
    }

    private String getElementName(WebElement element) {
        if (!element.getText().isEmpty()) {
            return element.getText();
        }
        if (element.getDomAttribute("name") != null && !Objects.equals(
            element.getDomAttribute("name"), "")) {
            return element.getDomAttribute("name");
        }
        if (element.getDomAttribute("id") != null && !Objects.equals(
            element.getDomAttribute("id"), "")) {
            return element.getDomAttribute("id");
        }
        if (element.getDomAttribute("class") != null && !Objects.equals(
            element.getDomAttribute("class"), "")) {
            return element.getDomAttribute("class");
        }

        return "undefined element";

    }
}
