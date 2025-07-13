# 🧪 Veeva Multi-Product Automation Framework

This is a modular test automation framework built using **Java, Selenium, TestNG, Cucumber**, and **Allure Reporting**  
to automate web flows for Veeva's Core and Derived Products.

![Java](https://img.shields.io/badge/Java-11-blue)
![Selenium](https://img.shields.io/badge/Selenium-Framework-success)
![Allure](https://img.shields.io/badge/Allure-Reporting-orange)

---

## 📁 Project Structure

```
veeva-multi-product-automation/
│
├── automation-framework/         # Common reusable framework code
├── core-product-tests/           # Tests for core product
├── derived-product1-tests/       # Tests for derived product 1
├── derived-product2-tests/       # Tests for derived product 2
├── pom.xml                       # Parent POM (manages dependencies and plugins)
```

---

## ✅ Tech Stack

| Tool          | Purpose                          |
|---------------|----------------------------------|
| Java 11       | Programming language             |
| Selenium      | UI automation                    |
| TestNG        | Test execution engine            |
| Cucumber      | BDD and Gherkin feature support  |
| Maven         | Build and dependency management  |
| Allure        | Test reporting                   |
| Log4j         | Logging                          |

---

## 🧱 Modular Architecture (Multi-Module Maven)

- `automation-framework`: Shared reusable code (driver factory, hooks, base classes, config readers, utilities)
- `core-product-tests`: Feature files, step defs, and pages specific to the **NBA Warriors** site
- `derived-product1-tests`: Placeholder for NBA Sixers automation
- `derived-product2-tests`: Placeholder for NBA Bulls automation

---

## ✅ Test Cases Automated (Core Product)

1. **Men’s Jackets Extraction**
  - Navigate to Shop → Men’s
  - Scrape all Jacket data: **title**, **price**, and **Top Seller** tag
  - Store in a `.txt` file and attach to Allure report

2. **New & Features Video Feed Count**
  - Hover on menu and go to "New & Features"
  - Count total video feeds
  - Count how many are marked as **≥ 3d**

---

## ⚙️ Configuration

A `config.properties` file is used to parameterize test settings. Place it in:
```
automation-framework/src/main/resources/config.properties
```

Example:
```properties
browser=chrome
headless=false
incognito=false
```

---


## 📌 How It Works

- **Driver Management**: Thread-safe `DriverManager`.
- **Config Management**: `ConfigReader` loads `config.properties`.
- **BasePage**: Common Selenium page utilities.
- **Hooks**: Cucumber hooks for setup, teardown, screenshots.
- **Step Definitions**: Defined in `core-product-tests` and use `automation-framework` base classes.

## ⚙️ Prerequisites

- Java 11+
- Maven 3.6+
- Chrome browser
- Allure CLI (for generating reports):  
  👉 [Install Allure CLI](https://docs.qameta.io/allure/#_installing_a_commandline)

---

## 🚀 How to Run Tests

```bash
# Navigate to the test module (e.g., core-product-tests)
cd core-product-tests

# Run runner class
mvn clean test
```

---

## 📊 Generate Allure Report

```bash
# After tests have run, generate HTML report:
Action: mvn allure:report
# Open the report in browser
mvn allure:serve
```

> ⚠️ Report will be empty unless test scenarios actually ran and generated data in `allure-results`.

---

## 🧾 Feature File Example

```gherkin
Feature: Core Product Tests - NBA Warriors Site

  @Jackets
  Scenario: Extract all Men’s Jackets details from the Shop
    Given I am on the Core Product Home Page
    When I navigate to the Shop Menu and select Men's section
    And I scrape all Jacket items across all pages
    Then I store Jacket price, title, and Top Seller message in a text file
```

---

## 🧩 Tips

- Attachments (like `.txt` files) are linked in Allure report under **Attachments**.
- If using tags like `@Smoke`, uncomment the `tags = "@Smoke"` section in the `TestRunner`.

---

## 📂 Output Paths

| Path                              | Description                    |
|-----------------------------------|--------------------------------|
| `target/allure-results/`          | Raw Allure test data           |
| `target/allure-report/index.html` | Generated Allure report        |
| `test-output/jackets.txt`         | Example scraped output         |

---
## 🧩 Tips

- Text and CSV files are auto-attached to Allure reports using Hooks
- Page Object Model is used for all locators and actions
- No static waits used — explicit waits are handled via `WaitUtils`

---

## 📃 Author

UdayKumar Yakala — Automation QA Engineer