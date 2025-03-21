package com.airalo.page_object;

import static com.airalo.util.PriceParser.parsePrice;

import javax.money.MonetaryAmount;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public abstract class GenericPackage {

    protected WebElement root;

    public GenericPackage(WebElement root) {
        this.root = root;
    }

    public String getCoverage() {
        return root.findElement(By.cssSelector("[data-testid=\"COVERAGE-value\"]")).getText();
    }

    public String getData() {
        return root.findElement(By.cssSelector("[data-testid=\"DATA-value\"]")).getText();
    }

    public String getValidity() {
        return root.findElement(By.cssSelector("[data-testid=\"VALIDITY-value\"]")).getText();
    }

    public MonetaryAmount getPrice() {
        return parsePrice(
            root.findElement(By.cssSelector("[data-testid=\"PRICE-value\"]")).getText());
    }

    public abstract void clickBuyPackage();
}
