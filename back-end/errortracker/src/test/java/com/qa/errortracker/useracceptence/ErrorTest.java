package com.qa.errortracker.useracceptence;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@Sql(scripts = { "classpath:errortracker-schema.sql", "classpath:errortracker-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD
)
@ActiveProfiles("test")
public class ErrorTest {
	private WebDriver driver;

	@BeforeEach
	public void setup() {
		System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().window().setSize(new Dimension(1366, 768));
	}

	@Test
	public void addError() throws InterruptedException {
		driver.get("http://localhost:8080/");
		
		WebElement element = driver.findElement(By.cssSelector("#addTodo"));
		
		element.click();
		
		element = driver.findElement(By.cssSelector("#error-name"));
		element.sendKeys("Oliver");
		
		element = driver.findElement(By.cssSelector("#shortErr"));
		element.sendKeys("This is a short error");
		
		element = driver.findElement(By.cssSelector("#stack"));
		element.sendKeys("This is the stack error");
		
		element = driver.findElement(By.cssSelector("input.btn-blue"));
		element.click();
		
		
		By byCard = By.xpath("/html/body/article/div/div[1]/div/button/div[2]");
		
		element = driver.findElement(byCard);
		new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOf(element));
		
		String txt = element.getText();
		assertEquals("-951002154-Oliver", txt);
	}

	@AfterEach
	public void tearDown() {
		driver.close();
	}
}
