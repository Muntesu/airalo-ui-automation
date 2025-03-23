package com.airalo.page_object;

import com.airalo.util.JsExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PackageCard extends GenericPackage {

    public PackageCard(WebElement root) {
        super(root);
    }

    public void clickBuyPackage() {
        var buyButton = root.findElement(By.cssSelector("[data-testid=\"buy-button\"]"));
        JsExecutor.scrollIntoView(buyButton);
        buyButton.click();
    }


}
