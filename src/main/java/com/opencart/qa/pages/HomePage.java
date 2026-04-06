package com.opencart.qa.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;

import net.bytebuddy.build.Plugin.Engine.Source.Element;

public class HomePage {
 
	private WebDriver driver;             //Every page will have  its own driver ,ElementUtil and constructor with 
	private ElementUtil eleutil;		//one parameter
	
	public HomePage(WebDriver driver) {
		this.driver=driver;
		eleutil=new ElementUtil(driver);
		
	}
	
	//Maintain the by Locators private and final
	
	private final By logoutLink=By.linkText("Logout");
	private final By headers=By.cssSelector("div#content h2");
	private final By searchTextField=By.name("search");
	private final By searchIcon=By.xpath("//*[@id=\"search\"]/span/button");
	
	//driver.navigate.refresh();
	
	
	//Create public methods with the private By Locators  ...we need title here, so we cn use the method available in LoginPage
	public String getHomePageTitle() {
		
		String actTitle=eleutil.waitForTitleIs(AppConstants.HOME_PAGE_TITLE,AppConstants.SHORT_TIME_OUT );
		System.out.println("Home Page Title: " +actTitle);  //Change the title to Home_Page_title
		return actTitle;
	}
	
	public boolean islogoutLinkExist() {
		return eleutil.isElementDisplayed(logoutLink);   //This method reurns an exception if element is not found and then 
														//returns false
	}
	
	//Metohds to verify the headers...we can use the waitforAllElementPresent method to apply a wait and display the list
	//of elements.Next we need to get the txt of the elemnts , hence we need to iterate thru the loop,,use for each loop
	
	public List<String> getHomePageHeaders() {
		List<WebElement> headersList=eleutil.waitFoAllElementPresence(headers, AppConstants.SHORT_TIME_OUT);
		
		List<String> headersValueList=new ArrayList<String>();
		for (WebElement e : headersList) {
			String text=e.getText();      //we need to add the text in an array List as there is a list of elements
			headersValueList.add(text);
		}
		return headersValueList;
	}
	
	
	//We need the search metod....the user will enter the value ...hence we need to pass the parmeter as an argumentS
	public ResultsPage doSearch(String searchkey) {
		eleutil.doSendKeys(searchTextField, searchkey, AppConstants.SHORT_TIME_OUT);
//		driver.navigate().refresh();
//		eleutil.doSendKeys(searchIcon, searchkey, AppConstants.MEDIUM_TIME_OUT);
		eleutil.doClick(searchIcon);
		
		return new ResultsPage(driver);
	}
	
	
	
	
	
	
	
	
}
