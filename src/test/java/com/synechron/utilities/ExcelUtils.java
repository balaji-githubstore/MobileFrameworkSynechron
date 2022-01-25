package com.synechron.utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public static String[][] getSheetIntoArray(String fileDetail,String sheetName) throws IOException {
		
		FileInputStream file = new FileInputStream(fileDetail);
		XSSFWorkbook book = new XSSFWorkbook(file); // open
		XSSFSheet sheet = book.getSheet(sheetName);
		DataFormatter format = new DataFormatter();

		int rowCount = sheet.getPhysicalNumberOfRows();
		int cellCount = sheet.getRow(0).getPhysicalNumberOfCells();

		String[][] main = new String[rowCount - 1][cellCount];

		for (int r = 1; r < rowCount; r++) {
			for (int c = 0; c < cellCount; c++) {
				String cellValue = format.formatCellValue(sheet.getRow(r).getCell(c));
				main[r - 1][c] = cellValue;
			}
		}
		
		return main;
	}

}
