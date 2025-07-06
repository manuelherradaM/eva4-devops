package devops;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioFuncionalTest {

    private static WebDriver driver;
    private static final String BASE_URL = "http://localhost:8080/";

    /* ---------- Levanta Spark ---------- */
    @BeforeAll
    static void iniciarServidor() throws InterruptedException {
        new Thread(() -> App.main(null)).start();
        Thread.sleep(3_000);                    // espera a que Spark levante
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(opt);
    }

    /* ---------- Cierra ---------- */
    @AfterAll
    static void apagarServidor() {
        if (driver != null) driver.quit();
        spark.Spark.stop();
    }

    /* ---------- Escenario completo ---------- */
    @Test
    @Order(1)
    void flujoCompletoActualizarPeso() {
        driver.get(BASE_URL);

        driver.findElement(By.name("nombre")).sendKeys("Manuel");
        driver.findElement(By.name("peso")).sendKeys("90");
        driver.findElement(By.name("nuevoPeso")).sendKeys("80");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Espera explícita: aguarda hasta que el elemento sea visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement pesoMostradoElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("pesoActual"))
        );

        String pesoMostrado = pesoMostradoElement.getText();
        assertEquals("80.0", pesoMostrado, "El peso mostrado no coincide con el esperado");
    }
}
