package com.qa.amazon.tests;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;
import com.qa.amazon.baseTest.BaseTest;
import com.qa.amazon.listeners.ExtentTestManager;
import com.qa.amazon.listeners.TestListener;
import com.qa.amazon.pages.HomePage;
import com.qa.amazon.pages.LoginPage;

public class VerifyAmazonTest extends BaseTest {

	private LoginPage loginPage;
	private HomePage homePage;
	private String username, password, expectedTitle;

	@BeforeTest
	public void before() {

		super.initializeDriver();

		log.info("Before test - setting up test data");
		username = testInputData.get("Username").toString().trim();
		password = testInputData.get("Password").toString().trim();
		expectedTitle = testInputData.get("title").toString().trim();
		log.info("Before test - setting up test data finished.");

		
		loginPage = new LoginPage(BaseTest.driver);
		homePage = new HomePage(BaseTest.driver);


	}

	@Test(priority = 1, groups = "Home")
	public void verifyHomePageTitleTest(Method method) {
		ExtentTestManager.startTest(method.getName(), "verifyHomePageTitleTest");
		String actualTitle = homePage.getPageTitle();
		Assert.assertEquals(actualTitle, expectedTitle, "Title is not as expected");

	}

	@Test(priority = 2, groups = "Login")
	public void verifyPasswordErrorTest(Method method) {
		ExtentTestManager.startTest(method.getName(), "verifyPasswordErrorTest");
		loginPage.clickOnSignIn();
		loginPage.enterEmail(username);
		loginPage.clickOnContinue();
		loginPage.enterPassword(password);
//		loginPage.clickOnLoginButton();
//		Assert.assertTrue(loginPage.getPasswordError(), "Password error is not as expected");
		Assert.assertTrue(false);
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
