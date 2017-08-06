package Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

	public static ArrayList<HashMap<Integer, Object>> ReadDataAll(String path, String sheetName) {
		ArrayList<HashMap<Integer, Object>> result = new ArrayList<HashMap<Integer, Object>>();
		Workbook workbook;
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(new File(path));
			workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheet(sheetName);
			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				HashMap<Integer, Object> map = new HashMap<>();
				int i = 0;
				for (Cell cell : nextRow) {
					map.put(i++, cell.toString());
				}
				if (!map.isEmpty()) {
					result.add(map);
				}
			}
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static ArrayList<Object> ReadDataByColumn(String path, String sheetName,
            int column) {
        ArrayList<Object> result = new ArrayList<Object>();
        Workbook workbook;
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(new File(path));
            workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                result.add(nextRow.getCell(column));
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
	
	public static ArrayList<HashMap<Integer, Object>> ReadDataByColumns(String path, String sheetName,
			ArrayList<Integer> getColumn) {
		ArrayList<HashMap<Integer, Object>> result = new ArrayList<HashMap<Integer, Object>>();
		Workbook workbook;
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(new File(path));
			workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheet(sheetName);
			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				HashMap<Integer, Object> map = new HashMap<>();
				for (Integer column : getColumn) {
					map.put(column, nextRow.getCell(column));
				}
				if (!map.isEmpty()) {
					result.add(map);
				}
			}
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static ArrayList<HashMap<String, Object>> ReadDataByHeader(String path, String sheetName,
			ArrayList<String> getColumn) {
		ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		Workbook workbook;
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(new File(path));
			workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheet(sheetName);
			Row row = sheet.getRow(0);
			Iterator<Row> iterator = sheet.iterator();
			iterator.next();
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				HashMap<String, Object> map = new HashMap<>();
				for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
					if (getColumn.contains(row.getCell(i).toString())) {
						map.put(row.getCell(i).toString(), nextRow.getCell(i));
					}
				}
				if (!map.isEmpty()) {
					result.add(map);
				}
			}
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void WriteData(String fileName, String sheetName, ArrayList<Object> data, String status,
			String screenShoot) {
		FileInputStream inputStream;
		Workbook workbook;
		Sheet sheet;
		Row row;
		sheetName = sheetName.substring(0, Math.min(sheetName.length(), 25));
		ArrayList<Object> tempData = new ArrayList<Object>();
		tempData.addAll(data);
		int nextLastRow;
		try {
			if (Helper.CheckSheetNameDoesNotExist(fileName, sheetName)) {
				File file = new File(Helper.GetExcelFilePath(fileName));
				if (!file.exists()) {
					workbook = new XSSFWorkbook();
					sheet = workbook.createSheet(sheetName);
					row = sheet.createRow(Constants.Excel.START_ROW);
					RowAction(row, Helper.GetTestHeader(), Constants.CellStyles.HEADER);

				} else {
					inputStream = new FileInputStream(Helper.GetExcelFilePath(fileName));
					workbook = new XSSFWorkbook(inputStream);
					if (Helper.CheckSheetNameDoesNotExist(fileName, sheetName)) {
						sheet = workbook.createSheet(sheetName);
						row = sheet.createRow(Constants.Excel.START_ROW);
						RowAction(row, Helper.GetTestHeader(), Constants.CellStyles.HEADER);
					}
				}
			} else {
				inputStream = new FileInputStream(Helper.GetExcelFilePath(fileName));
				workbook = new XSSFWorkbook(inputStream);
			}

			sheet = workbook.getSheet(sheetName);
			if (sheet.getRow(Constants.Excel.START_ROW).getLastCellNum() < Helper.GetTestHeader().size()) {
				row = sheet.getRow(Constants.Excel.START_ROW);
				RowAction(row, Helper.GetTestHeader(), Constants.ActionType.EDIT, Constants.CellStyles.HEADER);
			}

			nextLastRow = sheet.getLastRowNum() + 1;
			row = sheet.createRow(nextLastRow);
			tempData.add(0, nextLastRow);
			tempData.add(status);
			tempData.add(screenShoot);
			RowAction(row, tempData);

			FileOutputStream fos = new FileOutputStream(Helper.GetExcelFilePath(fileName));
			workbook.write(fos);
			fos.flush();
			fos.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void RowAction(Row Row, ArrayList<Object> Data) {
		switch (Data.get(Data.size() - 2).toString()) {
		case Constants.TestResults.PASSED:
			RowAction(Row, Data, Constants.CellStyles.PASSED);
			break;
		case Constants.TestResults.FAILED:
			RowAction(Row, Data, Constants.CellStyles.FAILED);
			break;
		case Constants.TestResults.SKIPPED:
			RowAction(Row, Data, Constants.CellStyles.SKIPPED);
			break;
		default:
			RowAction(Row, Data, Constants.CellStyles.NORMAL);
			break;
		}
	}

	public static void RowAction(Row Row, ArrayList<Object> Data, int CellStyle) {
		RowAction(Row, Data, CellStyle, Constants.ActionType.ADD);
	}

	public static void RowAction(Row Row, ArrayList<Object> Data, int CellStyle, int ActionType) {
		CellStyle style = GetCellStyle(Row.getSheet().getWorkbook(), CellStyle);
		switch (ActionType) {
		case Constants.ActionType.ADD:
			if (CellStyle != Constants.CellStyles.HYPERLINK) {
				String file = Data.get(Data.size() - 1).toString();
				int size = Data.size() - 1;
				// set data
				for (int column = 0; column < size; column++) {
					Row.getSheet().autoSizeColumn(column);
					Cell cell = Row.createCell(column);
					cell.setCellValue(Data.get(column).toString());
					cell.setCellStyle(style);
				}

				if (CellStyle == Constants.CellStyles.HEADER) {
					Row.getSheet().autoSizeColumn(size);
					Cell cell = Row.createCell(size);
					cell.setCellValue(Data.get(size).toString());
					cell.setCellStyle(style);
				} else {
					Row.getSheet().autoSizeColumn(size);
					CellStyle _style = GetCellStyle(Row.getSheet().getWorkbook(), Constants.CellStyles.HYPERLINK);
					Cell cell = Row.createCell(size);
					cell.setCellValue("");
					if (file != "") {
						CreationHelper createHelper = Row.getSheet().getWorkbook().getCreationHelper();
						Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.URL);
						hyperlink.setAddress(String.format("%s", Data.get(size).toString().replace("\\", "/")));
						cell.setHyperlink(hyperlink);
						cell.setCellValue("Click here");
						cell.setCellStyle(_style);
					} else {
						cell.setCellStyle(style);
					}

				}
			} else {
				CreationHelper createHelper = Row.getSheet().getWorkbook().getCreationHelper();
				CellStyle _styleNormal = GetCellStyle(Row.getSheet().getWorkbook(), Constants.CellStyles.NORMAL);

				for (int column = 0; column < Data.size(); column++) {
					if (column < Data.size() - 1) {
						Row.getSheet().autoSizeColumn(column);
						Cell cell = Row.createCell(column);
						cell.setCellValue(Data.get(column).toString());
						cell.setCellStyle(_styleNormal);
					} else {
						Row.getSheet().autoSizeColumn(column);
						Cell cell = Row.createCell(column);
						cell.setCellValue(Data.get(column).toString());
						Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
						hyperlink.setAddress(String.format("'%s'!A1", Data.get(column)));
						cell.setHyperlink(hyperlink);
						cell.setCellStyle(style);
					}
				}
			}
			break;
		case Constants.ActionType.EDIT:
			if (CellStyle != Constants.CellStyles.HYPERLINK) {
				for (int column = 0; column < Data.size(); column++) {
					Row.getSheet().autoSizeColumn(column);
					Cell cell = Row.getCell(column);
					cell.setCellValue(Data.get(column).toString());
					cell.setCellStyle(style);
				}
			} else {
				CreationHelper createHelper = Row.getSheet().getWorkbook().getCreationHelper();
				CellStyle _styleNormal = GetCellStyle(Row.getSheet().getWorkbook(), Constants.CellStyles.NORMAL);

				for (int column = 0; column < Data.size(); column++) {
					if (column < Data.size() - 1) {
						Row.getSheet().autoSizeColumn(column);
						Cell cell = Row.getCell(column);
						cell.setCellValue(Data.get(column).toString());
						cell.setCellStyle(_styleNormal);
					} else {
						Row.getSheet().autoSizeColumn(column);
						Cell cell = Row.getCell(column);
						cell.setCellValue(Data.get(column).toString());
						Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
						hyperlink.setAddress(String.format("'%s'!A1", Data.get(column)));
						cell.setHyperlink(hyperlink);
						cell.setCellStyle(style);
					}
				}
			}
			break;
		}
	}

	public static void Summary(String fileName, String sheetName) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		ArrayList<String> column = new ArrayList<String>();
		ArrayList<Object> dataSummary = new ArrayList<Object>();
		Workbook workbook;
		Sheet summary;
		Row row;
		int countPassed = 0, countFailed = 0, countSkipped = 0;
		int rowIndex = 0;
		Boolean isAdd = true;
		String fullName = sheetName;
		sheetName = sheetName.substring(0, Math.min(sheetName.length(), Constants.Excel.SHEET_NAME_MAX_LENGTH));
		try {
			FileInputStream inputStream = new FileInputStream(new File(Helper.GetExcelFilePath(fileName)));
			workbook = new XSSFWorkbook(inputStream);

			column.add(Constants.Excel.HEADER_RESULT);
			data = Excel.ReadDataByHeader(Helper.GetExcelFilePath(fileName), sheetName, column);
			for (HashMap<String, Object> result : data) {
				switch (result.get(Constants.Excel.HEADER_RESULT).toString()) {
				case Constants.TestResults.PASSED:
					countPassed++;
					break;
				case Constants.TestResults.FAILED:
					countFailed++;
					break;
				case Constants.TestResults.SKIPPED:
					countSkipped++;
					break;
				}
			}

			if (Helper.CheckSheetNameDoesNotExist(fileName, Constants.Excel.SUMMARY_SHEET_NAME)) {
				summary = workbook.createSheet(Constants.Excel.SUMMARY_SHEET_NAME);
				workbook.setSheetOrder(Constants.Excel.SUMMARY_SHEET_NAME, Constants.Excel.SUMMARY_POSITION);
				row = summary.createRow(Constants.Excel.START_ROW);
				RowAction(row, Helper.GetSummaryHeader(), Constants.CellStyles.HEADER);

				rowIndex = 1;
			} else {
				summary = workbook.getSheet(Constants.Excel.SUMMARY_SHEET_NAME);
				rowIndex = Helper.SearchRowInColumn(fileName, fullName, Constants.SummaryHeader.SEARCH_COLUMN);
				if (rowIndex > 0) {
					isAdd = false;
				} else {
					rowIndex = summary.getLastRowNum() + 1;
				}
			}

			dataSummary.add(rowIndex);
			dataSummary.add(fullName);
			dataSummary.add(countPassed);
			dataSummary.add(countFailed);
			dataSummary.add(countSkipped);
			dataSummary.add(sheetName);

			if (isAdd) {
				row = summary.createRow(rowIndex);
				RowAction(row, dataSummary, Constants.CellStyles.HYPERLINK, Constants.ActionType.ADD);
			} else {
				row = summary.getRow(rowIndex);
				RowAction(row, dataSummary, Constants.CellStyles.HYPERLINK, Constants.ActionType.EDIT);
			}
			FileOutputStream fos = new FileOutputStream(Helper.GetExcelFilePath(fileName));
			workbook.setActiveSheet(0);
			workbook.write(fos);
			fos.flush();
			fos.close();
			inputStream.close();
			workbook.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	public static CellStyle GetCellStyle(Workbook workbook, int cellStyle) {
		CellStyle style;
		CellStyle _style;
		Workbook template;
		style = workbook.createCellStyle();
		try {
			FileInputStream inputStream = new FileInputStream(new File(Constants.Excel.DEFAULT_TEMPLATE_FILE));
			template = new XSSFWorkbook(inputStream);
			switch (cellStyle) {
			case Constants.CellStyles.HEADER:
				_style = template.getSheet(Constants.Excel.TEMPLATE_SHEET_NAME).getRow(Constants.Template.HEADER_ROW)
						.getCell(Constants.Template.HEADER_COLUMN).getCellStyle();
				style.cloneStyleFrom(_style);
				break;
			case Constants.CellStyles.NORMAL:
				_style = template.getSheet(Constants.Excel.TEMPLATE_SHEET_NAME).getRow(Constants.Template.NORMAL_ROW)
						.getCell(Constants.Template.NORMAL_COLUMN).getCellStyle();
				style.cloneStyleFrom(_style);
				break;
			case Constants.CellStyles.HYPERLINK:
				_style = template.getSheet(Constants.Excel.TEMPLATE_SHEET_NAME).getRow(Constants.Template.HYPERLINK_ROW)
						.getCell(Constants.Template.HYPERLINK_COLUMN).getCellStyle();
				style.cloneStyleFrom(_style);
				break;
			case Constants.CellStyles.PASSED:
				_style = template.getSheet(Constants.Excel.TEMPLATE_SHEET_NAME).getRow(Constants.Template.PASSED_ROW)
						.getCell(Constants.Template.PASSED_COLUMN).getCellStyle();
				style.cloneStyleFrom(_style);
				break;
			case Constants.CellStyles.FAILED:
				_style = template.getSheet(Constants.Excel.TEMPLATE_SHEET_NAME).getRow(Constants.Template.FAILED_ROW)
						.getCell(Constants.Template.FAILED_COLUMN).getCellStyle();
				style.cloneStyleFrom(_style);
				break;
			case Constants.CellStyles.SKIPPED:
				_style = template.getSheet(Constants.Excel.TEMPLATE_SHEET_NAME).getRow(Constants.Template.SKIPPED_ROW)
						.getCell(Constants.Template.SKIPPED_COLUMN).getCellStyle();
				style.cloneStyleFrom(_style);
				break;
			}
			template.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return style;
	}

}