package com.airalo.page_object;

import com.airalo.dto.PackageDTO;
import com.airalo.util.JsExecutor;
import com.airalo.util.WebWait;
import java.util.List;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Getter
@AllArgsConstructor
public class MainPage extends BasePage {

    private final WebDriver driver;
    private Search search;
    private NavigationMenu navigationMenu;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.search = new Search(driver);
        this.navigationMenu = new NavigationMenu(driver);
    }

    public void search(String searchItem) {
        Search countrySearch = getSearch();
        countrySearch.startCountriesSearch(searchItem)
            .finishCountriesSearch()
            .openFoundCountry(searchItem);
    }

    public Package getPackage(Predicate<Package> condition) {
        return getPackages()
            .stream()
            .filter(condition)
            .findFirst()
            .orElseThrow();
    }

    public List<Package> getPackages() {
        return driver.findElements(By.cssSelector("[data-testid=\"sim-package-item\"]"))
            .stream()
            .map(Package::new)
            .toList();
    }

    public PackageDTO getPackageDTO(Package aPackage) {
        return PackageDTO.builder().coverage(aPackage.getCoverage())
            .data(aPackage.getData()).validity(aPackage.getValidity())
            .price(aPackage.getPrice()).build();
    }

    public PackageCard getPackageCard() {
        var packageDetailSelector = By.cssSelector("[data-testid=\"package-detail\"]");
        WebWait.scrollAndWaitUntilElementVisible(packageDetailSelector);
        return new PackageCard(getDriver().findElement(packageDetailSelector));
    }

    public PackageDTO getPackageCardDTO(PackageCard aPackage) {
        return PackageDTO.builder().coverage(aPackage.getCoverage())
            .data(aPackage.getData()).validity(aPackage.getValidity())
            .price(aPackage.getPrice()).build();
    }

}
