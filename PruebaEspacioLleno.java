import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class PruebaEspacioLleno {

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
    public void testAgregarProductoAlCarritoYVerificarCamposObligatorios() {
        try {
            driver.get("https://www.saucedemo.com/");
            WebElement userNameTextBox = driver.findElement(By.id("user-name"));
            userNameTextBox.sendKeys("standard_user");

            WebElement passwordTextBox = driver.findElement(By.id("password"));
            passwordTextBox.sendKeys("secret_sauce");

            WebElement loginButton = driver.findElement(By.id("login-button"));
            loginButton.click();

            WebElement producto = driver.findElement(By.cssSelector(".inventory_item_name"));
            producto.click();

            WebElement agregarAlCarritoBtn = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
            agregarAlCarritoBtn.click();

            WebElement carritoBtn = driver.findElement(By.id("shopping_cart_container"));
            carritoBtn.click();

            WebElement checkoutButton = driver.findElement(By.cssSelector(".checkout_button"));
            checkoutButton.click();

            WebElement nombreInput = driver.findElement(By.id("first-name"));
            nombreInput.sendKeys("Tu Nombre");

            WebElement apellidoInput = driver.findElement(By.id("last-name"));
            apellidoInput.sendKeys("Tu Apellido");

            WebElement codigoPostalInput = driver.findElement(By.id("postal-code"));
            codigoPostalInput.sendKeys("Código Postal");

            WebElement continueButton = driver.findElement(By.cssSelector(".submit-button"));
            continueButton.click();

            WebElement mensajeError = driver.findElement(By.cssSelector(".error-message"));
            if (mensajeError.getText().contains("Por favor, complete todos los campos obligatorios.")) {
                System.out.println("Prueba exitosa: Se verificó que todos los campos obligatorios estén llenados.");
            } else {
                System.out.println("Prueba fallida: No se llenaron todos los campos obligatorios.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
