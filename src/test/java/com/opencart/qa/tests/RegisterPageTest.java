package com.opencart.qa.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencart.qa.base.BaseTest;
import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.CsvUtil;
import com.opencart.qa.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
		registerPage=loginPage.navigateToRegisterPage();    //We need to call the navigateToRegisterPage() ...from login page to click on register
															//page link and click on next page
		}
	
	@DataProvider
	public Object[][] getUserRegTestData(){
		return new Object[][] {
			{"kartik1", "kumar", "8978798978", "kumr1@123", "yes"},
			{"arun", "r", "8978988978", "arun1@123", "yes"},
			{"priya", "automation", "8990988978", "priya1@123", "yes"}
		};
	}
	
	@DataProvider
	public Object[][] getUserRegExcelTestData(){
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);                    //Method is static so we an call directly using class name
	}
	
	
	@DataProvider
	public Object[][] getUserRegCSVTestData(){								//The data source is CSV here
		return CsvUtil.csvData(AppConstants.REGISTER_SHEET_NAME);
	}
	
	
	
	//@Test(dataProvider="getUserRegTestData")
	//@Test(dataProvider="getUserRegExcelTestData")
	
	@Test(dataProvider="getUserRegCSVTestData")
	public void userRegisterTest(String firstname,String lastname,String telephone,String password,String subsribe ) {
		
		Assert.assertTrue(
		registerPage.userRegistration(firstname, lastname, telephone, password, subsribe));
		
	}
	
	//This page will go to the Base test ...it'll initialse driver go to Login Page and from there go to Register page...click on register
	//link get the register page Object get the reference nd get the values to return true or false
}
