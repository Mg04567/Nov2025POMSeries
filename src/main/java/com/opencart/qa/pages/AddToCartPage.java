package com.opencart.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.opencart.qa.utils.ElementUtil;

public class AddToCartPage {

	private WebDriver driver;
	private ElementUtil elUtil;
	
	public AddToCartPage(WebDriver driver) {
		this.driver=driver;
		elUtil=new ElementUtil(driver);
		}
	
	private final By headers=By.cssSelector("div#content h1");
	
	public String getCartHeader() {
		return elUtil.doElementGetText(headers);
	}
	
	
	
	
}
