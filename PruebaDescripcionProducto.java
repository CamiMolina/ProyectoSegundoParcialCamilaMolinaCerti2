import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

public class PruebaDescripcionProducto {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testVerificarDescripcionDelProducto() {
        try {
            driver.get("https://www.saucedemo.com/");

            WebElement userNameTextBox = driver.findElement(By.id("user-name"));
            userNameTextBox.sendKeys("standard_user");

            WebElement passwordTextBox = driver.findElement(By.id("password"));
            passwordTextBox.sendKeys("secret_sauce");

            WebElement loginButton = driver.findElement(By.id("login-button"));
            loginButton.click();

            WebElement producto = driver.findElement(By.id("item_4_title_link"));
            producto.click();

            WebElement descripcionProducto = driver.findElement(By.cssSelector(".inventory_details_desc.large_size"));

            assertTrue(descripcionProducto.isDisplayed(), "La descripción del producto se muestra en la página.");
            System.out.println("Descripción del producto: " + descripcionProducto.getText());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Prueba fallida: Ocurrió una excepción durante la ejecución de la prueba.");
        }
    }
}
