package es.urjc.tfm.scheduly.controllers;

import es.urjc.tfm.scheduly.SchedulyApplication;

import static java.lang.String.format;
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
    public void scheduleMessageGetMessageTest() {
		driver.get("https://scheduly-molynx.cloud.okteto.net/");
		
		// index view
		driver.findElement(By.id("scheduleButton")).click();
		
		// schedule message view
        driver.findElement(By.id("messageBody")).sendKeys("selenium test text");
        driver.findElement(By.id("day")).sendKeys("9");
        driver.findElement(By.id("month")).sendKeys("9");
        driver.findElement(By.id("year")).sendKeys("2099");
        driver.findElement(By.id("hour")).sendKeys("9");
        driver.findElement(By.id("minute")).sendKeys("19");
        driver.findElement(By.id("schedule")).click();
        
        // scheduled message view
        driver.findElement(By.id("back")).click();
        WebElement div = driver.findElement(By.className("message-info"));
        java.util.List<WebElement> elements = div.findElements(By.tagName("div"));
        WebElement lastElem = elements.get(elements.size() - 2);
        String lastElemText = lastElem.getText().split(",")[0];
        
		// verifies if the message has been scheduled
        assertEquals("selenium test text", lastElemText);
        
		lastElem.click();
		// show message view
		WebElement messageTextArea = findElementWithText("Body: selenium test text");
    	assertEquals(lastElemText, messageTextArea.getText().split(": ")[1]);
    	
	}
	
	private WebElement findElementWithText(String text) {
        return driver.findElement(By.xpath(format("//*[text()='%s']", text)));
	}
	
}
