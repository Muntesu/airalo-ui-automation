package com.airalo.webdriver;


import static com.airalo.configuration.ConfigurationManager.config;

import com.airalo.environment.Browser;
import java.time.Duration;
import java.util.Objects;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;


public class DriverFactory {

    public static final long DRIVER_WAIT_TIME = 5;
    private static final long SCRIPT_WAIT_TIME = 10;
    private static final long PAGE_LOAD_WAIT_TIME = 15;

    public static WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    public static void setupDriver() {
        Browser browser = Browser.valueOfLabel(config().browser());
        startDriver(Objects.requireNonNullElse(browser, Browser.CHROME));
    }

    public static void quitDriver() {
        DriverManager.quitDriver();
    }

    private static void startDriver(Browser browser) {
        final WebDriver driver = switch (browser) {
            case FIREFOX -> startFirefoxDriver();
            case SAFARI -> startSafariDriver();
            case HEADLESS -> startHeadlessDriver();
            default -> startChromeDriver();
        };
        setDriverDefaults(driver);
        WebDriverEventListener eventListener = new WebDriverEventListener();
        WebDriver decoratedDriver = new EventFiringDecorator<>(eventListener).decorate(driver);
        DriverManager.setDriver(decoratedDriver);
    }


    private static WebDriver startFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        setFirefoxOptions(options);
        return new FirefoxDriver(options);
    }

    private static WebDriver startSafariDriver() {
        SafariOptions options = new SafariOptions();
        setSafariOptions(options);
        return new SafariDriver(options);
    }

    private static WebDriver startChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        setChromeOptions(options);
        return new ChromeDriver(options);
    }

    private static WebDriver startHeadlessDriver() {
        ChromeOptions options = new ChromeOptions();
        setChromeOptions(options);
        options.addArguments("--headless");
        options.addArguments("--start-fullscreen");
        options.addArguments("--disable-popup-blocking"); // Prevent pop-ups
        return new ChromeDriver(options);
    }

    private static void setDriverDefaults(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DRIVER_WAIT_TIME));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(SCRIPT_WAIT_TIME));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_WAIT_TIME));
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().maximize();
    }

    private static void setChromeOptions(ChromeOptions options) {
        System.setProperty("webdriver.chrome.silentOutput", "true");
        options.addArguments("start-maximized");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-crash-reporter");
        options.addArguments("--disable-notifications");
        options.addArguments("--no-sandbox");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
    }

    private static void setSafariOptions(SafariOptions options) {
        options.setAutomaticInspection(false);  // Disables Web Inspector auto-open
        options.setAutomaticProfiling(false); // Disables profiling
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
    }

    private static void setFirefoxOptions(FirefoxOptions options) {
        options.addArguments("--start-maximized"); // Maximizes the window
        options.addArguments("--disable-infobars"); // Hides info bars
        options.addArguments("--disable-popup-blocking"); // Blocks popups
        options.addArguments("--disable-notifications"); // Disables notifications
        options.addArguments("--no-sandbox"); // Disables sandboxing (useful for CI/CD)
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL); // Ensures standard page load

        options.addPreference("browser.tabs.remote.autostart", false);
        options.addPreference("browser.tabs.remote.autostart.2", false);
        options.addPreference("extensions.htmlaboutaddons.recommendations.enabled", false);
        options.addPreference("app.update.auto", false);

        // Enables headless mode if required
        if (System.getProperty("headless") != null && System.getProperty("headless").equalsIgnoreCase("true")) {
            options.addArguments("--headless");
        }
    }

}
