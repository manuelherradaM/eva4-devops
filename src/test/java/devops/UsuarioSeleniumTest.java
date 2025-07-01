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
void setUp() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless=new");          // ejecuta sin ventana
    options.addArguments("--no-sandbox");            // necesario en CI Linux
    options.addArguments("--disable-dev-shm-usage"); // evita problemas de /dev/shm
    driver = new ChromeDriver(options);
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
