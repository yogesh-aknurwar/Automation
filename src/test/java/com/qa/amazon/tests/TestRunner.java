package com.qa.amazon.tests;

import org.testng.TestNG;

import com.qa.amazon.listeners.TestListener;


public class TestRunner {

	static TestNG testNg;
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		TestListener report = new TestListener();		
		testNg = new TestNG();		
		testNg.setTestClasses(new Class[] {VerifyAmazonTest.class});
		testNg.addListener(report);
		testNg.run();
	}

}
