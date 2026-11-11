package za.co.ndosiautomation.pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasicFormPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public BasicFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    private By button(String text) {
        return By.xpath("//button[normalize-space()=" + xpathLiteral(text) + "] | //input[@type='submit' and @value=" + xpathLiteral(text) + "]");
    }
    private By label(String text) { return By.xpath("//label[normalize-space()=" + xpathLiteral(text) + "]"); }
    private WebElement field(String labelText) {
        WebElement labelElement = wait.until(ExpectedConditions.visibilityOfElementLocated(label(labelText)));
        String forAttribute = labelElement.getAttribute("for");
        if (forAttribute != null && !forAttribute.isBlank()) {
            List<WebElement> targets = driver.findElements(By.id(forAttribute));
            if (!targets.isEmpty()) return targets.get(0);
        }
        List<WebElement> nestedFields = labelElement.findElements(By.cssSelector("input, select, textarea"));
        if (!nestedFields.isEmpty()) return nestedFields.get(0);
        throw new AssertionError("No form control found for label: " + labelText);
    }
    public void openBasicForm() {
        By tab = By.xpath("//*[self::a or self::button or @role='tab'][normalize-space()='Web Automation Basic Form']");
        wait.until(ExpectedConditions.elementToBeClickable(tab)).click();
    }
    public void enterText(String labelText, String value) {
        WebElement element = field(labelText);
        element.clear();
        element.sendKeys(value);
    }
    public void selectOption(String labelText, String optionText) {
        new Select(field(labelText)).selectByVisibleText(optionText);
    }
    public void checkOption(String labelText) {
        WebElement element = field(labelText);
        if (!element.isSelected()) element.click();
    }
    public void selectCheckboxByLabel(String optionText) {
        WebElement optionLabel = wait.until(ExpectedConditions.elementToBeClickable(label(optionText)));
        String forAttribute = optionLabel.getAttribute("for");
        if (forAttribute != null && !forAttribute.isBlank()) {
            List<WebElement> targets = driver.findElements(By.id(forAttribute));
            if (!targets.isEmpty()) {
                if (!targets.get(0).isSelected()) optionLabel.click();
                return;
            }
        }
        optionLabel.click();
    }
    public void clickValidate() { wait.until(ExpectedConditions.elementToBeClickable(button("Validate"))).click(); }
    public void clickSubmitForm() { wait.until(ExpectedConditions.elementToBeClickable(button("Submit Form"))).click(); }
    public void openSubmissions() { wait.until(ExpectedConditions.elementToBeClickable(button("Submissions"))).click(); }
    public void viewSubmission() {
        By viewButton = By.xpath("//button[normalize-space()='View'] | //a[normalize-space()='View']");
        wait.until(ExpectedConditions.elementToBeClickable(viewButton)).click();
    }
    public boolean submissionContains(String expectedText) {
        By text = By.xpath("//*[contains(normalize-space(.), " + xpathLiteral(expectedText) + ")]");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(text)).isDisplayed();
    }
    private String xpathLiteral(String value) {
        if (!value.contains("'")) return "'" + value + "'";
        if (!value.contains("\"")) return "\"" + value + "\"";
        String[] parts = value.split("'", -1);
        StringBuilder result = new StringBuilder("concat(");
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) result.append(", \"'\", ");
            result.append("'").append(parts[i]).append("'");
        }
        return result.append(")").toString();
    }
}
