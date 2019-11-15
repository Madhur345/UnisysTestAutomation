package pompages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	@FindBy(id="email")
	WebElement unTB;

	@FindBy(id="firstname")
	WebElement firstName;
	
	@FindBy(id="lastname")
	WebElement lastName;

	@FindBy(xpath="//button[@type='submit']")
	WebElement loginButton;

	public LoginPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}

	public void setUserName(String un){
		unTB.sendKeys(un);
	}
	public void setFirstName(String firstname){
		firstName.sendKeys(firstname);
	}
	
	public void setLastName(String lastname){
		lastName.sendKeys(lastname);
	}
	public void clickLogin(){
		loginButton.click();
	}
}
