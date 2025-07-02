package devops;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioSeleniumTest {

    private static final String BASE_URL = "http://localhost:8080/";
    private static WebDriver driver;

    /* ---------- Levanta Spark una sola vez ---------- */
    @BeforeAll
    static void levantarServidor() throws InterruptedException {
        new Thread(() -> App.main(null)).start();
        Thread.sleep(3_000); // espera 3 s a que Spark abra el puerto
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(opt);
    }

    /* ---------- Apaga Spark y cierra navegador ---------- */
    @AfterAll
    static void detenerTodo() {
        if (driver != null) {
            driver.quit();
        }
        spark.Spark.stop();
    }

    /* ---------- Flujo completo ---------- */
    @Test
    @Order(1)
    void deberiaMostrarInformacionUsuario() {
        driver.get(BASE_URL);

        driver.findElement(By.name("nombre")).sendKeys("Manuel");
        driver.findElement(By.name("peso")).sendKeys("90");
        driver.findElement(By.name("nuevoPeso")).sendKeys("80");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Valida nombre (primer <p>)
        String nombreTexto = driver.findElements(By.tagName("p")).get(0).getText();
        assertTrue(nombreTexto.startsWith("Nombre: Manuel"));

        // Valida peso actualizado
        String pesoTexto = driver.findElement(By.name("pesoActual")).getText();
        assertEquals("80.0", pesoTexto);
    }
}
