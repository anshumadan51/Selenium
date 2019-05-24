package com.Project.Selenium.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class Excel extends IntitializeFramework{

	

	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	public HSSFWorkbook workbook = null;
	public HSSFWorkbook workbook1 = null;
	public HSSFSheet sheet = null;
	public HSSFRow row = null;
	public HSSFCell cell = null;
	public int temprownum = 0;
	

	/**
	 * @author anshulmadan
	 * @Description: Constructor for intiliaze the Excel
	 * @param path   Excel workbook path
	 */
	public Excel(String path) {
		// TODO Auto-generated constructor stub
		try {
			fis = new FileInputStream(path);
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			Report.logMessage("Fail",  e.getMessage());
		}
	}

	/**
	 * @author anshulmadan 
	 * @Description: Get the total used row of a sheet
	 * @param sheetName 	sheet name of workbook
	 * @return It return total number of used row
	 */
	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}

	}

	/**
	 * @author anshulmadan
	 * @Description: Get the total used column of a sheet
	 * @param sheetName	sheet name of workbook
	 * @return It return total number of used column
	 */
	public int getColCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			int number = row.getLastCellNum();
			return number;
		}

	}

	/**
	 * @author anshulmadan
	 * @Description: Get the cell date from a worksheet
	 * @param sheetName 	Sheet name of workbook
	 * @param colName	Column name for which we need data
	 * @param rowNum		Row Number from which we need data
	 * @return It return the string of Cell data of worksheet
	 */
	public String getCellData(String sheetName, String colName, int rowNum) {
		if (rowNum <= 0)
			return "";

		int index = workbook.getSheetIndex(sheetName);
		int col_Num = -1;
		if (index == -1)
			return "";

		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(0);
		for (int i = 0; i < row.getLastCellNum(); i++) {

			if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
				col_Num = i;
				break;
			}
		}

		if (col_Num == -1)
			return "";
		row = sheet.getRow(rowNum);

		if (row == null) {
			return "";
		}
		cell = row.getCell(col_Num, row.RETURN_BLANK_AS_NULL);

		if (cell == null) {
			return "";
		}
		return cell.getStringCellValue();

	}

	/**
	 * @author anshulmadan
	 * @Description: Write the data to the cell of a worksheet
	 * @param sPath		Path of workbook
	 * @param sheetName	Sheet name of workbook
	 * @param colName	Column name for which we need data
	 * @param rowNum		Row Number from which we need data
	 * @param CellValue	Cell data which want to write
	 * @return It return the string of Cell data of worksheet
	 */
	public String writteCellData(String sPath, String sheetName, String colName, int rowNum, String CellValue) {
		sheet = workbook.getSheetAt(0);

		if (rowNum <= 0)
			return "";

		int index = workbook.getSheetIndex(sheetName);
		int col_Num = -1;
		if (index == -1)
			return "";

		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(0);
		for (int i = 0; i < row.getLastCellNum(); i++) {

			if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
				col_Num = i;
				break;
			}
		}

		if (col_Num == -1)
			return "";
		if (temprownum == rowNum) {
			row = sheet.getRow(rowNum);
		} else {
			row = sheet.createRow(rowNum);
		}

		temprownum = rowNum;

		if (row == null) {
			return "";
		}

		cell = row.createCell(col_Num);

		if (cell == null) {
			return "";
		}
		cell.setCellValue(CellValue);

		try {

			fileOut = new FileOutputStream(sPath);
			workbook.write(fileOut);

			fileOut.close();
			return "Print";
		} catch (Exception e) {

		}
		return "Print";
	}
	
	/**
	 * @author anshulmadan
	 * @Description: Get the total test iteration for a test
	 * @param sheetName
	 *            Sheet name of workbook
	 * @param testCase
	 *            test case name 
	 * @return It return the sArray list having number of row where test row is present 
	 */
	public ArrayList<Integer> getTestIteration(String sheetName, String testCase) {
		ArrayList<Integer> testCaseNumber = new ArrayList<Integer>();
		int index = workbook.getSheetIndex(sheetName);
		sheet = workbook.getSheetAt(index);
		int totalRow = sheet.getLastRowNum();
try {
		for (int i = 0; i <= totalRow; i++) {

			row = sheet.getRow(i);
			cell = row.getCell(0);
			if (cell.getStringCellValue().contains(testCase)) {
				testCaseNumber.add(i);
			}
		}
		
	}
catch(Exception ex) {
	System.out.println(ex.getMessage());
}
return testCaseNumber;	
}
	
	/**
	 * @author anshulmadan
	 * @Description: get the ExcelColumn Data 
	 * @param Scenario
	 *            Scenario of Feature file
	 * @param colName
	 *            Column name for which we need data
	 * @param testCase
	 *           test case name
	 * @return It return the hash map having all the test data
	 */
	public String getColData(String testCaseName, String colName) {
		
		String sheetName = null;
		String Columnvalue = "";
		try {
			testCaseName = testCaseName.replaceAll(" ", "");
			sheetName = getExcelProperties(testCaseName);
			ArrayList<Integer> testIteration = getTestIteration(sheetName, testCaseName);
			Columnvalue = getCellData(sheetName, colName, testIteration.get(0));
			return Columnvalue;
		} catch (Exception ex) {
			return Columnvalue;
		}
	}
}
