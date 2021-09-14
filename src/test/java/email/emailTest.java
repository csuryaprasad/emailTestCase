package email;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class emailTest {

	 private WebDriver driver;
	 Logger log =  	Logger.getLogger("emailTest");
	 
	 @BeforeClass
	    public void setUp()
	    {
	        WebDriverManager.chromedriver().setup();
	        ChromeOptions options = new ChromeOptions();
	        options.addArguments("--no-sandbox");
	        options.addArguments("--disable-dev-shm-usage");
	        options.addArguments("--headless");
	        driver = new ChromeDriver(options);
	        driver.navigate().to("https://the-internet.herokuapp.com/login");
	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(120, TimeUnit.MILLISECONDS);
	    }
	 
	 @Test
	    public void userLogin()
	    {
	        WebElement usernameTxt = driver.findElement(By.id("username"));
	        usernameTxt.sendKeys("tomsmith");
	        WebElement passwordTxt = driver.findElement(By.id("password1"));
	        passwordTxt.sendKeys("SuperSecretPassword!");
	        WebElement submitBtn = driver.findElement(By.className("radius"));
	        submitBtn.click();
	        log.info("Current URL is:" + driver.getCurrentUrl());
	        Assert.assertTrue(driver.getCurrentUrl().contains("secure"));
	    }

	    @AfterClass
	    public void tearDown(){
	        if (driver != null) {
	            driver.quit();
	        }
	    }
	 
}
