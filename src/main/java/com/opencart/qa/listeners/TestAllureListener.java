
package com.opencart.qa.listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.opencart.qa.factory.DriverFactory;

import io.qameta.allure.Attachment;

public class TestAllureListener implements ITestListener {   //ITestListener is an interface

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	
	// Text attachments for Allure	
	@Attachment(value = "Page screenshot", type = "image/png")   //sreeshots will be given be Allure
	public byte[] saveScreenshotPNG(WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);  //here driver will be converted to TakesScreenshot and 
	}														//getScreenshotAs() method will get the sreenshot the driver is taken from
															//getDriver of onTestFailure() method

	// Text attachments for Allure
	@Attachment(value = "{0}", type = "text/plain")
	public static String saveTextLog(String message) {
		return message;
	}

	// HTML attachments for Allure
	@Attachment(value = "{0}", type = "text/html")
	public static String attachHtml(String html) {
		return html;
	}

	@Override
	public void onStart(ITestContext iTestContext) {
		System.out.println("I am in onStart method " + iTestContext.getName()); //at start method 
	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		System.out.println("I am in onFinish method " + iTestContext.getName());
	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
	}

	@Override     //The below method can be used to get screenshot for success cases
	public void onTestSuccess(ITestResult iTestResult) {
		System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
//		Object testClass = iTestResult.getInstance();
//		// Allure ScreenShotRobot and SaveTestLog
//		if (DriverFactory.getDriver() instanceof WebDriver) {
//			System.out.println("Screenshot captured for test case:" + getTestMethodName(iTestResult));
//			saveScreenshotPNG(DriverFactory.getDriver());
//		}
//		// Save a log on allure.
//		saveTextLog(getTestMethodName(iTestResult) + " succeed and screenshot taken!");
	
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");
		Object testClass = iTestResult.getInstance();
		// Allure ScreenShotRobot and SaveTestLog
		if (DriverFactory.getDriver() instanceof WebDriver) {
			System.out.println("Screenshot captured for test case:" + getTestMethodName(iTestResult));
			saveScreenshotPNG(DriverFactory.getDriver());     //this getDriver will give the current thread and current driver of that page
		}													//and get the screenshot
		// Save a log on allure.
		saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");		
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}

}

