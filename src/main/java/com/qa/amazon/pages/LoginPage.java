package com.qa.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.amazon.utility.TestUtil;

import lombok.Data;

@Data
public class LoginPage extends TestUtil{
	
	public LoginPage(WebDriver driver) {
		super(driver);
		
	}
	
	private By signIn = By.xpath("//a[@id='nav-link-accountList']");
	private By email = By.xpath("//input[@type='email']");
	private By password = By.xpath("//input[@name='password']");
	private By login = By.xpath("//input[@id='signInSubmit']");
	private By cntinue = By.xpath("//input[@id='continue']");
	private By passwordError = By.xpath("//span[contains(text(),'Your password is incorrect')]");
	
	
	public void enterEmail(String uname)
	{
		log.info("Waiting for Email to be entered");
		super.enterStringValueInInputField(email, uname);
		log.info("Email is entered.");
	}

	public void enterPassword(String pass)
	{
		log.info("Waiting for Password to be entered");
		super.enterStringValueInInputField(password, pass);
		log.info("password is entered.");
	}
	
	public void clickOnLoginButton()
	{
		log.info("Waiting to click on Login button");
		super.click(login);
		log.info("Clicked on Login button.");
	}
	
	
	public void clickOnSignIn()
	{
		log.info("Waiting to click on Sign in button");
		super.clickByJS(signIn);
		log.info("Clicked on Sign In Link.");
	}
	public void clickOnContinue()
	{
		
		log.info("Waiting to click on Continue button.");
		super.clickByJS(cntinue);
		log.info("Clicked on Continue button.");
	}
	public boolean getPasswordError()
	{
		log.info("Waiting for error to be visible.");
		return super.isElementDisplayed(passwordError);
		
	}

}
