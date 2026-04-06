package com.opencart.qa.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opencart.qa.factory.DriverFactory;

import io.qameta.allure.Step;



public class ElementUtil {

	private WebDriver driver;       //Here the driver value will be null initially and will give a NPE, hence driver value should be initialised
							//and this driver will be called and assigned with the ChromeDriver Value when we create an objet to call
							//these 2 methods in the main method, So create a onstructor here and assign the driver to construtor.
							//Construtor will be called whhen objet is created and the driver will get initilised automatically.
	private Actions act;
	private JavaScriptUtil jsUtil;
	
	

	public ElementUtil(WebDriver driver) {    //this is the construtor here
		this.driver=driver;
		act=new Actions(driver);              //We can create the Actions class globally and making it private so taht it cant be
											//used outside the class Encapsulation.act is a private layer using it under the public metods
		jsUtil=new JavaScriptUtil(driver);
					
	}
	
	@Step("get the text of the Element using By Locator: {0} " )
	public  String doElementGetText(By locator) {   
		return getElement(locator).getText();
	}
	
	
	@Step("Element is displayed using  By locator {0}" )
	public  Boolean isElementDisplayed(By Locator) {
		try {
			return getElement(Locator).isDisplayed(); //Here we an retun Boolean as isDisplayed() returns a boolean value and fr metod
														//we should mention as return .
		} catch (NoSuchElementException e) {
			System.out.println("Element not found using this locator"  + Locator);
			e.printStackTrace();
			return false;                              //If element is found return true if element is not found it'll not give false
														//it'll give Exception .
		}
	}
	
	@Step("Clicking on element using  By locator {0}" )
	public void doClick(By Locator) {
		getElement(Locator).click();
	}
	
	@Step("Clicking on element using  By locator {0}" )
	public void doClick(By Locator,long timeout) {    //doClick with Timeout, as we are calling getElement() method
		getElement(Locator).click();
	}
	

	public void doClear(By locator) {
		getElement(locator).clear();
		}
	
	@Step("Entering value : {1} using By locator {0}" )
	public void doSendKeys(By locator, String value) { //Here we are creating a method to both find element and perform action
		doClear(locator);
		getElement(locator).sendKeys(value);					//Here we are calling the getElement method long with sendKeys to merge and make easy
	}
	
	@Step("Entering value : {1} using By locator {0}" )
	public void doSendKeys(By locator, String value,long timeout) { 
		doClear(locator);
		getElement(locator).sendKeys(value);
		
	}
	
	public String getElementAttribute(By locator,String name) {
		return getElement(locator).getAttribute(name);
	}
	
	
	@Step("Find element using  By locator {0}" )
	public WebElement getElement(By locator) { 
		WebElement element=driver.findElement(locator);
		if (Boolean.parseBoolean(DriverFactory.highlight)) {   // jsUtil...JavascriptUtil class is added to this package
			jsUtil.flash(element);								//jsUtil.flash() method is called nd element is returned
		}
		return element;
	}
	
	//Get Element overloaded with Timeout Wait
	public WebElement getElement(By locator, long timeout) {
		try {
			return driver.findElement(locator);			//Suppose the webelement is wrong not found It'll will throw ElementNoFound 
														//Exception , hene we can declare the try cath blok to handle Exception
		} catch (NoSuchElementException e) {
			System.out.println("Element not found" +locator);
			e.printStackTrace();							//After printing stak trace...if we wnt to wait for few seonds again a
															//for the element we can use the WebDriverWait
//			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));  //Normal webDriver Wait
//			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return waitForElementVisible(locator, timeout);
			}
	
	}
	
	@Step("Find elements using  By locator {0}" )
	public List<WebElement> getElements(By locator) {     //Utility for multiple elements
		return driver.findElements(locator);
		
	}
	
	public List<String> getElementsTextList(By locator) {   //This metod has to return a list of the text and should not be avoid
		List<WebElement> elementList=getElements(locator);		//So we need to create an empty array list and store the values in the
		List<String> eleTextList=new ArrayList<String>();						//array list and return the arralylist.
															//If we mention return type as String then tis metod can return only
															//one text , not a list of text.List an be only store =d in an array
															//Arraylist has VC=10 and PC=0
		for (WebElement e : elementList) {
			String text=e.getText();
			if (text.length()!=0) {
				eleTextList.add(text);	//This will keep adding the text into the Arraylist
			}
		}
		return eleTextList;
		}
	
	
		
