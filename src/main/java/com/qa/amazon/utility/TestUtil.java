package com.qa.amazon.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Colors;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.qa.amazon.logger.Log4j;

import static com.qa.amazon.constants.Constants.*;

/**
 * @author ina291yogaknu
 *
 */
public class TestUtil {

	public static Workbook book;
	public static Sheet sheet;
	protected static WebDriver driver;
	private static WebDriverWait wait;
	static protected Logger log;
	protected static Actions act;
	public static Properties prop;
	public static WebElement webelement;	
	InputStream inputStream;
	public static JSONObject jsonObject;
	BufferedReader reader;
	
	public TestUtil() {
	}
	public TestUtil(WebDriver tdriver) {
		driver = tdriver;
		log = Log4j.initLog4j(TestUtil.class);
	}

	

	public static void switchToParentFrame() {
		driver.switchTo().parentFrame();	
	}
	public static WebDriver openNewTab() {
		return driver.switchTo().newWindow(WindowType.TAB);
	}
	public static WebDriver openNewWindow() {
		return driver.switchTo().newWindow(WindowType.WINDOW);
	}
	public static WebDriver getParentWindow() {
		Set<String> handles = driver.getWindowHandles();
		List<String> ls = new ArrayList<String> (handles);
		String parentWindow = ls.get(0);
		return driver.switchTo().window(parentWindow);		
	}
	public static WebDriver getChildWindow() {
		Set<String> handles = driver.getWindowHandles();
		List<String> ls = new ArrayList<String> (handles);
		String childWindow = ls.get(1);
		return driver.switchTo().window(childWindow);		
	}
	public static void takeElementScreenshot(By ele, String fileName) {
		webelement = driver.findElement(ele);
		File scrImage = webelement.getScreenshotAs(OutputType.FILE); 
		try {
			FileUtils.copyFile(scrImage, new File(ELEMENT_SCREENSHOT_PATH+fileName +".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void getPageScreenshot(String pageName) {
		TakesScreenshot scrShot = ((TakesScreenshot)driver);
		File scrImage = scrShot.getScreenshotAs(OutputType.FILE); 
		try {
			FileUtils.copyFile(scrImage, new File(PAGE_SCREENSHOT_PATH+pageName +".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void mouseHover(By ele) {
		act = new Actions(driver);
		act.moveToElement(driver.findElement(ele));
		highlight(ele);
	}

	public static void waitForElementToBeVisible(By ele) {

		try {
			new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT));
//			wait = new WebDriverWait(driver, EXPLICIT_WAIT_TIMEOUT);
			wait.until(ExpectedConditions.visibilityOfElementLocated(ele));
			highlight(ele);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(ele + " is not visible , hence  test case got failed.");
		}

	}

	public static void waitForElementToBeClickable(By ele) {

		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_TIMEOUT));
//			wait = new WebDriverWait(driver, EXPLICIT_WAIT_TIMEOUT);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			highlight(ele);
		}

		catch (Exception e) {
			e.printStackTrace();
			Assert.fail(ele + " is not clickable , hence  test case got failed.");
		}
	}

	
	
	public static void click(By ele) {
		WebElement element = getWebElement(ele);
		waitForLoad();
		element.click();
	}

	public static void clickByJS(By ele) {
		WebElement element = getWebElement(ele);
		waitForLoad();
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", element);
	}
	
	private static void highlight(By ele) {
		WebElement element = getWebElement(ele);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].style.border='4px solid red'", element);
	}

	public static WebElement getWebElement(By ele) {		
		return driver.findElement(ele);
	}
	
