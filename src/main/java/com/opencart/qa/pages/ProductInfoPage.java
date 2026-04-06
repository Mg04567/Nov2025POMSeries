package com.opencart.qa.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;

public class ProductInfoPage {

	//1.Initialize the driver and element Util
	private WebDriver driver;     //Everypage will have its own driver and element Util
	private ElementUtil elUtil;
	private Map<String, String> productMap;
	
	//2.Pge class constructors
	public ProductInfoPage(WebDriver driver) {   
		this.driver=driver;
		elUtil=new ElementUtil(driver);
		}
	
	
	//3.Private By Locators, we can test headers if is available or not, how mny images available, quantity text, add to cart
	//We mainly focus on funtionality of the page
	private final By headers=By.cssSelector("div#content h1");
	private final By images=By.cssSelector("ul.thumbnails img");
	private final By quantity=By.name("quantity");
	private final By addToCart=By.id("button-cart");
	private final By metadata=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]//li");
	private final By pricedata=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]//li");
	
	
	
	//4.Create methods for the Page, public page actions/methods
	//Method to return the header name...it'll return the header text and with this we can validate it 
	public String getProductHeader() {
		return elUtil.doElementGetText(headers);
	}
	
	public int getProductImagesCount() {
		int imagesCount= elUtil.waitFoAllElementVisible(images, AppConstants.SHORT_TIME_OUT).size();
		System.out.println("Total number of images for product: " + getProductHeader() + ":"  +imagesCount );
		return imagesCount;
	}
	
	
	//We ca add all the 4 metods into a single hashmp nd make the methods as private nd us this method to call the other
	//privte methhods...Encapsulation Concept
	//First initialise the hashmap here and remove the initialisation from the oter 2 methods
	//Then call the header metohd and images method
	public Map<String, String> getProductInfoData() {
		//productMap=new HashMap<String,String>();
		//productMap=new LinkedHashMap<String,String>();
		productMap=new TreeMap<String,String>();
		
		productMap.put("productname", getProductHeader()); //From one method we can directly call anoter method Java concept
	productMap.put("imagescount", String.valueOf(getProductImagesCount()));  //The images method returns an int but put()
															//returns String hence we need to convert int to String
	getProductMetaData();
	getProductPriceData();
	System.out.println("Product Information : \n"   + productMap  );
	return productMap;
	}
	

//Brand: Apple
//Product Code: Product 18
//Reward Points: 800
//Availability: Out Of Stock
//In the below e takes the text from 0th index and split on the basis of colon and oth value is the key ad 1th value will
	//be pair in HashMap
	
	private void getProductMetaData() {         //Make these methods private after getProductInfoData Map method
		List<WebElement> metaDataList=elUtil.getElements(metadata);
	//	productMap=new HashMap<String,String>();   //Initialise the Hashmap is done in productMap()
	for(WebElement e: metaDataList) {
	String metaText=e.getText();			//The above values are split using :..hence we can use the .split()
	String meta[]=metaText.split(":");		//This split() metod returns  String[]...string array ...which can be used for index positioning
	String metakey=meta[0].trim();      //This gives the meta value at 0th poistion which is brand
	String metaValue=meta[1].trim();   //This will give Apple
	productMap.put(metakey, metaValue);
	}
	}
	
//	$2,000.00
//	Ex Tax: $2,000.00
	
	//The above pricelist has only two values..hence we dont need a for loop, we an directly use getText()with split
	//to split the value into tow part and the value before : beomes index[0] and after : [1]
	//First we get the Ex Tax: $500.00...which is get(!)..then use split to split nd after split the vlue after colon 
	//beomes index [1] and we need to trim as there is apace
	
	private void getProductPriceData() {
		List<WebElement> priceList=elUtil.getElements(pricedata);  
		String productPrice=priceList.get(0).getText().trim();
		String productExTaxPrice=priceList.get(1).getText().split(":")[1].trim();
		productMap.put("productPrice", productPrice);      //Here we are declaring our own vlues as the value is not in the forrm
		productMap.put("extaxPrice",productExTaxPrice);		//of key and vlue...Only value is avilble
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//	public AddToCartPage addToCart(String productName) {
//		System.out.println("select product name: "  +productName);
//		//elUtil.doSendKeys(quantity, "2");
//		elUtil.doClick(addToCart,AppConstants.MEDIUM_TIME_OUT);
//		elUtil.doClick(By.linkText("MacBook"), AppConstants.MEDIUM_TIME_OUT);
//		//elUtil.doClick(By.linkText("shopping cart"), AppConstants.MEDIUM_TIME_OUT);
//		return new AddToCartPage(driver);
	
	
}