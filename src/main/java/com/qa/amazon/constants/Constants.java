package com.qa.amazon.constants;

import java.io.File;
import java.time.Duration;

/**
 * @author ina291yogaknu
 *
 */
public class Constants {
	
	public static final int ONESEC = 1000;
	public static final int SHORT_WAIT = 3000;
	public static final int MEDIUM_WAIT = 6000;
	public static final int LONG_WAIT = 10000;
	public static final int EXPLICIT_WAIT_TIMEOUT = 100;
	public static final int IMPLICIT_WAIT_TIMEOUT = 50;
	public static final int PAGE_LOAD_WAIT_TIMEOUT = 50;
	public static final int FLUENT_WAIT_TIMEOUT = 20;
	public static final int POLLING_WAIT_TIMEOUT = 5;
	public final static String TESTDATA = "testdata/TestInputData.json";
	public final static String LOGGER = "logger/log4j.properties";
	public final static String CONFIGFILE = "config/config.properties";

	public final static String CHROMEDRIVER = "./src/main/resources/chromedriver.exe";
	public final static String ELEMENT_SCREENSHOT_PATH = System.getProperty("user.dir") +File.separator+ "ScreenShots"+File.separator+"ElementScreenShots"+File.separator;
	public final static String FAILED_SCREENSHOT_PATH = System.getProperty("user.dir") +File.separator+ "ScreenShots"+File.separator+ "FailedScreenShots"+File.separator;
	public final static String PAGE_SCREENSHOT_PATH = System.getProperty("user.dir") +File.separator+ "ScreenShots"+File.separator+ "FailedScreenShots"+File.separator;
//	public final static String ELEMENT_SCREENSHOT_PATH = "./ScreenShots/ElementScreenShots/";
//	public final static String FAILED_SCREENSHOT_PATH = "./ScreenShots/FailedScreenShots/";
//	public final static String PAGE_SCREENSHOT_PATH = "./ScreenShots/FailedScreenShots/";
	

}
