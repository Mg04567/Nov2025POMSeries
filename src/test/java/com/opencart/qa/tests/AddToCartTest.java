package com.opencart.qa.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.opencart.qa.base.BaseTest;

public class AddToCartTest extends BaseTest {

	@BeforeClass
	public void productInfoSetup() {
	//loginPage.doLogin(username, pwd);  Instead of tis we can write the below doLogin method which returns the homePage ref
	//This helps us to login to the page
		homePage=loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim());
	}	
	
	@Test
	public void cartHeaderTest() {
		resultsPage=homePage.doSearch("macbook");
		productInfoPage=resultsPage.selectProduct("MacBook");
		//addToCartPage=productInfoPage.addToCart("MacBook");
		Assert.assertEquals(addToCartPage.getCartHeader(), "MacBook");
	
	}
	
	
}
