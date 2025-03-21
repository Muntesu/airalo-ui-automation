package com.airalo.runner;


import static com.airalo.configuration.ConfigurationManager.config;
import static com.airalo.logger.LoggerManager.logInfo;
import static org.assertj.core.api.Assertions.assertThat;

import com.airalo.page_object.LoginPage;
import com.airalo.page_object.MainPage;
import com.airalo.webdriver.DriverFactory;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.javamoney.moneta.Money;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

//
//      Act for the user located in both japan and outside japan - suggestion
//      1. Open the https://www.airalo.com/ page
//      2. Check menu :partner with us, about us, login/logout,
//      3. Check the page title
//      ... other checks may be required to assure the page was loaded
//      4. Check the search is present
//      5. Type the country name (generic)
//      6. Check the country is available in dropdown
//      7. Click the country name
//      8. Check the page url has changed, and it follows
//           the https://www.airalo.com/{country_name}-esim pattern
//      9. Check the local eSIMs tab is selected
//      10. Check there is a list of available packages for Japan displayed below
//      11. Select the package for 1 GB
//      12. Click the Buy now button
//      13. Check the url has changed, and it contains both country name and the selected option
//      14. Check the main card has same Coverage, Data, Validity and Price as on the previously selected card
//      15. Scroll down
//      16. Check the price is the same as on card
//      17. Check buy button is present and clickable
//      18. Click buy button
//      19. Check user is redirected to login page
//      20. Check url
//      21. Check Login form is present

public class TestBuyPackage extends BaseTest {

    @Test(dataProvider = "countries")
    public void buyRandomPackage(String coverage, String data, String validity, Double price) {
        createTest("Airalo Search Test");
        logInfo("Starting Buy Random Package test");

        MainPage mainPage = new MainPage(DriverFactory.getDriver());

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

        var packageCard =  mainPage.getPackageCard();
        var packageCardDTO = mainPage.getPackageCardDTO(packageCard);

        assertThat(packageDTO.toString()).as("The packages data does not match")
                .isEqualTo(packageCardDTO.toString());

        assertThat(mainPage.getCurrentUrl()).as("The page URL doesn't contain country name")
                .contains(coverage.toLowerCase());

        packageCard.clickBuyPackage();

        var loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.isLoaded();

        assertThat(mainPage.getCurrentUrl()).as("The URL didn't change")
            .isEqualTo(config().loginUrl());
    }

    @DataProvider(name = "countries")
    private Object[][] countriesDataProvider() {
        return new Object[][]{{"Japan", "1 GB", "7 Days", 4.50}};
    }
}