	public int getelementsCount(By locator) {
		return getElements(locator).size();
	}
	
	//****************************SELECT DropDownUtilities******************************
	
	public Select getSelect(By locator) {
		return new Select(getElement(locator));                  //Utility for Selet class object
	}
	
	public  void dodropDownSelectByIndex(By locator, int index) {
		getSelect(locator).selectByIndex(index);
		// Select select=new Select(getElement(locator));
		
	}
	
	public  void dodropDownSelectByValue(By locator, String value) {
		getSelect(locator).selectByValue(value);
	}
	
	public  void dodropDownSelectByVisibleText(By locator, String text) {
		getSelect(locator).selectByValue(text);
	}

	public  int getDropDownOptionsount(By locator) {
		//Select select= new Select(getElement(locator));
		return getSelect(locator).getOptions().size();
		//return select.getOptions().size();
		
	}
	public  List<String> getDropDownOptionsTextList(By locator) {
		
		//Select select= new Select(getElement(locator));  Replace select class objet with getSelect(locator) method
		//List<WebElement> OptionsList=select.getOptions();
		List<WebElement> OptionsList=getSelect(locator).getOptions();
		List<String> textdata=new ArrayList<String>();
		for (WebElement e : OptionsList) {
			String text=e.getText();
			textdata.add(text);
			}
		return textdata;
	}
	
	
	//*******************************Actions Utility*********************************
	
	
	 
	public void doActionClick(By locator) {	//Utility for action class click method
		
		//Actions act=new Actions(driver);
		act.click(getElement(locator)).perform();
		}
	
	public void doActionSendkeys(By locator, String value) {  //Utility for ation class send keys method
		//Actions act=new Actions(driver);
		act.sendKeys(getElement(locator), value).perform();
	}
	
	public void handleMenu(By parentLocator, By childlocator) {
		//Actions act=new Actions(driver);
		act.moveToElement(getElement(parentLocator)).perform();  //addOns is webelement of parent bylocator, so we need to call 
																// the get element method moveToElement needs a webelement only.
		doClick(childlocator);				//Use do click method instead of getElement(childlocator).click();
	
	}
	
	public void multipleMenu(By locator1, By locator2,By locator3, By locator4) throws InterruptedException {
		//Actions act=new Actions(driver);
		doClick(locator1);
		act.moveToElement(getElement(locator2)).perform();
		act.moveToElement(getElement(locator3)).perform();
		doClick(locator4);
	}
	
	public void sendKeysWithPause(By locator,String value, long timeSpan) {
		//Actions act=new Actions(driver);
		char[] val=value.toCharArray();
		for (char e : val) {
			act.sendKeys(getElement(locator), String.valueOf(e)).pause(timeSpan).perform();	
	}
		
	}	
	
	
	
		//*************************Wait Util for Different Methods******************************
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeout
	 * Wait for Elements
	 */
	
	public void waitForElementClickable(By locator,long timeout) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
		 wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	@Step("Wait for the element to be presence: {0} with timeout {1}" )
	public WebElement waitForElementPresence(By locator,long timeout) {
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		}
	
