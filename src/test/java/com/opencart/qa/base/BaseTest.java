	package com.opencart.qa.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.aventstack.chaintest.plugins.ChainTestListener;
import com.opencart.qa.factory.DriverFactory;
import com.opencart.qa.listeners.TestAllureListener;
import com.opencart.qa.pages.AddToCartPage;
import com.opencart.qa.pages.HomePage;
import com.opencart.qa.pages.LoginPage;
import com.opencart.qa.pages.ProductInfoPage;
import com.opencart.qa.pages.RegisterPage;
import com.opencart.qa.pages.ResultsPage;

//@Listeners({TestAllureListener.class})
//@Listeners({TestAllureListener.class, chainTestListener.class})
//@Listeners({ChainTestListener.class,TestAllureListener.class})
public class BaseTest{

	
	//Here we have to initialise Driver and initialise the setup() and Teardown() metohds
	
	protected WebDriver driver;
	protected Properties prop;
	DriverFactory df;    //Inintialise the driver by using the driverfactory object
	protected LoginPage loginPage;	//create reference variables for other classes also...Login page objet is created here
			//so tat the objet can be used by any other test classes and child classes...no need to create the object agian and again
	    //change the prop type to proteted to access in the child classess
	protected HomePage homePage;
	protected ResultsPage resultsPage;
	protected ProductInfoPage productInfoPage;
	protected AddToCartPage addToCartPage;
	protected RegisterPage registerPage;
	
	
	@Parameters({"browser"})
	@BeforeTest
	//public void setup(@Optional("firefox") String browsername) {
	public void setup(@Optional("chrome") String browsername) {	
	df=new DriverFactory();
		prop=df.initProp();
		
		if (browsername!=null) {
			prop.setProperty("browser", browsername);   //Here we are settig the value from config.prop browser to browsername
													//in the xml file
		}
		
		driver=df.initDriver(prop);   //This initDriver method from DriverFactory class returns the driver..hence we need to
									//store it in the driver reference
		loginPage=new LoginPage(driver);    //Here we are passing the driver back to Login Page as Login page is waiting fr driver
		}
	
	
	@AfterMethod
	public void attachscreenShot(ITestResult result) {    //ITestResult is another interface in Testng..it describes the result of test
		if (!result.isSuccess()) {						//only for failure test cases = true
			ChainTestListener.embed(DriverFactory.getScreenShotFile(), "image/png"); //bytes is actually replced by DriverFactory.getScreenShotFile
																					//to feth the type of file
		}
		//ChainTestListener.embed(DriverFactory.getScreenShotFile(), "image/png");   //This helps to get the srceenshot for all the 
																					//test cases
	}
	
	
	
	@AfterTest
	public void tearDown() {
		driver.quit();       //We should always maintain a driver with ref in the beginning of base class so that we can call the 
							//driver again and again for all metods
	}
	
	
	
	
}
