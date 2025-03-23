package com.airalo.page_object;

import static com.airalo.configuration.ConfigurationManager.config;
import static com.airalo.logger.LoggerManager.logInfo;
import static com.airalo.util.WebWait.waitForUrlToBe;

import com.airalo.util.JsExecutor;
import com.airalo.util.WebWait;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Getter
@AllArgsConstructor
public class LoginPage extends BasePage {

    private final WebDriver driver;

    public void isLoaded() {
        WebWait.scrollAndWaitUntilElementVisible(By.cssSelector("form"));
    }
}
