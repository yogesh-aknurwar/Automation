package com.qa.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.amazon.utility.TestUtil;

public class HomePage extends TestUtil{
	
	
	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	private By signIn = By.xpath("//a[@id='nav-link-accountList']");
	
	public String getPageTitle()
	{
		log.info("Waiting for Home Page to be visible.");
		return super.returnCurrentPageTitle();
		
	}

}
