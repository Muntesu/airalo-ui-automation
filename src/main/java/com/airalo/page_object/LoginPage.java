package com.airalo.page_object;

import com.airalo.util.WebWait;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Getter
@AllArgsConstructor
public class LoginPage extends BasePage {

    private final WebDriver driver;

    public void isLoaded() {
        WebWait.untilElementVisible(By.className("auth-container"));
    }
}
