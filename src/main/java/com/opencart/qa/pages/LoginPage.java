package com.opencart.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	
	//Every page will have its own driver...and initially the value of driver will be null
	//To initilise this driver here we need to create the constructor of the LoginPage class, so when someone creates 
	//object of loginPage this constructor will be called supply the driver value ...give the same driver to 
	//this private Driver
	
	//1.Inintial Setup: Calling driver and elementUtil
	private WebDriver driver;
	private ElementUtil eleutil;   //call ElementUtil class into the Login page
	
	//2.Every page will have its own constructors
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		eleutil=new ElementUtil(driver);
	}
	
	//3.Private By locators are used.According to the Page class, we have to follow the Enacapsulation.
	//Means all the by locators should be maintained with Private so that no can can access outside the class .
	//Final keyword can be used so that no can change the values within the class also
	//No need to define the locators in the Constant...as they will be used internally in this page
	private final By emailId=By.id("input-email");
	private final By password=By.id("input-password");
	private final By loginBtn=By.cssSelector("input[type='submit']");
	private final By forgotPwdLnk=By.linkText("Forgotten Password");
	private final By registerLink=By.linkText("Register");
	
	
	//4.Public page methods...we need to crete metodsfor tis page...1st method to get title
	//We need to provide wait for fetching the title ...we can use ElementUtil waitTitle method
	//Account Login is the actual hardcoded value...which cant be mentioned here.Hence we can maintain a constant page
	//where we can mention the vallues of title and timeout
	//Assert should be mentioned in the Test class.Hence we return title here and pass it to the Test Class
	
	@Step("getting login page title value...")
	public String getLoginPageTitle() {
						
		String actTitle=eleutil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE,AppConstants.SHORT_TIME_OUT );
		System.out.println("Login Page Title: " +actTitle);
		return actTitle;
		//Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
		
//		 String title=driver.getTitle();
//		  Assert.assertEquals(title, "Account Login");
	}
	
	@Step("getting login page URL...")
	public String getLoginPageURL() {
		String actURL=eleutil.waitForURLContains(AppConstants.LOGIN_PAGE_URL, AppConstants.SHORT_TIME_OUT);  //Tis gives
												//the full URL which holds the fraction of URL defined by AppConstants.LOGIN_PAGE_URL
		System.out.println(actURL);  //gives the URL value
		return actURL;
	}
	
	
	@Step("checking forgot psswd link exist or not...")
	public boolean isForgotPwdLinkExist() {
		return eleutil.waitForElementVisible(forgotPwdLnk, AppConstants.MEDIUM_TIME_OUT).isDisplayed();
	}
	
	
	@Step("User is logged in with the Username : {0} and Password:{1}")    //Allure will automatically capture username and passwrd at 0 and 1 index 
	public HomePage doLogin(String username,String pwd) {
		System.out.println("App credentials: " +username+ " : " +pwd);
		eleutil.doSendKeys(emailId, username, AppConstants.MEDIUM_TIME_OUT);  //Here we can supply a wait for the email
		eleutil.doSendKeys(password, pwd);
		eleutil.doClick(loginBtn);
		//return driver.getTitle();          //We can return anything here...for example purpose we are returning Title hrer
	return new HomePage(driver);     //Here we are doing page chaining by creating an object for HomePage class and 
									//passing it to thhe doLogin().
	}
	
	
	
	//Here we are using the private variables bylocators in the Public metohds.This concept is called as Encapsualtion.
	//Page Object are the best exxample of Encapsulation.Here it is private and final so tat no one can change the
	//value outside the class and within the class
	
	
	//Private driver====> Page class will hve its own driver hence it is private...we can use the driver for other purpose also,
	
	
	
	@Step("Navigate to register page")
	public RegisterPage navigateToRegisterPage() {
		eleutil.waitForElementClickable(registerLink, AppConstants.SHORT_TIME_OUT);
		return new RegisterPage(driver);
		
	}
	
	
	
	
	
	
	
	
	
	
}
