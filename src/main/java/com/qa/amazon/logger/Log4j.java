package com.qa.amazon.logger;
/*
 *
 * This class is developed for Log4j logger to write execution log in console and log file.
 */
import org.apache.log4j.Logger;

import org.apache.log4j.PropertyConfigurator;

import com.qa.amazon.utility.TestUtil;

import static com.qa.amazon.constants.Constants.LOGGER;

import java.io.InputStream;

public class Log4j {

	@SuppressWarnings("rawtypes")
	public static Logger initLog4j(Class cl) {
		TestUtil util = new TestUtil();
		Logger log = Logger.getLogger(cl);
		InputStream logReader = util.getFileFromResourceAsStream(LOGGER);
		PropertyConfigurator.configure(logReader);
		return log;	
	}
}
