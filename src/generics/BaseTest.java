package generics;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest implements IAutoConstant{
	public WebDriver driver;
	public static ExtentReports extent;
	public static ExtentTest extentTest;
	static{
		System.setProperty(GECKO_KEY, GECKO_VALUE);
		System.setProperty(CHROME_KEY, CHROME_VALUE);
	}

	@BeforeTest
	public void setextent() {
		extent=Lib.generateExtentReport();
	}

	@BeforeMethod
	public  void openApplication(){

		{
			ChromeOptions options= new ChromeOptions();
			options.addArguments("window-size=1366,768");
			options.addArguments("--disable-notifications");
			options.addArguments("--disable-geolocation");
			options.addArguments("--ignore-certificate-errors");
			driver=new ChromeDriver(options);
		}

		String url = Lib.getPropertyValue("URL");
		driver.get(url);
		String implicitWait =Lib.getPropertyValue("IMPLICITWAIT");
		Long timeout =Long.parseLong(implicitWait);
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void closeBrowser(ITestResult result) throws IOException
	{
		if(result.getStatus()==ITestResult.FAILURE){
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS "+result.getName()); 
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS "+result.getThrowable()); 
			String screenshotPath = Lib.captureScreenshots(driver, result.getName());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath)); 
		}
		else if(result.getStatus()==ITestResult.SKIP){
			extentTest.log(LogStatus.SKIP, "Test Case SKIPPED IS " + result.getName());
		}
		else if(result.getStatus()==ITestResult.SUCCESS){
			extentTest.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());

		}
		extent.endTest(extentTest);
		driver.close();

	}

	@AfterTest
	public void endReport(){
		extent.flush();
		extent.close();
	}
}