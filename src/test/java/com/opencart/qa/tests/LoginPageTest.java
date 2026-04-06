package com.opencart.qa.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.opencart.qa.base.BaseTest;
import com.opencart.qa.utils.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Epic100: Design login page Open Cart Application ")
@Story("LoginS 200 : Add login page features with title, user login, url etc")

public class LoginPageTest extends BaseTest {

	
@Description("Checking login Page Title")
@Severity(SeverityLevel.NORMAL)
	@Test
	public void loginPageTitleTest() {   //Here we need to create test blocks..we need to acess the title() of LoginPage
								//to test its funtionalities.Hence we need to create objet of LoginPage class.
								//The object is already created in Base Class whih is parent of LoginPageTest, a child can
								//inherit all the properties of parent,hence we can inherit the object of LoginPage.
								//But sice the object has default access modifer,it can be accessed only within same package
								//Hence we have to either change it to Public or protected.
								//or outside package the child class can access the parent properties
		ChainTestListener.log("Starting loginPageTitleTest");
		Assert.assertEquals(loginPage.getLoginPageTitle(),AppConstants.LOGIN_PAGE_TITLE);
			
	}
	
@Description("Checking login Page URL")
@Severity(SeverityLevel.CRITICAL)
	@Test
	public void loginPageURLTest() {
		Assert.assertTrue(loginPage.getLoginPageURL().contains(AppConstants.LOGIN_PAGE_URL));
	}
	
@Description("Checking forgot password link exist on page ...")
@Severity(SeverityLevel.BLOCKER)	
@Issue("Bug 9001: Forgot passwors link is missing")
	@Test
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@Test(priority=Integer.MAX_VALUE)   //We need the login page to run at the last as te URL, title and forgotpwd should run
									//before...hence we nedd to give the priority last...but if there are n nunmber of cases
									// we cannot speify last as priority is only Integer...hence we use MAX_VALUE
	
	@Description("Checking user is able to login with valid credentials")
	@Severity(SeverityLevel.BLOCKER)
	public void LoginTest() {
		homePage=loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim());  
		ChainTestListener.log("Home page title" +homePage.getHomePageTitle());
																		//change the values as per the properties file       
																		//username and password should be given in properties file	
	Assert.assertEquals(homePage.getHomePageTitle(), AppConstants.HOME_PAGE_TITLE);    //In testing assertion should be there fr all 
																//methods, hence we can try to get the title and 
																//compare actual title with expected title and do a title 
																//Assertion here
	//Change the reference here to homePage which comes from the base class
	}
}
