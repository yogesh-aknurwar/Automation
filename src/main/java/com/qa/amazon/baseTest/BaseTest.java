package com.qa.amazon.baseTest;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.qa.amazon.constants.Constants.*;

import com.qa.amazon.logger.Log4j;
import com.qa.amazon.utility.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;


/**
 * @author ina291yogaknu
 *
 */
public class BaseTest {

	public static WebDriver driver;
	protected static Logger log = Log4j.initLog4j(BaseTest.class);
	public static JSONObject configurationData,testInputData;
	public static String browser, url;
	public static FileInputStream fis;
	public static Properties prop;
	TestUtil util = new TestUtil();
	
	protected void initializeDriver() {
		prop = util.initializeProperties(CONFIGFILE);
		testInputData = util.readJSONFile(TESTDATA);
		browser = prop.getProperty("BROWSER");
		url = prop.getProperty("URL");
		log.info("Selected browser is --> " + browser);		
		this.launchBrowser(browser);

	}

	private WebDriver launchBrowser(String browserName) {

		switch (browserName) {

		case "Chrome":
						log.info("Chrome browser is selected to execute test cases");
						WebDriverManager.chromedriver().setup();
//						System.setProperty("webdriver.chrome.driver", CHROMEDRIVER);
						log.info("WebDriverManager is set to Chrome");
						ChromeOptions options = new ChromeOptions();
						Map<String, Object> prefs = new HashMap<String, Object>();
						prefs.put("credentials_enable_service", false);
						prefs.put("profile.password_manager_enabled", false);
						options.setExperimentalOption("prefs", prefs);
						DesiredCapabilities capabilities = new DesiredCapabilities();
						capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
						capabilities.setCapability(ChromeOptions.CAPABILITY, options);
						log.info("Chrome desired Property is set.");
						driver = new ChromeDriver(capabilities);
						log.info("Chrome driver object is created");
						break;

		case "Firefox":
						log.info("Firfoox browser is selected to execute test cases");
						WebDriverManager.firefoxdriver().setup();
						log.info("WebDriverManager is set to Firefox");
						driver = new FirefoxDriver();
						log.info("Firfoox driver object is created");
						break;

		case "Edge":
						log.info("Edge browser is selected to execute test cases.");
						WebDriverManager.edgedriver().setup();
						log.info("WebDriverManager is set to Edge");
						driver = new EdgeDriver();
						log.info("Edge driver object is created");
						break;

		case "InternetExplorer":
						log.info("IE browser is selected to execute test cases.");
						WebDriverManager.iedriver().setup();
						log.info("WebDriverManager is set to IE");
						driver = new InternetExplorerDriver();
						log.info("Internet Explorer driver object is created");
						break;

		default:

						throw new BrowserNotInitializedException(
						"Browser name is not specified correctly, please check and try again!");
		}

		openApplication();
		return driver;

	}

	private void openApplication() {
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_WAIT_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
		driver.get(url);
		log.info("Application is launched.");
	}

}