	public static void scrollPageToViewElement(By ele) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].scrollIntoView();", getWebElement(ele));
	}
	public static String returnCurrentPageTitle() {
		return driver.getTitle();
	}

	public static boolean isElementDisplayed(By ele) {
		waitForLoad();
		highlight(ele);
		int ele_size = driver.findElements(ele).size();

		if (ele_size == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isElementEnabled(By ele) {
		highlight(ele);
		return getWebElement(ele).isEnabled();
	}

	public boolean isCheckboxSelected(By ele) {
		highlight(ele);
		return getWebElement(ele).isSelected();
	}

	public static String getElementText(By ele) {
		highlight(ele);
		waitForElementToBeVisible(ele);
		return getWebElement(ele).getText().trim();
	}

	public boolean isRadioButtonSelected(By ele) {
		highlight(ele);
		return getWebElement(ele).isSelected();

	}

	public String getAttributeValue(By ele, String attributeName) {
		highlight(ele);
		return getWebElement(ele).getAttribute(attributeName).trim();

	}

	public String getTextOfElement(By ele) {
		highlight(ele);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String text = (String) jse.executeScript("return arguments[0].innerHTML", driver.findElement(ele));
		return text.trim();

	}

	public void switchToAlert() {
		driver.switchTo().alert();
	}
	public void alertAccept() {
		driver.switchTo().alert().accept();
	}
	public void alertCancel() {
		driver.switchTo().alert().dismiss();
	}
	public static String replaceString(String xpathString, String replaceBy) {
		StringBuffer xpathBuffer = new StringBuffer(xpathString);
		xpathBuffer.replace(xpathBuffer.indexOf("$"), xpathBuffer.lastIndexOf("$") + 1, replaceBy);
		return xpathBuffer.toString();
	}

	public static void enterStringValueInInputField(By ele, String value) {
		highlight(ele);
		getWebElement(ele).clear();
		getWebElement(ele).sendKeys(value);
	}

	public void enterNumericValueInInputField(By ele, int value) {
		highlight(ele);
		getWebElement(ele).clear();
		getWebElement(ele).sendKeys("" + value);
	}

	public static void enterUsingSendKeys(By ele) {
		highlight(ele);
		getWebElement(ele).sendKeys(Keys.ENTER);
	}

	public void tabUsingSendKeys(By ele) {
		highlight(ele);
		getWebElement(ele).sendKeys(Keys.TAB);
	}

	
	public void selectValueFromInputDropdown(By ele, String text) {
		highlight(ele);
		enterStringValueInInputField(ele, text);
		enterUsingSendKeys(ele);
	}
	
	public void selectByVisibleText(By ele, String text) {
		highlight(ele);
		Select sel = new Select(getWebElement(ele));
		sel.selectByVisibleText(text);
		log.info(text + " text is selected.");
	}
	
	public void selectByValue(By ele, String value) {
		highlight(ele);
		Select sel = new Select(getWebElement(ele));
		sel.selectByValue(value);
		log.info(value + " value is selected.");
	}
	
	public void selectByIndex(By ele, int index) {
		highlight(ele);
		Select sel = new Select(getWebElement(ele));
		sel.selectByIndex(index);
		log.info(index + " index is selected.");
	}
	public static void waitForSpecifiedTime(int i) {
		try {
			Thread.sleep(i * ONESEC);
		} catch (InterruptedException e) {
			log.error("Exception occured while waiting: " + e.getMessage());
		}

	}

	public static void shortWait() {
		try {
			Thread.sleep(SHORT_WAIT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void mediumWait() {
		try {
			Thread.sleep(MEDIUM_WAIT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void longWait() {
		try {
			Thread.sleep(LONG_WAIT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	

	public static void deleteFile(String fileName) {
		String userDir = System.getProperty("user.dir");
		File file = new File(userDir + "\\Download\\" + fileName);
		if (!file.exists()) {
			log.info("File is not present");
		} else {
			// delete a file
			log.info("File already exist");
			file.delete();
		}
	}

	public static Sheet readExcelFile(String filePath, int sheetIndex) {
		File file = new File(filePath);
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			log.info("inputStream object is created");
		} catch (FileNotFoundException e) {

			log.error("testData file not found");
		}

		Workbook workbook = null;

		try {
			workbook = new XSSFWorkbook(inputStream);
			log.info("workbook object is created");
		} catch (IOException e) {
			log.error("IO exception has occured");
		}

		Sheet sheet = (Sheet) workbook.getSheetAt(sheetIndex);
		log.info("Data sheet is found");
		return sheet;
	}

	public JSONObject readJSONFile(String JsonFileName)
	{
		String jsonFileContent = "";
		FileInputStream fis = null;
		String fileName = JsonFileName;
		//BufferedReader reader;

        ClassLoader classLoader = getClass().getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(fileName);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        		BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
            	jsonFileContent = jsonFileContent + line.trim();
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//    	finally
//		{
//			try 
//			{
//	           reader.close();
//	           fis.close();
//	        }
//			catch (IOException ex) 
//			{
//	           log.error("Failed to read json file. Detailed error - "+ ex.getMessage());
//	        }
//		}
		JSONParser expectedOutputParser = new JSONParser();
		JSONObject expectedOutputJSONResponse = new JSONObject();
		try{
		expectedOutputJSONResponse = (JSONObject)expectedOutputParser.parse(jsonFileContent);
		}
		catch(Exception ParseException)
		{
			System.out.println("Parsing has failed."+ParseException.getMessage());
		}
		return expectedOutputJSONResponse;
		 
	}
	
	

	public static void waitForLoad() {

		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				JavascriptExecutor js;
				String pageLoadStatus = null;
				do {

					js = (JavascriptExecutor) driver;

					pageLoadStatus = (String) js.executeScript("return document.readyState");

				} while (!pageLoadStatus.equals("complete"));

				return js.executeScript("return document.readyState").equals("complete");
			}
		};
		try {
			new WebDriverWait(driver, Duration.ofSeconds(SHORT_WAIT));
//			wait = new WebDriverWait(driver, SHORT_WAIT);
			wait.until(pageLoadCondition);
		} catch (Throwable error) {
			System.out.println(
					"------------------------------------------------------------------------------------");

		}
	}

	public static Object[][] getTestData(String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);

		Object data[][] = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
			}
		}

		return data;

	}

	 public Properties initializeProperties(String fileName) {
		 inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
			prop = new Properties();			
			try {
				prop.load(inputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return prop;
		}
	 
	 public InputStream getFileFromResourceAsStream(String fileName) {

	        ClassLoader classLoader = getClass().getClassLoader();
	        InputStream inputStream = classLoader.getResourceAsStream(fileName);

	        if (inputStream == null) {
	            throw new IllegalArgumentException("file not found! " + fileName);
	        } else {
	            return inputStream;
	        }

	    }
	 
	
}
