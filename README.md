# Ndosi Automation QA — Selenium WebDriver

Java 17 + Selenium WebDriver + TestNG + Maven + Page Object Model (POM).

## Scope

- Open `https://ndosiautomation.co.za/` and verify the login form is visible.
- Authenticate using environment variables (credentials are not stored in source control).
- Verify the dashboard Welcome message and authenticated username indicator.
- Open **Web Automation Basic Form**, fill the requested data, validate, submit, open Submissions, click View, and assert saved values.

## Project structure

```text
Ndosi_Automations/
├── pom.xml
├── testng.xml
├── .gitignore
├── README.md
└── src
    ├── main/java/za/co/ndosiautomation/
    │   ├── base/DriverFactory.java
    │   └── pages/
    │       ├── LoginPage.java
    │       ├── DashboardPage.java
    │       └── BasicFormPage.java
    └── test/java/za/co/ndosiautomation/tests/
        └── ValidLoginAndBasicFormTest.java
```

## Prerequisites

- JDK 17
- Maven 3.8+
- Google Chrome
- Access to the website and a dedicated valid QA test account

Selenium Manager can provision a supported ChromeDriver automatically.

## Run locally (Windows PowerShell)

```powershell
$env:NDOSI_USERNAME = Read-Host "Enter test username"
$securePassword = Read-Host "Enter test password" -AsSecureString
$env:NDOSI_PASSWORD = [System.Net.NetworkCredential]::new("", $securePassword).Password
mvn clean test
```

To run headless:

```powershell
mvn -Dheadless=true clean test
```

Reports: `target/surefire-reports/`.

## Before QA sign-off

The site DOM and success messages have not been verified from this package. Inspect the live application and update the example locators in `LoginPage`, `DashboardPage`, and `BasicFormPage`. Add assertions for the actual validation-success and submission-confirmation messages. The username indicator selector is illustrative. Password correctness is verified by successful authentication, not by exposing the password on the page.

## Publish to GitHub

The target repository is `https://github.com/IshmaelDi/Ndosi_Automations.git`.

If the repository already contains commits, clone it first and copy these project files into that clone so existing files/history are preserved. Then:

```bash
git add .
git commit -m "Add Selenium login and basic form automation"
git push origin main
```

If starting from this extracted package and the remote repository is empty:

```bash
git init
git branch -M main
git remote add origin https://github.com/IshmaelDi/Ndosi_Automations.git
git add .
git commit -m "Add Selenium login and basic form automation"
git push -u origin main
```

Do not commit passwords, access tokens, browser profiles, or generated reports. Use GitHub Secrets for CI credentials.
