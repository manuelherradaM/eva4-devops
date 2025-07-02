package devops;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioSeleniumTest {

    private WebDriver driver;

    @BeforeAll
    static void levantarServidor() throws InterruptedException {
        new Thread(() -> App.main(null)).start();
        Thread.sleep(3000);  // Espera que Spark escuche el puerto 8080
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterAll
    static void detenerServidor() {
        spark.Spark.stop();
    }

    @Test
    void deberiaMostrarInformacionUsuario() {
        driver.get("http://localhost:8080/");

        driver.findElement(By.name("nombre")).sendKeys("Manuel");
        driver.findElement(By.name("peso")).sendKeys("90");
        driver.findElement(By.name("nuevoPeso")).sendKeys("80");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Este contenido deberías generarlo en la respuesta HTML
        String pesoMostrado = driver.findElement(By.id("pesoActual")).getText();
        assertEquals("80.0", pesoMostrado);
    }
}
