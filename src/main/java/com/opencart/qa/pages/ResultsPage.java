package com.opencart.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;

public class ResultsPage {
	//1.Initialise driver and element util
	private WebDriver driver;
	private ElementUtil eleutil;
	
	//2.Declare Constructor
	public ResultsPage(WebDriver driver) {
		this.driver=driver;
		eleutil=new ElementUtil(driver);
	}
	
	//3.Private by loators....SRP...locators related to results page only	
		private final By searchResults=By.cssSelector("div.product-thumb");
		
		
	
		public int getSearchResultsCount() {
			
			int resultsCount=eleutil.waitFoAllElementVisible(searchResults, AppConstants.MEDIUM_TIME_OUT).size();
			System.out.println("Total Number of results after search: " + resultsCount);
			return resultsCount;  //For few results even after waiting for few seconds it gives timeout...hence we need to
									//write a try catch for waitFoAllElementVisible and in catch the error to return an empty
									//array List instead of null as int cant hold which will gibe NPE
		}						//If we dont write try cath for waitFoAllElementVisible,since the result is 0 for airtel it
								//gives timeout error...its not able to hold the 0 value as we return List<WebElement>
		
		
		public ProductInfoPage selectProduct(String productName) {   //Here we need to create a method to click on the product and display
														//the product ..since it is a click()  method it returns the next page
			System.out.println("select product name: "  +productName);
				eleutil.doClick(By.linkText(productName));      //Here all the ByLocators have the linkText text nme as the productnme
																//Hence we can use the productName instead of link text as common byloctor
				return new ProductInfoPage(driver);
		}
}
