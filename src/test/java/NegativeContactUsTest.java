import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NegativeContactUsTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        try {

            driver.get("http://192.168.91.81/contact-us");

            WebElement topicDropdown = driver.findElement(By.id("id_contact"));
            topicDropdown.sendKeys("Customer Service");

            WebElement emailField = driver.findElement(By.id("email"));
            emailField.sendKeys("test@example.com");

            WebElement fileInput = driver.findElement(By.id("file-upload"));
            fileInput.sendKeys("C:/Users/D2PT/Downloads/forproject.png");

            WebElement messageField = driver.findElement(By.name("message"));
            messageField.clear();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("submitMessage")));

            if (submitButton.isDisplayed()) {
                submitButton.click();  // Click the submit button after confirming visibility
            } else {
                System.out.println("Test Failed: Submit button is not displayed.");
            }

            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-danger")));

            if (errorMessage.isDisplayed()) {
                System.out.println("Test Passed: Error message is displayed.");
            } else {
                System.out.println("Test Failed: Error message is not displayed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test Failed: An error occurred.");
        } finally {
            driver.quit();
        }
    }
}
