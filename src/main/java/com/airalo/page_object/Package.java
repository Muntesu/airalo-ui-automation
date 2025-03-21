package com.airalo.page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Package extends GenericPackage {

    public Package(WebElement root) {
        super(root);
    }

    public void clickBuyPackage() {
        root.findElement(By.cssSelector("[data-testid=\"esim-button\"]")).click();
    }

}
