package com.airalo.util;


import static com.airalo.webdriver.DriverFactory.DRIVER_WAIT_TIME;
import static com.airalo.webdriver.DriverFactory.getDriver;

import java.time.Duration;
import java.util.Objects;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JsExecutor {

    public static void waitForPageToLoad() {
        final WebDriverWait wait = new WebDriverWait(getDriver(),
            Duration.ofSeconds(DRIVER_WAIT_TIME));
        ExpectedCondition<Boolean> jsLoad = driver -> Objects.equals(getJs()
            .executeScript("return document.readyState"), "complete");
        wait.until(jsLoad);
    }

    public static void jsClick(WebElement element) {
        getJs().executeScript("arguments[0].click();", element);
    }

    public static void highlight(WebElement element) {
        getJs().executeScript("arguments[0].style.border='2px solid red'", element);
    }

    public static void scrollIntoView(WebElement element) {
        try {
            getJs().executeScript(
                "arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", element);
        } catch (JavascriptException e) {
            getJs().executeScript("arguments[0].scrollIntoView(true);", element);
        }
    }

    public static void waitForLoaderToDisappear(String cssSelector) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(DRIVER_WAIT_TIME)).until(driver -> {
            String script = "let el = document.querySelector('" + cssSelector
                + "'); return !el || el.offsetParent === null;";
            return (Boolean) getJs().executeScript(script);
        });
    }

    public static void randomScroll() {
        getJs().executeScript("window.scrollTo(0, 1)");
    }

    private static JavascriptExecutor getJs() {
        return (JavascriptExecutor) getDriver();
    }
}
