package za.co.ndosiautomation.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    // Replace with the app's stable welcome heading and signed-in user selectors if available.
    private final By welcomeMessage = By.xpath("//*[self::h1 or self::h2 or self::h3 or self::p or self::span][contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'welcome')]");
    private final By usernameIndicator = By.cssSelector("[data-testid='current-username'], [data-testid='user-email'], .current-username");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public boolean isWelcomeMessageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessage)).isDisplayed();
    }
    public String getWelcomeMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessage)).getText().trim();
    }
    public String getCurrentUrl() { return driver.getCurrentUrl(); }
    public String getAuthenticatedUsername() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameIndicator)).getText().trim();
    }
}
