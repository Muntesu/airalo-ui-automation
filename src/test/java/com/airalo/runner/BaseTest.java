package com.airalo.runner;

import static com.airalo.configuration.ConfigurationManager.config;
import static com.airalo.logger.LoggerManager.logInfo;
import static com.airalo.webdriver.DriverFactory.getDriver;
import static com.airalo.webdriver.DriverFactory.quitDriver;
import static com.airalo.webdriver.DriverFactory.setupDriver;

import com.airalo.report.ExtentTestManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

@Listeners({TestListener.class})
public class BaseTest {

    protected static ExtentReports extent;

    @BeforeSuite(alwaysRun = true)
    public void setupExtentReports() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(
            "test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        logInfo("Starting webdriver");
        setupDriver();
        getDriver().get(config().baseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        ExtentTest test = getTest();

        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("Test Failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test Skipped: " + result.getThrowable());
        }

        logInfo("Quitting web driver");
        quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void flushExtentReports() {
        if (extent != null) {
            extent.flush();
        }
    }

    protected void createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        ExtentTestManager.setTest(test);
    }

    protected ExtentTest getTest() {
        return ExtentTestManager.getTest();
    }

}
