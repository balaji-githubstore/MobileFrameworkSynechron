package com.synechron.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DemoTest {
	
	@Test
	public void readExcelDynamic() throws IOException {
		FileInputStream file = new FileInputStream("src/test/resources/testdata/KhanAcademyData.xlsx"); // location
		XSSFWorkbook book = new XSSFWorkbook(file); // open
		XSSFSheet sheet = book.getSheet("invalidCredentialTest"); // sheet
		DataFormatter format = new DataFormatter();
		
		int rowCount=sheet.getPhysicalNumberOfRows();
		int cellCount=sheet.getRow(0).getPhysicalNumberOfCells();

		String[][] main=new String[rowCount-1][cellCount];
		
		for(int r=1;r<rowCount;r++)
		{
			for(int c=0;c<cellCount;c++)
			{
				String cellValue = format.formatCellValue(sheet.getRow(r).getCell(c));
//				System.out.println(cellValue);
				main[r-1][c]=cellValue;
			}
		}
		System.out.println();
	}

	@Test
	public void readExcel() throws IOException {
		FileInputStream file = new FileInputStream("src/test/resources/testdata/KhanAcademyData.xlsx"); // location

		XSSFWorkbook book = new XSSFWorkbook(file); // open

		XSSFSheet sheet = book.getSheet("invalidCredentialTest"); // sheet

		DataFormatter format = new DataFormatter();

		String[][] main=new String[2][3];
		
		for(int r=1;r<3;r++)
		{
			for(int c=0;c<3;c++)
			{
				String cellValue = format.formatCellValue(sheet.getRow(r).getCell(c));
//				System.out.println(cellValue);
				main[r-1][c]=cellValue;
			}
		}
	}

	@DataProvider
	public String[][] validData() {
		String[][] main = new String[3][2];
		// i - number of testcase
		// j - number of parameters
		main[0][0] = "john";
		main[0][1] = "john123";

		main[1][0] = "king";
		main[1][1] = "king123";

		main[2][0] = "mark";
		main[2][1] = "mark123";

		return main;
	}

	@Test(dataProvider = "validData")
	public void validTest(String username, String password) {
		System.out.println("valid " + username + password);
	}

}
