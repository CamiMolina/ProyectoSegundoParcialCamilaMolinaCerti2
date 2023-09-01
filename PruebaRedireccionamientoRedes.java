import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PruebaRedireccionamientoRedes {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    public void login() {
        driver.get("https://www.saucedemo.com/");
        WebElement userNameTextBox = driver.findElement(By.id("user-name"));
        userNameTextBox.sendKeys("standard_user");

        WebElement passwordTextBox = driver.findElement(By.id("password"));
        passwordTextBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();
    }

    private void verificarRedireccionamiento(String botonClase, String urlEsperada) {
        try {
            WebElement botonRedireccionamiento = driver.findElement(By.className(botonClase));
            botonRedireccionamiento.click();

            cambiarAVentana(urlEsperada);

            verificarURL(urlEsperada);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Prueba fallida: Ocurrió una excepción durante la ejecución de la prueba.");
        }
    }

    private void cambiarAVentana(String urlEsperada) {
        String ventanaPrincipal = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(ventanaPrincipal)) {
                driver.switchTo().window(handle);
                // Verificar que la URL actual coincide con la URL esperada.
                if (driver.getCurrentUrl().equals(urlEsperada)) {
                    return;
                }
            }
        }
        fail("La redirección no ocurrió o no es la esperada.");
    }

    private void verificarURL(String urlEsperada) {
        String urlActual = driver.getCurrentUrl();
        assertEquals(urlEsperada, urlActual, "La redirección no es la esperada.");
    }

    @Test
    public void testRedireccionamientoAFacebook() {
        verificarRedireccionamiento("social_facebook", "https://www.facebook.com/saucelabs");
    }

    @Test
    public void testRedireccionamientoALinkedIn() {
        verificarRedireccionamiento("social_linkedin", "https://www.linkedin.com/company/sauce-labs/");
    }

    @Test
    public void testRedireccionamientoATwitter() {
        verificarRedireccionamiento("social_twitter", "https://twitter.com/saucelabs");
    }
}
