package Config;

public class Constants {

    public static class Excel {

        public static final String EXTEDNSION_EXCEL_FILE = ".xlsx";

        public static final String DEFAULT_REPORT_FOLDER = (System.getProperty("user.dir") + "\\Report\\").replace("\\","/");

        public static final String DEFAULT_TEMPLATE_FILE = (System.getProperty("user.dir")  + "\\Report\\Template\\template.xlsx").replace("\\", "/");

        public static final String TEMPLATE_SHEET_NAME = "Template";
        
        public static final String TOTALCASE_SHEET_NAME = "TestCase Details";

        public static final String SUMMARY_SHEET_NAME = "Summary";

        public static final String HEADER_RESULT = "Result";

        public static final int SHEET_NAME_MAX_LENGTH = 25;
        
        public static final int START_ROW = 0;
        
        public static final int SUMMARY_POSITION = 0;
    }

    public static class Template
    {
        public static final int HEADER_ROW = 0;
        public static final int HEADER_COLUMN = 0;

        public static int PASSED_ROW = 1;
        public static final int PASSED_COLUMN = 0;
        
        public static final int FAILED_ROW = 2;
        public static final int FAILED_COLUMN = 0;
        
        public static final int SKIPPED_ROW = 3;
        public static final int SKIPPED_COLUMN = 0;
        
        public static final int NORMAL_ROW = 4;
        public static final int NORMAL_COLUMN = 0;
        
        public static final int HYPERLINK_ROW = 5;
        public static final int HYPERLINK_COLUMN = 0;
    }
    
    public static class SummaryHeader {
        public static final String INDEX = "No.";
        
        public static final String NAME = "Sheet name";
        
        public static final String PASSED = "Total case passed";
        
        public static final String FAILED = "Total case failed";
        
        public static final String SKIPPED = "Total case skipped";
        
        public static final String LINK = "Link to Details";
        
        public static final int SEARCH_COLUMN = 2;
    }
    
    public static class TestHeader {
        public static final String INDEX = "No.";
        
        public static final String TEST_CASE = "Test case";
        
//        public static final String TEST_METHOD = "Test method";
        
        public static final String DESCRIPTION = "Description";
        
        public static final String DATE_START = "Date start";
        
        public static final String DATE_FINISH = "Date finish";
        
        public static final String RESULT = "Result";
        
        public static final String PICTURE = "Picture";
    }

    public static class TestResults {
        public static final String PASSED = "Passed";

        public static final String FAILED = "Failed";

        public static final String SKIPPED = "Skipped";
    }

    public static class CellStyles {
        public static final int HEADER = 0;

        public static final int NORMAL = 1;

        public static final int HYPERLINK = 2;

        public static final int PASSED = 3;

        public static final int FAILED = 4;

        public static final int SKIPPED = 5;

    }

    public static class ActionType {
        public static final int ADD = 0;

        public static final int EDIT = 1;
    }
}
