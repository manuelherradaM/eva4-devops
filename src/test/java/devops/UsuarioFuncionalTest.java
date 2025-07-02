package devops;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioFuncionalTest {

    private static WebDriver driver;
    private static final String BASE_URL = "http://localhost:8080/";

    /* ---------- Levanta Spark ---------- */
    @BeforeAll
    static void iniciarServidor() throws InterruptedException {
        new Thread(() -> App.main(null)).start();
        Thread.sleep(3_000);
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

        String pesoMostrado = driver.findElement(By.id("pesoActual")).getText();
        assertEquals("80.0", pesoMostrado);
    }
}
