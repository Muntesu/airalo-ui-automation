package com.airalo.webdriver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class DriverManager {

	private static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>() {
		@SneakyThrows
		@Override
		public void remove() {
			WebDriver driver = get();
			if (driver != null) {
				driver.quit();
			}
			super.remove();
		}
	};

	protected static WebDriver getDriver() {
		return webDriver.get();
	}

	protected static void setDriver(WebDriver driver) {
		webDriver.set(driver);
	}

	protected static void quitDriver() {
		webDriver.remove();
	}

}
