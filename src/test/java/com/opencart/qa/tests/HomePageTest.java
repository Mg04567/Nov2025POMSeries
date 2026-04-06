package com.opencart.qa.tests;



import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencart.qa.base.BaseTest;
import com.opencart.qa.pages.HomePage;
import com.opencart.qa.utils.AppConstants;

public class HomePageTest extends BaseTest {

	//HomePage homePage;    //The title directly takes from the Base class
				//We need to create test methods for the Title, for that create an object of HomePage which takes the driver
				//Hence change the cisiblility of driver in BaseClass  to protected.
	//To run this method first we need to login to the page first...hence we need to call the Login page reference here
	//and write assertions for homePageTitle
	
	@BeforeClass
	public void homePageSetup() {
		homePage=loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim());
		//homePage=new HomePage(driver); 
										//Here we need to change doLogin() as it returns the homepage we can use the referene
	}
	
	@Test
	public void homePageTitleTest() {
//		homePage=new HomePage(driver);   
//		loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim());
		String homePageActTitle=homePage.getHomePageTitle();
		Assert.assertEquals(homePageActTitle, AppConstants.HOME_PAGE_TITLE);
	}
	
	//Now we need to write another method for the logout Link assertions, for boolen we can use Assert.assertTrue
	@Test
	public void logoutLinkExistTest() {
//		homePage=new HomePage(driver);   
//		loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim());
	//	homePage.islogoutLinkExist();  //tis returns a boolean hence we can use Assert.assertTrue
		Assert.assertTrue(homePage.islogoutLinkExist());
	}
	
	//Next we need to test the headers
	
	@Test
	public void headersTest() {
		List<String> actHeaders=homePage.getHomePageHeaders();
		
		Assert.assertEquals(actHeaders, AppConstants.EXP_HEADERS_LIST);  //We need to create an expected arrayList here, atual array list=actHeaders
															//assert Class gives comparision for 2 collections
	}
	
	
	//Next we have to mention the search method
	//we need to write the list of search elements by using dataprovider
	@DataProvider
	public Object[][] getSearchTestData(){
		return new Object[][] {     //The dataprovide always returns a 2D array with any type of Data, hence Object array   
			{"macbook",3},			//Here we are returning the entire 2D array
			{"imac",1},	
			{"canon",1},
			{"samsung",2},
			{"airtel",0}
		};
	}
	
	@Test(dataProvider="getSearchTestData")
	public void searchTest(String searchKey, int expResultscount) {
		
		resultsPage=homePage.doSearch(searchKey);    //This method is written in Home Page ...which returns the Results Page and points
											//to result Page...The doSearch returns the ResultPage reference..
										//The refernce page variables are always maintained in the BaseTest and the same
										//an be imported here...no need to create object of that class.
	Assert.assertEquals(resultsPage.getSearchResultsCount(), expResultscount);  //Thevlue which we are expectimg will be 3
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
