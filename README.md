# Airalo UI Automation

This project is designed to automate end-to-end UI testing for the [Airalo](https://www.airalo.com/) website using Selenium WebDriver, TestNG, and a Page Object Model (POM) architecture. 
The test covers user journey (such as searching and trying to buy a sim package) across different browsers and modes (headless vs. regular).

---

## Table of Contents

- [Prerequisites](#prerequisites)
- [Project Setup](#project-setup)
- [Configuration](#configuration)
- [Running the Tests](#running-the-tests)
    - [Locally](#locally)
    - [Using CI/CD (GitHub Actions)](#using-cicd-github-actions)
- [Test Case Implementation Overview](#test-case-implementation-overview)
- [Project Structure](#project-structure)

---

## Prerequisites

Ensure you have the following installed:

- Java 17 
- Maven 3.8+ installed and in classpath
- [IDE with enabled Lombok](https://www.baeldung.com/lombok-ide)

---

## Project Setup

### Clone the repository:
<code>git clone https://github.com/Muntesu/airalo-ui-automation.git</code>

### Install Dependencies:
The project uses Maven to manage dependencies. In order to install them run: <code>mvn clean compile</code>

### Configure the browser:
Update browser property in the `system.properties` file located in `src/main/resources`. It can be set to `chrome`, `firefox`, `safari`, or `headless` as needed.

## Configuration

### System Properties:
The project uses a configuration file to load properties from `system.properties`. Ensure you have the correct URLs and browser settings defined.

### Driver Setup:
The `DriverFactory` class sets up your WebDriver with custom options. For headless mode, the Chrome options include:
<code>--headless=new</code>.
Additional flags optimize performance and reliability in CI environments.

### Event Listeners & Decorators:
The project uses a custom `WebDriverEventListener` wrapped around the driver to log events. 

---

## Running the Tests

### Locally
To run the tests locally, execute:
<code>mvn test -DsuiteXmlFile=src/test/resources/suites/buy_sims.xml</code>


This command will:
1. Compile the project.
2. Use TestNG to run the test suite defined in `buy_sims.xml`.
3. Use the configuration specified in `system.properties` (e.g., browser mode, URLs).

To run tests in a specific mode, update the `browser` value in `system.properties`.

### Using CI/CD (GitHub Actions)
The project is integrated with GitHub Actions for CI/CD. The workflow file is `.github/workflows/test.yaml`. Make sure to review and update the workflow file as needed for your environment.

Additionally, the workflow supports manual triggering, allowing you to run the tests on-demand directly from the GitHub Actions tab.

---

## Test Case Implementation Overview

### Approach

#### Page Object Model (POM):
Each page (e.g., HomePage, LoginPage, PackagePage) has a corresponding Java class encapsulating elements and actions needed for that page.


#### Test Execution Flow:
Tests navigate through several pages using Page Objects, validating data integrity and ensuring required elements are present before proceeding.

#### Report Generation

After the tests complete, the project generates detailed test reports and artifacts:

- **HTML Reports:**  
  An HTML report (e.g., `ExtentReport.html`) is generated and saved in the `test-output/` directory. This report includes a summary of the test run, logs, and test results.

- **Screenshots:**  
  Screenshots are captured automatically for failed tests and are saved in the `test-output/screenshots/` directory. These screenshots help diagnose issues, especially in headless mode.

---

## Project Structure
```
airalo-ui-automation/
├── .github/                      # CI/CD workflows (GitHub Actions)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── airalo/
│   │   │           ├── configuration/    # Loads properties from system.properties
│   │   │           ├── dto/              # Data transfer objects
│   │   │           ├── environment/      # Browser/environment enums
│   │   │           ├── logger/           # Logger manager for test execution
│   │   │           ├── page_object/      # Page Object Model classes
│   │   │           ├── util/             # Utility classes (waits, JS execution, etc.)
│   │   │           └── webdriver/        # Driver setup and event listeners
│   │   └── resources/
│   │       └── system.properties         # Global config (URLs, browser)
│
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── airalo/
│       │           ├── report/          # Report generation (e.g., ExtentReports hooks)
│       │           └── runner/          # TestNG test classes
│       └── resources/
│           └── suites/                  # TestNG suite XMLs (e.g., buy_sims.xml)
│
├── test-output/                 # Reports, screenshots, and execution artifacts
├── .gitignore                   # Git ignore rules
├── pom.xml                      # Maven project config
└── README.md                    # Project documentation

```


