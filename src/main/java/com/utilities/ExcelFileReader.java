 package com.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileReader {

	XSSFWorkbook wb;
	XSSFSheet sheet;
	File file;
	String filePath;
	FileInputStream fi;

	public ExcelFileReader(String filePath) {

		this.filePath = filePath;

		try {

			file = new File(filePath);
			fi = new FileInputStream(file);
			wb = new XSSFWorkbook(fi);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int totalRow(String sheetName) {

		sheet = wb.getSheet(sheetName);

		int rows = sheet.getLastRowNum() + 1;
		return rows;

	}

	public int totalColumn(String sheetName) {

		sheet = wb.getSheet(sheetName);
		int cols = sheet.getRow(0).getLastCellNum();
		// cols = cols - 1; //Edited by Tanmay
		// cols = cols + 1;

		return cols;
	}

	public String getData(String sheetName, int rowNum, int columnNum) {

		XSSFCell cell = wb.getSheet(sheetName).getRow(rowNum).getCell(columnNum);

		if (cell != null) {

			if (cell.getCellType().equals(CellType.NUMERIC)) {
				int i = (int) wb.getSheet(sheetName).getRow(rowNum).getCell(columnNum).getNumericCellValue();
				String strCellValue = String.valueOf(i);
				return strCellValue;
			} else if (cell.getCellType() == CellType.STRING) {
				String data = wb.getSheet(sheetName).getRow(rowNum).getCell(columnNum).getStringCellValue();
				return data;
			}

		}

		return null;

	}

	public void setData(String sheetName, int rowNum, int columnNum, String data) {

		System.out.println(sheetName);
		System.out.println(rowNum);
		System.out.println(columnNum);
		System.out.println(data);

		try {

			file = new File(filePath);
			fi = new FileInputStream(file);
			wb = new XSSFWorkbook(fi);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(filePath);
		wb.getSheet(sheetName).getRow(rowNum).createCell(columnNum).setCellValue(data);

		try {
			FileOutputStream fo = new FileOutputStream(new File(filePath));

			wb.write(fo);
			wb.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
