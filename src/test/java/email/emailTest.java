package email;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
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
	        driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
	    }
	 
	 @Test
	    public void userLogin()
	    {
	        WebElement usernameTxt = driver.findElement(By.id("username"));
	        usernameTxt.sendKeys("tomsmith");
	        WebElement passwordTxt = driver.findElement(By.id("password"));
	        passwordTxt.sendKeys("SuperSecretPassword!");
	        WebElement submitBtn = driver.findElement(By.className("radius"));
	        submitBtn.click();
	        log.info("Current URL is:" + driver.getCurrentUrl());
	        Assert.assertTrue(driver.getCurrentUrl().contains("secure"));
	    }
	 
	    @AfterMethod
	    public void tearDown(ITestResult result)   {
	     
	    	if(ITestResult.FAILURE==result.getStatus())   	{
	    		try {
    				TakesScreenshot ts=(TakesScreenshot)driver;
    				File source=ts.getScreenshotAs(OutputType.FILE);
 					FileHandler.copy(source, new File("./test-output/"+result.getName()+".png"));
 					log.info("Screenshot taken");
	    			} catch (Exception e)	{
	    				log.info("Exception while taking screenshot "+e.getMessage());
	    		} 
	    	}
	    }
	    @AfterClass
	    public void tearDown(){
	        if (driver != null) {
	            driver.quit();
	        }
	    }
	    

	 
}
