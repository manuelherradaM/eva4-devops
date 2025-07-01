package devops;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioSeleniumTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Asegúrate de tener el ChromeDriver instalado y en el PATH
        System.setProperty("webdriver.chrome.driver", "C:/WebDriver/bin/chromedriver.exe"); // Ajusta esta ruta según tu sistema
        driver = new ChromeDriver();
    }

    @Test
    public void deberiaMostrarInformacionUsuario() {
        driver.get("http://localhost:8080");

        WebElement nombre = driver.findElement(By.id("nombre"));
        WebElement peso = driver.findElement(By.id("peso"));

        assertEquals("Nombre: Manuel", nombre.getText());
        assertEquals("Peso: 81.0", peso.getText());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
