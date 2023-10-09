package es.urjc.tfm.scheduly.controllers;

import es.urjc.tfm.scheduly.SchedulyApplication;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import io.github.bonigarcia.wdm.WebDriverManager;

@TestPropertySource(properties = "listener.enabled=false")
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
			String messageBody ="selenium test texto";
			// schedule message view
			goToScheduleMessageViewFillsAndClickSchedule(messageBody,"9","9","2055","9","19");
			// scheduled message view
			driver.findElement(By.id("back")).click();
			// index
			WebElement lastElem = getRecentlyScheduledMessage();
			String lastElemText = lastElem.getText().split(",")[0];
			// verifies if the message has been scheduled
			assertEquals(messageBody, lastElemText);
			String id = lastElem.getAttribute("id");
			lastElem.click();
			// show message view
			WebElement messageBodyCreatedMessage = driver.findElement(By.id("messageBody"));
			assertEquals(lastElemText, messageBodyCreatedMessage.getText());
			deleteAnElementAndVerifyInShowMessageView(id);
	   }
	
	@Test
    public void scheduleMessageOutOfDateTest() {
		driver.get("https://scheduly-molynx.cloud.okteto.net/");
		
		// index view
		driver.findElement(By.id("scheduleButton")).click();
		String messageBody ="selenium test text for out of date";
		// schedule message view
		goToScheduleMessageViewFillsAndClickSchedule(messageBody,"9","9","1200","9","19");
        // error view
        driver.findElement(By.id("back")).click();

		// index view
		driver.findElement(By.id("scheduleButton")).click();
		// schedule message view
        goToScheduleMessageViewFillsAndClickSchedule(messageBody,"9","9","1200","9","19");
		// error view
		driver.findElement(By.id("scheduleButton")).click();
		goToScheduleMessageViewFillsAndClickSchedule(messageBody,"9","9","2200","9","19");
        // scheduled view
		driver.findElement(By.id("back")).click();
		// index view
        WebElement lastElem = getRecentlyScheduledMessage();
        String lastElemText = lastElem.getText().split(",")[0];
        
		// verifies if the message has been scheduled
        assertEquals(messageBody, lastElemText);
		String id = lastElem.getAttribute("id");
		lastElem.click();
		// show message view
		WebElement messageBodyCreatedMessage = driver.findElement(By.id("messageBody"));
    	assertEquals(lastElemText, messageBodyCreatedMessage.getText());
		deleteAnElementAndVerifyInShowMessageView(id);

    	
	}

	private void goToScheduleMessageViewFillsAndClickSchedule(String messageBody, String day, String month, String year, String hour, String minute){
        driver.findElement(By.id("messageBody")).sendKeys(messageBody);
        driver.findElement(By.id("day")).sendKeys(day);
        driver.findElement(By.id("month")).sendKeys(month);
        driver.findElement(By.id("year")).sendKeys(year);
        driver.findElement(By.id("hour")).sendKeys(hour);
        driver.findElement(By.id("minute")).sendKeys(minute);
        driver.findElement(By.id("schedule")).click();
	}

	private WebElement getRecentlyScheduledMessage(){
		WebElement div = driver.findElement(By.className("message-info"));
        java.util.List<WebElement> elements = div.findElements(By.tagName("div"));
        return elements.get(elements.size() - 1);
	}

	private void deleteAnElementAndVerifyInShowMessageView(String id) {
		driver.findElement(By.id("deleteButton")).click();
		// delete view
    	driver.findElement(By.id("deleteButton")).click();
    	// deleted view
		driver.findElement(By.id("back")).click();
		// index view
		try {
		driver.findElement(By.id(id));
		} catch (NoSuchElementException e) {
			System.out.println("Message " + id + " has been deleted properly");
		}
	}
}
