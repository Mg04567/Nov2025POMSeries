package com.opencart.qa.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;
	
	private Properties prop;
	//If headless and incognito is true then we have to initialise these objects
	//Create a constructor here  with one paramter
	public OptionsManager(Properties prop) {  //When someone creates the objet of OptionsManager this onstructor will be lled
												//and prop will be supplied AND PRIVATE prop will be initialise
		this.prop=prop;
		
	}
	
	public ChromeOptions getChromeOptions() {
		co=new ChromeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))) { //Here we are check if the value is true...but the config prop is lways string.
										   //Hence we convert to Boolean
			co.addArguments("--headless");
			
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) { 
			co.addArguments("--incognito");							//Here we can create more options in future
		}
	return co;
		}
	
	public FirefoxOptions getFirefoxOptions() {
		fo=new FirefoxOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))) { 
			fo.addArguments("--headless");
			
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) { 
			fo.addArguments("--incognito");
		}
	return fo;
		}
	
	
	public EdgeOptions getEdgeOptions() {
		eo=new EdgeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))) { 
			eo.addArguments("--headless");
			
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) { 
			eo.addArguments("--InPrivate");
		}
	return eo;
		}
	
	
	
	
	
	
	
	
	
}