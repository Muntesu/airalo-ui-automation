package com.airalo.page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PackageCard extends GenericPackage {

    public PackageCard(WebElement root) {
        super(root);
    }

    public void clickBuyPackage() {
        root.findElement(By.cssSelector("[data-testid=\"buy-button\"]")).click();
    }


}
