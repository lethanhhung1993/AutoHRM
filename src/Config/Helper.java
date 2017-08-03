package Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Helper {

    public static Boolean CheckSheetNameDoesNotExist(String fileName, String sheetName) {
        Boolean isExist = true;
        try {
            File file = new File(GetExcelFilePath(fileName));
            if (file.exists()) {
                FileInputStream inputStream = new FileInputStream(file);
                Workbook wbk = new XSSFWorkbook(inputStream);
                for (int i = 0; i < wbk.getNumberOfSheets(); i++) {
                    if (wbk.getSheetName(i).toLowerCase().equals(sheetName.toLowerCase())) {
                        isExist = false;
                        break;
                    }
                }
                wbk.close();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isExist;
    }

    public static int SearchRowInColumn(String fileName, String valueSearch, int column)
    {
        int rowIndex = -1;
        try 
        {
            File file = new File(GetExcelFilePath(fileName));
            if (file.exists()) 
            {
                FileInputStream inputStream = new FileInputStream(file);
                Workbook wbk = new XSSFWorkbook(inputStream);
                Sheet sheet = wbk.getSheet(Constants.Excel.SUMMARY_SHEET_NAME);
                for (Row row: sheet)
                {
                    if (row.getCell(column - 1).toString().toUpperCase().equals(valueSearch.toUpperCase()))
                    {
                        rowIndex = row.getRowNum();
                    }
                }
                
                wbk.close();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowIndex;
    }

    public static String GetExcelFilePath(String fileName)
    {
        return String.format("%s%s%s", Constants.Excel.DEFAULT_REPORT_FOLDER, fileName, Constants.Excel.EXTEDNSION_EXCEL_FILE);
    }

    public static ArrayList<Object> GetTestHeader()
    {
        ArrayList<Object> header = new ArrayList<Object>();
        header.add(Constants.TestHeader.INDEX);
        header.add(Constants.TestHeader.TEST_CASE);
//        header.add(Constants.TestHeader.TEST_METHOD);
        header.add(Constants.TestHeader.DESCRIPTION);
        header.add(Constants.TestHeader.DATE_START);
        header.add(Constants.TestHeader.DATE_FINISH);
        header.add(Constants.TestHeader.RESULT);
        header.add(Constants.TestHeader.PICTURE);
        return header;
    }
    
    public static ArrayList<Object> GetSummaryHeader()
    {
        ArrayList<Object> header = new ArrayList<Object>();
        header.add(Constants.SummaryHeader.INDEX);
        header.add(Constants.SummaryHeader.NAME);
        header.add(Constants.SummaryHeader.PASSED);
        header.add(Constants.SummaryHeader.FAILED);
        header.add(Constants.SummaryHeader.SKIPPED);
        header.add(Constants.SummaryHeader.LINK);
        return header;
    }
    
    public static String GetPath(String path)
    {
        return (System.getProperty("user.dir")).replace("\\","/") + path;
    }
}
