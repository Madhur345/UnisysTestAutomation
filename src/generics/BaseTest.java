package generics;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class BaseTest implements IAutoConstant{
	public WebDriver driver;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	static{
		System.setProperty(GECKO_KEY, GECKO_VALUE);
		System.setProperty(CHROME_KEY, CHROME_VALUE);
	}

	@BeforeTest
	public void setExtent() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/myReport.html");

		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("InteliApp Desktop Testing");
		htmlReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("Host name", "localhost");
		extent.setSystemInfo("Environemnt", "QA");
		extent.setSystemInfo("user", "vishal");
		
		
		
	}
	
	@BeforeMethod
	@Parameters("browser")
	public  void openApplication(String browser){
		if(browser.equals("firefox"))
		{
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("geo.enabled", false);
			profile.setPreference("webnotifications", false);
			FirefoxOptions options = new FirefoxOptions();
			options.addPreference("security.sandbox.content.level", 5);
			options.setProfile(profile);
			driver = new FirefoxDriver(options);
			Capabilities browserCap = ((RemoteWebDriver) driver).getCapabilities();
			String browserName = browserCap.getBrowserName();
			String browserVersion = browserCap.getVersion();
			test= extent.createTest("This is Title Section ", "This is Description Section "  + " Browser Name: "+browserName + "Browser Version = "+browserVersion);
		}
		else{
			ChromeOptions options= new ChromeOptions();
			options.addArguments("window-size=1366,768");
			options.addArguments("--disable-notifications");
			options.addArguments("--disable-geolocation");
			options.addArguments("--ignore-certificate-errors");
			driver=new ChromeDriver(options);
			Capabilities browserCap = ((RemoteWebDriver) driver).getCapabilities();
			String browserName = browserCap.getBrowserName();
			String browserVersion = browserCap.getVersion();
			test= extent.createTest("This is Title Section ", "This is Description Section "  + " Browser Name: "+browserName + "Browser Version = "+browserVersion);
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
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName()); 
			test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); 
			String screenshotPath = Lib.captureScreenshots(driver, result.getName());
			test.addScreenCaptureFromPath(screenshotPath);
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, "Test Case SKIPPED IS " + result.getName());
		}
		else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "Test Case PASSED IS " + result.getName());
		}
		extent.flush();
		driver.close();
	}
}