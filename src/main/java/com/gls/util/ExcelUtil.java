package com.gls.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel文件读取
 * @author ding
 * @since 2016年2月18日
 */
public class ExcelUtil {
	/**
	 * 解析Excel格式模板数据
	 * @param excelName
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static List<String[]> analyzeExcelFile(String excelName,
			InputStream inputStream) throws IOException {
		return analyzeExcelFile(excelName, inputStream, 0);
	}

	public static List<String[]> analyzeExcelFile(String excelName,
			InputStream inputStream,int startRow) throws IOException {
		if(startRow < 0){
			return null;
		}
		List<String[]> list = new ArrayList<String[]>();
		String suffix = excelName.substring(excelName.lastIndexOf(".") + 1);
		Workbook wb = null;

		if ("xls".equals(suffix)) {
			wb = new HSSFWorkbook(inputStream);
		} else if ("xlsx".equals(suffix)) {
			wb = new XSSFWorkbook(inputStream);
		} else {
			throw new IOException("====== 文件类型不对，只能上传电子表格文件. ======");
		}

		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(startRow);
		Cell cell = null;
		int cellCount = row.getLastCellNum();

		for (int i = 0; i < cellCount; i++) {
			cell = row.getCell(i);

			if (cell == null) {
				throw new IOException("====== 文件第一行中，各单元格之间不能存在空的单元格. ======");
			}
						
			 if(cell == null ||
			 "".equals(cell.toString())){
			 throw new IOException("====== 文件第一行中，各单元格之间不能存在空的单元格. ======");
			 }
		}

		int rowCount = sheet.getLastRowNum();
		for (int i = 1+startRow; i <= rowCount; i++) {
			String[] values = new String[cellCount];
			row = sheet.getRow(i);
			for (int j = 0; j < cellCount; j++) {
				cell = row.getCell(j);
				String value = "";
				if (cell != null) {
					try {
						value = cell.getStringCellValue();
					} catch (Exception ex) {
						try {
							System.out.println(cell.getNumericCellValue());
							value = String.valueOf(new BigDecimal(cell
									.getNumericCellValue()));
						} catch (Exception ex2) {
							try {
								value = String.valueOf(cell.getDateCellValue());
							} catch (Exception ex3) {
								value = String.valueOf(cell
										.getBooleanCellValue());
							}
						}
					}

					if (value == null) {
						value = "";
					}
				}
				values[j] = value.trim();
			}
			//判断是否存在空行，以空行为数据结尾
			if(values[0]==null||values[0].equals("")){
				break;
			}
			list.add(values);
		}

		return list;
	}
}
