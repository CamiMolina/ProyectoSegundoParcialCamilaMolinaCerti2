import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PruebaCarritoConVarios {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testAgregarVariosElementosAlCarrito() {
        driver.get("https://www.saucedemo.com/");
        WebElement userNameTextBox = driver.findElement(By.id("user-name"));
        userNameTextBox.sendKeys("standard_user");

        WebElement passwordTextBox = driver.findElement(By.id("password"));
        passwordTextBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();
        agregarElementoAlCarrito("add-to-cart-sauce-labs-fleece-jacket");
        agregarElementoAlCarrito("add-to-cart-test.allthethings()-t-shirt-(red)");

        boolean elementosAgregados = verificarElementosAgregadosAlCarrito();

        assertTrue(elementosAgregados, "No se han agregado elementos al carrito.");
    }

    private void agregarElementoAlCarrito(String botonId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id(botonId)));
        addToCartButton.click();
    }

    private boolean verificarElementosAgregadosAlCarrito() {
        WebElement carrito = driver.findElement(By.className("shopping_cart_badge"));
        int cantidadElementosCarrito = Integer.parseInt(carrito.getText());

        return cantidadElementosCarrito >= 2;
    }
}
