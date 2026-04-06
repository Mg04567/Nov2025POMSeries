package com.opencart.qa.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {

	private WebDriver driver;
	
	public  JavaScriptUtil(WebDriver driver) {
		this.driver=driver;
		}
	
	public void flash(WebElement element) {
		String bgcolour=element.getCssValue("background-color");
		
		
		for (int i = 0; i < 7; i++) {              //Color changes between actual color and green 100 times
			ChangeColor("rbg(0,200,0)", element);	   //This gives green color to the element
			ChangeColor(bgcolour, element);          //This takes the background color of the element
		}
	}

	public void ChangeColor(String color,WebElement element) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);  //argument is the array starting with oth index
		try {
			Thread.sleep(20); 
		} catch (InterruptedException e) {        //each cycle there is a gap of 20 secs
			
		}
	}
	
	public void drawBorder(String color,WebElement element) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].style.border='3px solid red'",element);
	}
	
	public void scrollPageDown() {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript(("window.scrollTo(0,document.body.scrollHeight)"));
	}
	
	public void scrollPageDown(String height) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript(("window.scrollTo(0,'" +height+ " ')"));
	}
	
	public void scrollPageUp() {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript(("window.scrollTo(document.body.scrollHeight,0)"));
	}
	
	public void scrollIntoView(WebElement element) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true);",element);
	}
	
	public void clickElementByJs(WebElement element) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();",element);
	}
	
	
	public String getTitleByJS() {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		return js.executeScript("return document.title").toString();
	}
	
	public String getURLByJS() {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		return js.executeScript("return document.URL").toString();
	}
		
	public void getAlertByJS(String message) throws InterruptedException {
			JavascriptExecutor js=(JavascriptExecutor)driver;
			 js.executeScript("alert('" + message + "')");
			 Thread.sleep(2000);
			 driver.switchTo().alert().accept();
	}
	
	public void refreshBrowserByJS() {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("history.go(0);");
	}
	
	public void navigateToForwardPage() {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("history.go(1);");
	}
	
	public void navigateToBackPage() {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("history.go(-1);");
	}
	public void navigateToSpecifiPage(String pageNum) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("history.go('" +pageNum+ "')");
	}
	
	public String getPageInnerText() {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		return js.executeScript("return document.documentElement.innerText").toString();
}
}