package com.opencart.qa.pages;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DemoPage {

	public static void main(String[] args) throws InterruptedException {
		//System.setProperty("webdriver.chrome.driver", "E:\\chromedriver-win64\\chromedriver.exe");
		WebDriver driver=null;
		//FirefoxOptions fo=new FirefoxOptions();
		ChromeOptions co=new ChromeOptions();
		
		co.setCapability("browserName", "chrome");
		try {
			driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), co);
			//driver=new FirefoxDriver();
			driver.get("https://www.google.com/");
			Thread.sleep(2000);
			System.out.println(driver.getTitle());
			driver.quit();
			
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}

	}

}
