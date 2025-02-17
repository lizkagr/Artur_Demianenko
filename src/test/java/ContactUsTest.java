import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class ContactUsTest {
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
            messageField.sendKeys("Hello! I have a question regarding my order.");

            WebElement submitButton = driver.findElement(By.name("submitMessage"));


            if (submitButton.isDisplayed()) {
                System.out.println("Test Passed: Confirmation message is displayed.");
                submitButton.click();
            } else {
                System.out.println("Test Failed: Confirmation message is not displayed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test Failed: An error occurred.");
        } finally {
            driver.quit();
        }
    }
}
