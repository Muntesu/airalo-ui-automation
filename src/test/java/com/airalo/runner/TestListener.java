package com.airalo.runner;

import com.airalo.report.ExtentTestManager;
import com.airalo.webdriver.DriverFactory;
import com.aventstack.extentreports.ExtentTest;
import java.io.File;
import java.io.IOException;
import org.apache.maven.surefire.shared.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = DriverFactory.getDriver();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String screenshotPath =
            "test-output/screenshots/" + result.getName() + "_" + System.currentTimeMillis()
                + ".png";
        try {
            File dest = new File(screenshotPath);
            FileUtils.copyFile(screenshot, dest);

            ExtentTest test = ExtentTestManager.getTest();
            test.fail("Test failed. Screenshot below:")
                .addScreenCaptureFromPath("screenshots/" + dest.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}