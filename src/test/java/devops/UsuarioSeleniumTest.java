package devops;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioSeleniumTest {

    private static Process server;
    private WebDriver driver;

    // 1) Levantar Spark antes de todos los tests
    @BeforeAll
    static void startServer() throws Exception {
        server = new ProcessBuilder("java", "-cp", "target/classes;target/test-classes", "devops.App").start();
        Thread.sleep(3000);          // espera 3 s a que Spark escuche
    }

    // 2) Apagar Spark al final
    @AfterAll
    static void stopServer() {
        server.destroy();
    }

    // 3) Crear navegador headless
    @BeforeEach
    void setUp() {
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(opt);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    // 4) Flujo completo
    @Test
    void deberiaMostrarInformacionUsuario() {
        driver.get("http://localhost:8080/");

        driver.findElement(By.name("nombre")).sendKeys("Manuel");
        driver.findElement(By.name("peso")).sendKeys("90");
        driver.findElement(By.name("nuevoPeso")).sendKeys("80");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // En la página de resultado validamos nombre y peso
        assertEquals("Nombre: Manuel",
                     driver.findElement(By.id("nombre")).getText());
        assertEquals("Peso: 80.0",
                     driver.findElement(By.id("peso")).getText());
    }
}
