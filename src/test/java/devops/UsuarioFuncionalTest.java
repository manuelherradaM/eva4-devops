package devops;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

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
    void setUpDriver() {
        driver = new ChromeDriver();  // requiere chromedriver en PATH
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
