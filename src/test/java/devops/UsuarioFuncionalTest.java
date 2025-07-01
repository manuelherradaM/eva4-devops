package devops;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import org.openqa.selenium.*;


import spark.Spark;

class UsuarioFuncionalTest {

    private WebDriver driver;

    // ---------- Levantar y apagar el servidor ----------
    @BeforeAll
    static void startServer() throws InterruptedException {
        // Inicia Spark (web) en otro hilo
        new Thread(() -> App.main(null)).start();
        Thread.sleep(3000);           // espera a que escuche el puerto
    }

    @AfterAll
    static void stopServer() {
        Spark.stop();
    }

    // ---------- Preparar y cerrar navegador ----------
@BeforeEach
void setUp() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless=new");          // ejecuta sin ventana
    options.addArguments("--no-sandbox");            // necesario en CI Linux
    options.addArguments("--disable-dev-shm-usage"); // evita problemas de /dev/shm
    driver = new ChromeDriver(options);
}

    @AfterEach
    void tearDownDriver() {
        driver.quit();
    }

    // ---------- Prueba funcional ----------
    @Test
    void flujoCompletoActualizarPeso() {
        driver.get("http://localhost:4567/");

        driver.findElement(By.name("nombre")).sendKeys("Manuel");
        driver.findElement(By.name("peso")).sendKeys("90");
        driver.findElement(By.name("nuevoPeso")).sendKeys("80");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String pesoMostrado = driver.findElement(By.id("pesoActual")).getText();
        assertEquals("80.0", pesoMostrado);
    }
}
