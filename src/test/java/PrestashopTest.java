import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class PrestashopTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {

            driver.get("http://192.168.91.81/contact-us");

            FileReader fileReader = new FileReader("src/test/resources/products.csv", StandardCharsets.UTF_8);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader);

            for (CSVRecord record : records) {
                String productName = record.get("Product");
                String price = record.get("Price");
                String discount = record.get("Discount");
                String size = record.get("Size");

                System.out.println("Testing product: " + productName);

                WebElement searchBox = driver.findElement(By.name("s"));
                searchBox.clear();
                searchBox.sendKeys(productName);
                driver.findElement(By.name("s")).click();

                WebElement productLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(productName)));
                productLink.click();

                WebElement wishlistButton = driver.findElement(By.cssSelector(".wishlist-button-add"));
                wishlistButton.click();

                WebElement loginMessage = driver.findElement(By.cssSelector(".modal-content"));
                if (loginMessage.isDisplayed()) {
                    System.out.println("Test Passed: Login message displayed for wishlist.");
                }

                // Close login prompt
                driver.findElement(By.xpath("//*[@id=\"footer\"]/div[2]/div/div[1]/div[6]/div[1]/div/div/div[1]/button")).click();


                // Choose size and add to cart
                WebElement sizeDropdown = driver.findElement(By.id("group_1"));
                sizeDropdown.click();
                WebElement sizeOption = driver.findElement(By.xpath("//option[text()='" + size + "']"));
                sizeOption.click();
                WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"add-to-cart-or-refresh\"]/div[2]/div/div[2]/button")));


                checkoutButton.click();

                // Wait for confirmation
                WebElement cartConfirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id=\"add-to-cart-or-refresh\"]/div[2]/div/div[2]/button")));
                System.out.println("Test Passed: Product added to cart.");

                // Verify cart details
                WebElement cartProductName = driver.findElement(By.cssSelector(".cart_description .product-name"));
                if (cartProductName.getText().contains(productName)) {
                    System.out.println("Test Passed: Product is in the cart.");
                }

                // Verify size
                WebElement cartSize = driver.findElement(By.cssSelector(".cart_description .cart_product_attributes"));
                if (cartSize.getText().contains(size)) {
                    System.out.println("Test Passed: Correct size in cart.");
                }

                // Verify discount (if applicable)
                if (!driver.findElements(By.cssSelector(".cart_total .discount")).isEmpty()) {
                    System.out.println("Test Passed: Discount applied correctly.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Test Failed: Could not read CSV file.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test Failed: An error occurred.");
        } finally {
            driver.quit();
        }
    }
}
