package za.co.ndosiautomation.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import za.co.ndosiautomation.base.DriverFactory;
import za.co.ndosiautomation.pages.BasicFormPage;
import za.co.ndosiautomation.pages.DashboardPage;
import za.co.ndosiautomation.pages.LoginPage;

public class ValidLoginAndBasicFormTest {
    private static final String BASE_URL = System.getProperty("baseUrl", "https://ndosiautomation.co.za/");
    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private BasicFormPage basicFormPage;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.createDriver();
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        basicFormPage = new BasicFormPage(driver);
        loginPage.open(BASE_URL);
    }

    @Test(description = "Valid login, dashboard verification and basic form submission", groups = {"smoke", "regression", "end-to-end"})
    public void verifyValidLoginAndBasicFormSubmission() {
        String username = System.getenv("NDOSI_USERNAME");
        String password = System.getenv("NDOSI_PASSWORD");
        Assert.assertNotNull(username, "Set NDOSI_USERNAME environment variable.");
        Assert.assertFalse(username.isBlank(), "Username must not be blank.");
        Assert.assertNotNull(password, "Set NDOSI_PASSWORD environment variable.");
        Assert.assertFalse(password.isBlank(), "Password must not be blank.");

        Assert.assertTrue(loginPage.isLoginFormDisplayed(), "Login form should be visible.");
        loginPage.login(username, password);
        Assert.assertTrue(dashboardPage.isWelcomeMessageDisplayed(), "Welcome message should be visible after login.");
        Assert.assertFalse(dashboardPage.getCurrentUrl().toLowerCase().contains("login"), "Expected to leave login route after authentication.");
        Assert.assertFalse(dashboardPage.getWelcomeMessage().isBlank(), "Welcome message should not be blank.");
        Assert.assertTrue(dashboardPage.getAuthenticatedUsername().contains(username), "Dashboard should identify the authenticated test user; update selector to match site DOM.");

        basicFormPage.openBasicForm();
        basicFormPage.enterText("Full Name", "John Doe");
        basicFormPage.enterText("Email", "john@test.com");
        basicFormPage.enterText("Age", "25");
        basicFormPage.selectOption("Gender", "Male");
        basicFormPage.selectOption("Country", "South Africa");
        basicFormPage.selectOption("Experience", "Intermediate (2-5 years)");
        basicFormPage.selectCheckboxByLabel("JavaScript");
        basicFormPage.selectCheckboxByLabel("Selenium");
        basicFormPage.enterText("Comments", "Test automation practice");
        basicFormPage.checkOption("Subscribe to Newsletter");
        basicFormPage.checkOption("Accept Terms & Conditions");

        basicFormPage.clickValidate();
        // TODO: Assert the application's real validation success message.
        basicFormPage.clickSubmitForm();
        // TODO: Assert the application's real submission confirmation.
        basicFormPage.openSubmissions();
        basicFormPage.viewSubmission();

        Assert.assertTrue(basicFormPage.submissionContains("John Doe"), "Saved submission should contain Full Name.");
        Assert.assertTrue(basicFormPage.submissionContains("john@test.com"), "Saved submission should contain Email.");
        Assert.assertTrue(basicFormPage.submissionContains("South Africa"), "Saved submission should contain Country.");
        Assert.assertTrue(basicFormPage.submissionContains("JavaScript"), "Saved submission should contain JavaScript skill.");
        Assert.assertTrue(basicFormPage.submissionContains("Selenium"), "Saved submission should contain Selenium skill.");
        Assert.assertTrue(basicFormPage.submissionContains("Test automation practice"), "Saved submission should contain Comments.");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
