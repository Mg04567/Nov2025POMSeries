package com.opencart.qa.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.module.Browser;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import com.opencart.qa.exceptions.BrowserException;
import com.opencart.qa.exceptions.FrameworkException;

public class DriverFactory {
	
	WebDriver driver;
	Properties prop;    //we create the reference at the top so that i can use the reference anywhere within the lass
	public static String highlight;
	public OptionsManager optionsManager;
	public static ThreadLocal<WebDriver> tlDriver=new ThreadLocal<WebDriver>();
	
	
public WebDriver initDriver(Properties prop) {         // Change to properties prop
	//String browsername=System.getProperty("browser");  //Here after configuration with JENKINS..we need to change the config from properites
														//file to system.getproperty and the browser parameter is from JENKINS CHOOSE
														//parameter file
	 
	
	String browserName=prop.getProperty("browser");			//declare broswername here of type String
	System.out.println("Browser name is :"  +browserName);
	
	highlight=prop.getProperty("highlight");   		//highlight variable is assigned with the prop value here
	optionsManager=new OptionsManager(prop);
	
	switch(browserName.trim().toLowerCase()) {
	case "chrome":	
		
		//Write the logic here for remote driver configurations:
		//remote value=true in config file..it is a string hence we need to typecast to boolean for if condition
		
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			//run test cases on grid...we need to create an initialise remote driver method with cross browsing
			//If remote flag is true testing will be done in remote machhine and if false itll run on local mcine
			initRemoteDriver(browserName);
		}
		else 
		{
			//run test cases on local machine
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		}
	//tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
	// driver=new ChromeDriver(optionsManager.getChromeOptions());
	     break;
	     
	case "firefox":
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			//run test cases on grid...we need to create an initialise remote driver method with cross browsing
			initRemoteDriver(browserName);
		}
		else 
		{
			//run test cases on local machine
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		}
		//tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		// driver=new FirefoxDriver(optionsManager.getFirefoxOptions());
		 break;
		 
		 
	case "edge":
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			//run test cases on grid...we need to create an initialise remote driver method with cross browsing
			initRemoteDriver(browserName);
		}
		else 
		{
			//run test cases on local machine
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
		}
		
		//tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
		// driver=new EdgeDriver(optionsManager.getEdgeOptions());
		 break;
		 
		 //For safari only local execution...no remote execcution
	case "safari":
		tlDriver.set(new SafariDriver());
		
		// driver=new SafariDriver();
		 break;
		default:
			System.out.println("===========Invalid Browser==========="  +browserName);
			throw new BrowserException("===========Invalid Browser===========");
			}
	
//	driver.manage().deleteAllCookies();       
//	driver.manage().window().maximize();
//	driver.get(prop.getProperty("url"));                              //change url to hardcoded value from .properties file
//	
//	return driver;
	
	getDriver().manage().deleteAllCookies();       //If we dont supply the getDriver() here we'll get NPE
	getDriver().manage().window().maximize();
	getDriver().get(prop.getProperty("url"));                              //change url to hardcoded value from .properties file
	
	return getDriver();
}


/**
 * this method will setup the RWD with hubURL and browser options ,it will supply the test bloks to the remote grid machine
 * @param browsername
 */

private void initRemoteDriver(String browserName) {
	System.out.println("Running the test cases on selenium grid:" +browserName );
	try {
	switch (browserName.trim().toLowerCase()) {
	case "chrome":
		tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
		break;
	case "firefox":
		tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
		break;
	case "edge":
		tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
		break;
	default:
		System.out.println("Please supply the right browser name: " +browserName);
		break;
	}
	}catch(MalformedURLException e) {
		e.printStackTrace();
	}
}


/**
 * This will return one local copy of the driver for a specific thread
 * @return
 */

public static WebDriver getDriver() {
	return tlDriver.get();    //Someone calls the driver it'll give a copy of the WebDriver which points to the specifi Browser
}






//the below FileInputStream will connect a stream with the config.properties
/**
 * This metod is used to initialise properties file
 * @return it returns the properties class objet which is having all the properties (key-value pair)
 */



//mvn test -Denv="qa"   (Maven command)
public Properties initProp() {
	FileInputStream fs=null;  //If we are not passing any enevironment name envName will be null
	prop=new Properties();     //Creating object of prop
	
	String envName=System.getProperty("env");            
	System.out.println("Env is : " +envName);
	try {
	if (envName==null) {
		System.out.println("Env name is null , hence running the test cases in QA environment");
		fs=new FileInputStream("./src/test/resources/config/config.qa.properties");
	}else {
	
	switch (envName.trim().toLowerCase()) {
	case "qa":
		fs=new FileInputStream("./src/test/resources/config/config.qa.properties");
		
		break;
	case "dev":
		fs=new FileInputStream("./src/test/resources/config/config.dev.properties");
		break;
	case "stage":
		fs=new FileInputStream("./src/test/resources/config/config.stage.properties");
		break;
	case "uat":
		fs=new FileInputStream("./src/test/resources/config/config.uat.properties");
		break;
	case "prod":
		fs=new FileInputStream("./src/test/resources/config/config.properties");
		break;
	default:
		System.out.println("==========Invalid Environment name===========" +envName);
		throw new FrameworkException("Invalid ENV Name" +envName);
		//	break;
	}
	}
	
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	try {
		
		prop.load(fs); 
	} catch (IOException e) {
		
		e.printStackTrace();
	}
		return prop;                     //This method returns properties.
	}
	
//try {
	//FileInputStream fs=new FileInputStream("./src/test/resources/config/config.properties");  //comment this as we have already decl
										//ared in above switch case
//	try {
//	prop=new Properties();
//	prop.load(fs);                      //Here initialise the prop of Properties() class and then prop.load() method is used 
//										//to pass the ofig properties file to it
//} catch (FileNotFoundException e) {
//	
//	e.printStackTrace();
//} catch (IOException e) {
//	
//	e.printStackTrace();
//}
//	return prop;                     //This method returns properties.
//}


/**
 * takes screenshot for ChaninTest
 * @return
 */

public static File getScreenShotFile() {
	return ((TakesScreenshot) getDriver()).getScreenshotAs((OutputType.FILE)); //JPEG, .png files can be used here, screenshot will be stored in the internl temporary directory.
}																			//It returns the file object…which points to location of file



public static byte[] getScreenShotByte() {
	return ((TakesScreenshot) getDriver()).getScreenshotAs((OutputType.BYTES));
}


public static String getScreenShotBase() {
	return ((TakesScreenshot) getDriver()).getScreenshotAs((OutputType.BASE64));
}

}