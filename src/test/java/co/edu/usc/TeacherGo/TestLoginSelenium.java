package co.edu.usc.TeacherGo;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class TestLoginSelenium {

    @Test
    public void testInicioSesion() {

        String ciEnv = System.getenv("CI");

        // Solo ejecutar si **NO** estamos en un entorno de CI
        assumeTrue(ciEnv == null || ciEnv.isBlank(), "Test Selenium deshabilitado en entorno CI");


        // la ruta en mi equipo donde deje el driver de chrome para llamado de selenium
        System.setProperty("webdriver.chrome.driver", "C:\\SeleniumDriver\\chromedriver.exe");


        WebDriver driver = new ChromeDriver();

        try {
           // la ruta del puerto asignado a mi equipo  yla vista template de login
            driver.get("http://localhost:3308/login");

            WebElement campoCorreo = driver.findElement(By.name("correo"));
            WebElement campoContrasena = driver.findElement(By.name("contrasena"));
            WebElement botonIngresar = driver.findElement(By.xpath("//button[@type='submit']"));
           // este correo y contraseña ya se encuentra registrado en base de datos ideal para mi prueba
            campoCorreo.sendKeys("laura.torres@example.com");
            campoContrasena.sendKeys("laurapass");
            botonIngresar.click();

            //con el wait alcanzo a ver el redericionamiento al panel  si se concluye
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains("/panel-usuario"));
            String currentUrl = driver.getCurrentUrl();
            assertTrue(currentUrl.contains("/panel-usuario"), "No se redirigió correctamente al panel de usuario");

           //confirmo el funcionamiento
            System.out.println("La prueba selenium funciono, enviando a: " + currentUrl);
            //aqui estableco la duracion de tiempo para poder obsevar la ejecucion graficamente
            Thread.sleep(5000);

        } catch (Exception e) {
            System.err.println("Error durante la ejecución de la prueba:");
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
