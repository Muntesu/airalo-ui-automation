package com.airalo.util;

import static com.airalo.webdriver.DriverFactory.DRIVER_WAIT_TIME;
import static com.airalo.webdriver.DriverFactory.getDriver;
import static org.awaitility.Durations.ONE_HUNDRED_MILLISECONDS;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebWait {

    public static void untilElementVisible(By menuLocator) {
        try {
            getWait().until(ExpectedConditions.visibilityOfElementLocated(menuLocator));
        } catch (WebDriverException e) {
            throw new WebDriverException("The element is still not visible" + e.getMessage());
        }
    }

    public static void untilElementInvisible(WebElement element) {
        try {
            getWait().until(ExpectedConditions.invisibilityOf(element));
        } catch (WebDriverException e) {
            throw new WebDriverException("The element is still visible" + e.getMessage());
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
