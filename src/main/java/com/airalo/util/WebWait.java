package com.airalo.util;

import static com.airalo.webdriver.DriverFactory.DRIVER_WAIT_TIME;
import static com.airalo.webdriver.DriverFactory.getDriver;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebWait {

    public static void waitForUrlToBe(String expectedUrl) {
        getWait().until(ExpectedConditions.urlToBe(expectedUrl));
    }

    public static void scrollAndWaitUntilElementVisible(By locator) {
        try {
            WebElement element = getWait().until(
                ExpectedConditions.presenceOfElementLocated(locator));
            JsExecutor.scrollIntoView(element);
            getWait().until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Element not visible after scroll: " + locator, e);
        }
    }

    public static void untilElementInteractable(By locator) {
        try {
            getWait().until(ExpectedConditions.elementToBeClickable(locator));
        } catch (WebDriverException e) {
            throw new WebDriverException("The element is still loading" + e.getMessage());
        }
    }

    private static WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(DRIVER_WAIT_TIME));
    }
}
