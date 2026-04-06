package com.opencart.qa.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.opencart.qa.base.BaseTest;

public class ProductInfoPageTest extends BaseTest{

	//The Base Test Class opens the browser , initilises the driver and gives the LoginPage Clss Object..but login will 
	//not happen in base class, hence here we need to use @BeforeClass
	
	@BeforeClass
	public void productInfoSetup() {
	//loginPage.doLogin(username, pwd);  Instead of tis we can write the below doLogin method which returns the homePage ref
	//This helps us to login to the page
		homePage=loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim());
	}	
		
	//Now we need to traverse to the ProductInfoPage to accessthe headers..firt we need to enter the value serch button 
	//For this we can access the HomePage doSearch()..which takes the searchKey and reutrns the resultPage refernce 
	//Next we need to access the selectProduct() method from resultsPage to click on the Product and return the ProdutPage ref 
	//Next we need to add the ProductPage ref in BaseTest...for resultsPage.selectProduct() to return ref of productInfoPage
	//Now we can use Assert to compare the getProductHeader() with the expected value
	
	
	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
				{"macbook","MacBook"},
				{"macbook","MacBook Air"},
				{"macbook","MacBook Pro"},
				{"imac","iMac"},
				{"samsung","Samsung SyncMaster 941BW"},
				{"samsung","Samsung Galaxy Tab 10.1"},
				};
	}
	
	
	@Test(dataProvider="getProductData")
	public void productHeaderTest(String searchKey,String productName) {
		resultsPage=homePage.doSearch(searchKey);
		productInfoPage=resultsPage.selectProduct(productName);
		
		Assert.assertEquals(productInfoPage.getProductHeader(), productName);
	}
	
	@DataProvider
	public Object[][] getProductImagesData() {
		return new Object[][] {
				{"macbook","MacBook",5},
				{"macbook","MacBook Air",4},
				{"macbook","MacBook Pro",4},
				{"imac","iMac",3},
				{"samsung","Samsung SyncMaster 941BW",1},
				{"samsung","Samsung Galaxy Tab 10.1",7},
				{"canon","Canon EOS 5D",3}
				};
	}
	
	//Test cases for Images
	@Test(dataProvider="getProductImagesData")
	public void productImagesCountTest(String searchKey,String productName,int imagesCount ) {
		
		resultsPage=homePage.doSearch(searchKey);
		productInfoPage=resultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductImagesCount(),imagesCount);
	}
	
	
	@Test
	public void productInfoTest() {
		resultsPage=homePage.doSearch("macbook");
		productInfoPage=resultsPage.selectProduct("MacBook Pro");
		
		Map<String, String> productInfoDataMap=productInfoPage.getProductInfoData();
		SoftAssert softAssert=new SoftAssert();
		
		Assert.assertEquals(productInfoDataMap.get("productname"), "MacBook Pro");
	//	Assert.assertEquals(productInfoDataMap.get("imagescount"), "5");  //Here we declred as 5 wrong value to check softAssertions
		Assert.assertEquals(productInfoDataMap.get("imagescount"), "4"); 
		Assert.assertEquals(productInfoDataMap.get("Brand"), "Apple");
		Assert.assertEquals(productInfoDataMap.get("Product Code"), "Product 18");
		Assert.assertEquals(productInfoDataMap.get("Reward Points"), "800");
		Assert.assertEquals(productInfoDataMap.get("Availability"), "Out Of Stock");
		
		Assert.assertEquals(productInfoDataMap.get("productPrice"), "$2,000.00");
		Assert.assertEquals(productInfoDataMap.get("extaxPrice"), "$2,000.00");
		softAssert.assertAll();
	}
	}
