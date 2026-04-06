package com.opencart.qa.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	
	
	private static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/OpenCartTestData.xlsx";
	private static Workbook book;
	private static Sheet sheet;
	
	public static Object[][] getTestData(String sheetName) {
		Object data[][] = null;                      //Initially the 2D array points to null

		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);
			book = WorkbookFactory.create(ip);        //WorkbookFactory is a class in APACHE POI which returns the sheet
			sheet = book.getSheet(sheetName.trim());  //Workbook has multiple sheets....from there we use getSheet is a method in JAVA
													//nd pass the sheetname here which returns a sheet reference vriable
			//data = new Object[3][5];                //This value becomes hardcoded ...instead of this we use below
			
			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];//[][]
						
										//It is considered here as sheet.getLastRowNum()=number of last row in the sheet whih is 3
							//sheet.getRow(0).getLastCellNum()= oth row's last cell num will give the nunmber of columns whhic is 5
							//since we have row and column we use 2 for loops to iterate through the excel sheet
							//Outer loop is for rows and inner loop is for columns which is always 5..
							//Now row is set to 0..but 0th row is header...hence while iterating we strt with sheet.getRow(i+1)
							//For i=0 ....data[0][0]=(0+1).0(Converts thr data from excel to toString()),
							//For i=0 inner loop j continues for 5 columns..then i increase by 1...again j loop continues for 5 columns
							//the same continues till i<3.
			
			for(int i=0; i<sheet.getLastRowNum(); i++) {
				for(int j=0; j<sheet.getRow(0).getLastCellNum(); j++) {
					data[i][j] = sheet.getRow(i+1).getCell(j).toString();
				}
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
									
		return data;				//Finally the sheet is returned
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}