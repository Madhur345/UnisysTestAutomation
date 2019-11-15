
package scripts;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import generics.BaseTest;
import generics.Lib;
import pompages.LoginPage;

public class TestLoginWithoutValidation extends BaseTest {

	@Test
	public void validLoginValidatioin() throws InterruptedException{

		test = extent.createTest("Login to inteliApp Desktop");
		LoginPage lp = new LoginPage(driver);
		String username = Lib.getcellValue("ValidLogin",1,0);

		test.info("Starting Testing");
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
		String expectedURL ="https://inteliserve.unisys.com/demo/dashboard";
		String actualURL =driver.getCurrentUrl();
		System.out.println(actualURL);
		SoftAssert s = new SoftAssert();
		s.assertEquals(actualURL, expectedURL);
		s.assertAll();
		test.pass("Loggin successfully");
	}
}
