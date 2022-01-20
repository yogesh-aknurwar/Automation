package com.qa.amazon.listeners;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import static com.qa.amazon.constants.Constants.*;

import com.qa.amazon.baseTest.BaseTest;
import com.relevantcodes.extentreports.LogStatus;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class TestListener extends BaseTest implements ITestListener{

	
	private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }
 
    @Override
    public void onStart(ITestContext iTestContext) {
        log.info("I am in onStart method " + iTestContext.getName());
        iTestContext.setAttribute("WebDriver", BaseTest.driver);
     }
 
    @Override
    public void onFinish(ITestContext iTestContext) {
    	log.info("I am in onFinish method " + iTestContext.getName());
        //Do tier down operations for extentreports reporting!
        ExtentTestManager.endTest();
        ExtentManager.getReporter().flush();
    }
 
    @Override
    public void onTestStart(ITestResult iTestResult) {
    	log.info("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
    }
 
    @Override
    public void onTestSuccess(ITestResult iTestResult) {
    	log.info("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
        //ExtentReports log operation for passed tests.
        ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
    }
 
    @Override
    public void onTestFailure(ITestResult iTestResult) {
    	log.info("I am in onTestFailure method " + getTestMethodName(iTestResult) + " Failed");
        //Take base64Screenshot screenshot.
        String base64Screenshot = getScreenshot();
 
        //ExtentReports log and screenshot operations for failed tests.
        ExtentTestManager.getTest().log(LogStatus.FAIL, iTestResult.getThrowable().getMessage(), ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
    }
 
    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        //ExtentReports log operation for skipped tests.
        ExtentTestManager.getTest().log(LogStatus.SKIP,iTestResult.getName() + " got Skipped" , iTestResult.getThrowable().getMessage());
    }
 
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    	log.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }
    
	public String getScreenshot() {

		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
	//	String Dest = "/home/jenkins/workspace/TEST_AUTOMATION/OVF_SONAR_Risk_Automation/OVF_SONAR_Risk_POC/FailedScreenShots/" + System.currentTimeMillis() + ".png";
	//	String Dest = System.getProperty("user.dir") +File.separator+ "ScreenShots"+File.separator+ "FailedScreenShots" +File.separator+ System.currentTimeMillis() + ".png";
		String Dest = FAILED_SCREENSHOT_PATH + System.currentTimeMillis() + ".png";

		File file = new File(Dest);

		try {
			ImageIO.write(screenshot.getImage(), "PNG", file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String base64Image = "";

		try (FileInputStream imageInFile = new FileInputStream(file)) {
			// Reading a Image file from file system
			byte imageData[] = new byte[(int) file.length()];
			imageInFile.read(imageData);
			base64Image = Base64.getEncoder().encodeToString(imageData);
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}
		return "data:image/png;base64, " + base64Image;
	}
	
}
