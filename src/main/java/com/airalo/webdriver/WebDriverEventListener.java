package com.airalo.webdriver;

import static com.airalo.logger.LoggerManager.logError;
import static com.airalo.logger.LoggerManager.logInfo;
import static com.airalo.util.JsExecutor.highlight;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

public class WebDriverEventListener implements WebDriverListener {

    public void beforeGet(WebDriver driver, String url) {
        logInfo("Navigating to {}", url);
    }

    public void beforeRefresh(WebDriver.Navigation refresh) {
        logInfo("Refreshing the page");
    }

    public void beforeClick(WebElement element) {
        highlight(element);
        logInfo("Clicking on {}", getElementName(element));
    }

    public void beforeSendKeys(WebElement element, CharSequence[] keysToSend) {
        highlight(element);
        logInfo("Changing value {} for {}", keysToSend, getElementName(element));
    }

    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        Throwable cause = e.getCause(); // unwrap the actual exception
        logError("Selenium error in method {}: {}", method.getName(), cause.getClass().getName());
        logError("Exception message: {}", cause.getMessage());
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
