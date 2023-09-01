import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PruebaPermanenciaCarrito {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testProductosPermanecenEnCarrito() {
        try {
            driver.get("https://www.saucedemo.com/");
            WebElement userNameTextBox = driver.findElement(By.id("user-name"));
            userNameTextBox.sendKeys("standard_user");

            WebElement passwordTextBox = driver.findElement(By.id("password"));
            passwordTextBox.sendKeys("secret_sauce");

            WebElement loginButton = driver.findElement(By.id("login-button"));
            loginButton.click();

            By producto1Locator = By.xpath("//div[@class='inventory_item_name' and contains(text(),'Sauce Labs Backpack')]");
            By producto2Locator = By.xpath("//div[@class='inventory_item_name' and contains(text(),'Sauce Labs Bike Light')]");

            WebElement producto1 = waitForElementToBeVisible(producto1Locator);
            WebElement producto2 = waitForElementToBeVisible(producto2Locator);

            producto1.click();
            producto2.click();

            WebElement menuButton = driver.findElement(By.id("react-burger-menu-btn"));
            menuButton.click();

            WebElement logoutLink = driver.findElement(By.id("logout_sidebar_link"));
            logoutLink.click();

            userNameTextBox = driver.findElement(By.id("user-name"));
            userNameTextBox.sendKeys("standard_user");

            passwordTextBox = driver.findElement(By.id("password"));
            passwordTextBox.sendKeys("secret_sauce");

            loginButton = driver.findElement(By.id("login-button"));
            loginButton.click();

            WebElement carritoLink = driver.findElement(By.id("shopping_cart_container"));
            carritoLink.click();

            WebElement productoEnCarrito1 = driver.findElement(By.xpath("//div[contains(@class,'inventory_item_name') and text()='Sauce Labs Backpack']"));
            WebElement productoEnCarrito2 = driver.findElement(By.xpath("//div[contains(@class,'inventory_item_name') and text()='Sauce Labs Bike Light']"));

            assertTrue(productoEnCarrito1.isDisplayed(), "El producto 'Sauce Labs Backpack' no se encontró en el carrito después de cerrar y abrir la sesión.");
            assertTrue(productoEnCarrito2.isDisplayed(), "El producto 'Sauce Labs Bike Light' no se encontró en el carrito después de cerrar y abrir la sesión.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private WebElement waitForElementToBeVisible(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
