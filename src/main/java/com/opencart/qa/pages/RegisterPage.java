package com.opencart.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;
import com.opencart.qa.utils.StringUtil;

public class RegisterPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	private final By firstName=By.id("input-firstname");
	private final By lastName=By.id("input-lastname");
	private final By email=By.id("input-email");
	private final By telephone=By.id("input-telephone");
	
	private final By password=By.id("input-password");
	private final By confirmPassword=By.id("input-confirm");
	private final By subsribeYes=By.xpath("(//label[@class='radio-inline'])[position()=1]/input[@type='radio']");
	
	private final By subsribeNo=By.xpath("//label[@class='radio-inline'][position()=2]//input[@type='radio']");
	
	private final By agreeCheckBox=By.name("agree");
	private final By continueButton=By.xpath("//input[@value='Continue' and @type='submit']");
	private final By successMessg=By.cssSelector("div#content h1");
	
	private final By logoutLink=By.linkText("Logout");
	private final By registerLink=By.linkText("Register");
	
	public RegisterPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
		
	}
	
	//Method to geerate rndom Email ID same way we can use for generating rndom last name first nme
//	public String getRandomEmailId() {
//		return "auto"+System.currentTimeMillis()+"nal.com";
//	}
//	
	
	public boolean userRegistration(String firstname,String lastname,String telephone,String password,
			String subsribe) {
		eleUtil.waitForElementVisible(this.firstName, AppConstants.SHORT_TIME_OUT).sendKeys(firstname);
		eleUtil.doSendKeys(this.lastName, lastname);
		eleUtil.doSendKeys(this.email, StringUtil.getRandomEmailId());
		//eleUtil.doSendKeys(locator, value);   If we dont want this keyword we can give a different locator name nd value nme here
		eleUtil.doSendKeys(this.telephone,telephone);
		eleUtil.doSendKeys(this.password,password);
		eleUtil.doSendKeys(this.confirmPassword,password);
		
		if (subsribe.equalsIgnoreCase("yes")){
			eleUtil.doClick(subsribeYes);
		}
		else {
			eleUtil.doClick(subsribeNo);
		}
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueButton);
		
		if (eleUtil.waitForElementVisible(successMessg, AppConstants.MEDIUM_TIME_OUT).getText().contains(AppConstants.REGISTER_SUCCESS_MESSG)) {
			
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;
		}
		return false;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
