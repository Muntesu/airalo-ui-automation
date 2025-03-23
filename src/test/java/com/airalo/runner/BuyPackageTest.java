package com.airalo.runner;


import static com.airalo.configuration.ConfigurationManager.config;
import static com.airalo.logger.LoggerManager.logInfo;
import static com.airalo.util.WebWait.waitForUrlToBe;
import static org.assertj.core.api.Assertions.assertThat;

import com.airalo.page_object.LoginPage;
import com.airalo.page_object.MainPage;
import com.airalo.webdriver.DriverFactory;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.javamoney.moneta.Money;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BuyPackageTest extends BaseTest {

    @Test(groups = "buy", dataProvider = "countries")
    public void buyRandomPackage(String coverage, String data, String validity, Double price) {
        createTest("Airalo Search Test");
        logInfo("Starting Buy Random Package test");

        MainPage mainPage = new MainPage(DriverFactory.getDriver());

        logInfo("Opened the main page on " + mainPage.getCurrentUrl());

        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(mainPage.getNavigationMenu().isPartnerWithUsDisplayed())
                .as("The Partner With Us section is not present in navigation menu")
                .isTrue();
            softly.assertThat(mainPage.getNavigationMenu().isAboutUsDisplayed())
                .as("The About Us section is not present in navigation menu")
                .isTrue();
            softly.assertThat(mainPage.getNavigationMenu().isLoginItemDisplayed())
                .as("The Login section is not present in navigation menu")
                .isTrue();
            softly.assertThat(mainPage.getNavigationMenu().isCurrencyDisplayed())
                .as("The Currency section is not present in navigation menu")
                .isTrue();
            softly.assertThat(mainPage.getNavigationMenu().isLanguageNavigationDisplayed())
                .as("The Language section is not present in navigation menu")
                .isTrue();
        }

        mainPage.search(coverage);

        var packages = mainPage.getPackages();
        assertThat(packages.size())
            .as("The list of packages is empty")
            .isGreaterThan(1);

        var aPackage = mainPage.getPackage((p -> p.getData().equals(data)));
        var packageDTO = mainPage.getPackageDTO(aPackage);

        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(packageDTO.getCoverage()).as("The coverage is not correct")
                .isEqualTo(coverage);
            softly.assertThat(packageDTO.getData()).as("The data is not correct")
                .isEqualTo(data);
            softly.assertThat(packageDTO.getValidity()).as("The validity is not correct")
                .isEqualTo(validity);
            softly.assertThat(packageDTO.getPrice()).as("The price is not correct")
                .isEqualTo(Money.of(price, "USD"));
        }

        aPackage.clickBuyPackage();

        var packageCard = mainPage.getPackageCard();
        var packageCardDTO = mainPage.getPackageCardDTO(packageCard);

        assertThat(packageDTO.toString()).as("The packages data does not match")
            .isEqualTo(packageCardDTO.toString());

        assertThat(mainPage.getCurrentUrl()).as("The page URL doesn't contain country name")
            .contains(coverage.toLowerCase());

        packageCard.clickBuyPackage();

        // TODO temporary workaround as driver keeps the source of previous page
        waitForUrlToBe(config().loginUrl());
        mainPage.getDriver().navigate().refresh();

        var loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.isLoaded();

        assertThat(loginPage.getCurrentUrl()).as("The URL didn't change")
            .isEqualTo(config().loginUrl());
    }

    @DataProvider(name = "countries")
    private Object[][] countriesDataProvider() {
        return new Object[][]{{"Japan", "1 GB", "7 Days", 4.50}};
    }
}