	@Step("Wait for the element : {0} with timeout {1}" )
	public  WebElement waitForElementVisible(By locator,long timeout) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));  //Normal webDriver Wait
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public  WebElement waitForElementVisible(By locator,long timeout,long PollingTime) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.pollingEvery(Duration.ofSeconds(PollingTime))
			.ignoring(NoSuchElementException.class) 	
			.withMessage("-----element not found------" +locator);// webDriver Wait with polling Time
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	//Fluent Wait
	
	public  WebElement waitForElementVisibleWithFluentWait(By locator,long timeout,long PollingTime) {
		Wait<WebDriver> wait= new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(PollingTime))
				.ignoring(NoSuchElementException.class)      //Fluent Wait Methods
				.withMessage("-----element not found------");
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		
	}
	
	/**
	 * WaitFor All Elements
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeout
	 * @return
	 * 
	 */
	
	public  List<WebElement> waitFoAllElementPresence(By locator,long timeout) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}catch (TimeoutException e) {
		return Collections.emptyList(); //to return an empty array list
	}
	
}
	
	
	/**
	 * An expectation for checking that all elements present on the web page that match the locatorare visible. 
	 *	Visibility means that the elements are not only displayed but also have a heightand width that is greater than 0
	 * @param locator
	 * @param timeout
	 * @return
	 */
	
	public  List<WebElement> waitFoAllElementVisible(By locator,long timeout) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		} catch (TimeoutException e) {
			return Collections.emptyList();  //since this method returns List<WebElement>..we cannot return 0 or null,
											//we have to return List of arrays which can be an empty ArrayList
		}
		
	}
	
	
	
	public  String waitForTitleContains(String titlevalue,long timeout ) {
		
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.titleContains(titlevalue));
			return driver.getTitle();
		} catch (TimeoutException e) {
			System.out.println(titlevalue + ": is not found");
			e.printStackTrace();
			return null;				//If the exception is there it'll come inside the catch block , and it must return Something
							//of type String as the method is of type String, hence we can return the default value null
		}
}
		public  String waitForTitleIs(String titlevalue,long timeout ) {
			
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
			try {
				wait.until(ExpectedConditions.titleIs(titlevalue));
				return driver.getTitle();
			} catch (TimeoutException e) {
				System.out.println(titlevalue + ": is not found");
				e.printStackTrace();
				return null;				
			}
}
public  String waitForURLContains(String URLvalue,long timeout ) {
			
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
			try {
				wait.until(ExpectedConditions.urlContains(URLvalue));
				
			} catch (TimeoutException e) {
				System.out.println(URLvalue + ": is not found");
				e.printStackTrace();
								//If the exception is there it'll come inside the catch block , and it must return Something
								//of type String as the method is of type String, hence we can return the default value null
			}
			return driver.getCurrentUrl();
		}
	
public  String waitForURLToBe(String URLvalue,long timeout ) {
			
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeout));
			try {
				wait.until(ExpectedConditions.urlToBe(URLvalue));
				
			} catch (TimeoutException e) {
				System.out.println(URLvalue + ": is not found");
				e.printStackTrace();
								//If the exception is there it'll come inside the catch block , and it must return Something
								//of type String as the method is of type String, hence we can return the default value null
			}
			return driver.getCurrentUrl();
		}
//##############################Alert methods
//Create a private Utility for the WebDriverWait

private Alert waitForAlert(long timeOut) {
	WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	return wait.until(ExpectedConditions.alertIsPresent());
	
}

public String waitForJSAlertAndAcept(long timeOut) {
//    WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
//		Alert alert=wait.until(ExpectedConditions.alertIsPresent());
	Alert alert=waitForAlert(timeOut);
			String text=alert.getText();
			alert.accept();  
			return text;
	}
	
	public  String waitForJSAlertAndDismiss(long timeOut) {
//	     WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
//		Alert alert=wait.until(ExpectedConditions.alertIsPresent());
		Alert alert=waitForAlert(timeOut);
	    String text=alert.getText();
			
			alert.dismiss();  
			return text;
		}
	
	public  String waitForJSPromptAlertAndEnterValue(String value,long timeOut) {
//	     WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(timeOut));
//		Alert alert=wait.until(ExpectedConditions.alertIsPresent());
		Alert alert=waitForAlert(timeOut);
	    String text=alert.getText();
			
			alert.sendKeys(value); 
			alert.accept();
			return text;
		}
	
	public  void WaitForFrameAndSwitchToIt(By frameLocator,long timeout) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));	
	}
	
	public void WaitForFrameAndSwitchToIt(int frameIndex ,long timeout) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));	
	}
	
	public  void WaitForFrameAndSwitchToIt(String FrameIdOrName ,long timeout) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FrameIdOrName));	
	}
	
	public  void WaitForFrameAndSwitchToIt(WebElement FrameElement ,long timeout) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FrameElement));	
	}
}
