package es.urjc.tfm.scheduly.controllers;

import es.urjc.tfm.scheduly.SchedulyApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(classes = SchedulyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageWebControllerE2ETest {

	@LocalServerPort
    int port = 8081;

	 WebDriver driver;

	    @BeforeAll
	    static void setupAll() {
	        WebDriverManager.chromedriver().setup();

	    }

	    @BeforeEach
	    void setup() {
	        ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--headless");
		options.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(options);
	    }

	    @AfterEach
	    void teardown() {
	        driver.quit();
	    }
	
	@Test
    public void scheduleMessageAndGetAllMessageTest() {
		driver.get("https://scheduly-molynx.cloud.okteto.net/");
		driver.findElement(By.id("scheduleButton")).click();
        driver.findElement(By.id("messageBody")).sendKeys("texto de prueba nuevas");
        driver.findElement(By.id("day")).sendKeys("9");
        driver.findElement(By.id("month")).sendKeys("9");
        driver.findElement(By.id("year")).sendKeys("2099");
        driver.findElement(By.id("hour")).sendKeys("9");
        driver.findElement(By.id("minute")).sendKeys("19");
        driver.findElement(By.id("schedule")).click();
        driver.findElement(By.id("back")).click();
        WebElement div = driver.findElement(By.tagName("body"));
        java.util.List<WebElement> elements = div.findElements(By.tagName("a"));
        WebElement lastElem = elements.get(elements.size() - 2);

        assertEquals("texto de prueba nuevas", lastElem.getText().split(",")[0]);
        
    }
}
