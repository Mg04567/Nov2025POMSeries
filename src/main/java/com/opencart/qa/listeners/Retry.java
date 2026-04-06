package com.opencart.qa.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
	private int count = 0;
	private static int maxTry = 3;     //Stati is used here so that the maxtry can be used without the need to create the object

	@Override
	public boolean retry(ITestResult iTestResult) {
		if (!iTestResult.isSuccess()) { // Check if test not succeed  F F P   ...checks if test succeed..TEST Fails ! condition mkes true 
			if (count < maxTry) { // Check if maxtry count is reached		....once true enters the loop increases the count
				count++; // Increase the maxTry count by 1
				iTestResult.setStatus(ITestResult.FAILURE); // Mark test as failed   ....the test is marked as fail
				return true; // Tells TestNG to re-run the test               ...return true tells testng to rerun the case
			} else {
				iTestResult.setStatus(ITestResult.FAILURE); // If maxCount reached,test marked as failed
			}
		} else {
			iTestResult.setStatus(ITestResult.SUCCESS); // If test passes,f TestResult.isSuccess..is true..the whole condtion is false
											//Hence comes out of if loop  mrks test as success and return false tells the testng to not rerun.
		}
		return false;//no need to re-run the test
		
	}
}