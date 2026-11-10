package za.co.ndosiautomation.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    // Confirm these selectors against the live site's DOM before execution.
    private final By usernameField = By.cssSelector("input[type='email'], input[name='username'], input[name='email']");
    private final By passwordField = By.cssSelector("input[type='password']");
    private final By loginButton = By.xpath("//button[normalize-space()='Login' or normalize-space()='Log in'] | //input[@type='submit' and (@value='Login' or @value='Log in')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open(String url) { driver.get(url); }

    public boolean isLoginFormDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        return driver.findElements(usernameField).stream().anyMatch(WebElement::isDisplayed)
            && driver.findElements(passwordField).stream().anyMatch(WebElement::isDisplayed);
    }

    public void login(String username, String password) {
        WebElement user = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        user.clear();
        user.sendKeys(username);
        WebElement pass = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        pass.clear();
        pass.sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }
}
