package scripts;

import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.LogStatus;

import generics.BaseTest;
import generics.Lib;
import pompages.LoginPage;

public class TestValidLogin extends BaseTest {

	@Test
	public void validLogin() throws InterruptedException{

		extentTest = extent.startTest(TestValidLogin.class.getName());
		LoginPage lp = new LoginPage(driver);
		String username = Lib.getcellValue("ValidLogin",1,0);
		extentTest.log(LogStatus.INFO, "Start Testing ");
		//log.info("Starting Testing");
		lp.setUserName(username);
		Thread.sleep(2000);
		String firstName =Lib.getcellValue("ValidLogin",1,1);
		lp.setFirstName(firstName);
		Thread.sleep(2000);
		String lastName =Lib.getcellValue("ValidLogin",1,2);
		lp.setLastName(lastName);
		Thread.sleep(2000);
		lp.clickLogin();
		Thread.sleep(3000);
		String expectedURL ="https://inteliserve.unisys";
		String actualURL =driver.getCurrentUrl();
		Reporter.log(actualURL,false);
		SoftAssert s = new SoftAssert();
		s.assertEquals(actualURL, expectedURL);
		extentTest.log(LogStatus.PASS,"URL didn't match");
		s.assertAll();
		
	}
}
