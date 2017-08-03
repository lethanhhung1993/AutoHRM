package Config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.BeforeClass;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeMethod;

import Config.Constants.TestResults;
import Config.Login;

public class TestBase implements Path {
    protected Config config = new Config(this.getClass().getSimpleName());

    private String login = "http://118.69.172.201/EBM_QC/screen/Dashboard.aspx";

    private String dateStart;

    private String dateFinish;
    
    protected ITestResult testResult;

    private void LogIn() 
    
    {
        config.Log();
        config.LogIn(true);
        config.GetDriver(login);
        config.SetValueByXpath(Login.Xpath.TXT_USERNAME, "admin");
        config.SetValueByXpath(Login.Xpath.TXT_PASSWORD, "123");
        config.ClickByXpath(Login.Xpath.BTN_LOGIN);  
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        config.ClickByXpath(Login.Xpath.DD_ChiNhanh);
        config.ClickByXpath(Login.Xpath.DD_AllChiNhanh);
        config.ClickByXpath(Login.Xpath.DD_CuThe);
        config.ClickByName(Login.Name.BTN_ReFresh);
        config.LogIn(false);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
    }
    
    @BeforeSuite
    public void BeforeSuite(){
        LogIn();
    }
    
    @BeforeClass
    public void BeforeClass() {
        config.className = this.getClass().getSimpleName();
    }

    @AfterSuite
    public void AfterClass() {
        config.Log();
    }

    @AfterSuite
    public void AfterSuite() {
         config.quitbrowser();
    }

    @BeforeMethod
    public void BeforeMethod(ITestResult result) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateStart = sdf.format(new Date());
    }

    @AfterMethod
    public void AfterMethod(ITestResult result){
        String status;
        switch (result.getStatus()) {
        case ITestResult.SUCCESS:
            status = TestResults.PASSED;
            break;
        case ITestResult.FAILURE:
            status = TestResults.FAILED;
            break;
        case ITestResult.SKIP:
            status = TestResults.SKIPPED;
            break;
        default:
            status = "Invalid result";
            break;
        }

        String testcase = this.getClass().getSimpleName();
        String description = result.getMethod().getDescription() == null ? "" : result.getMethod().getDescription();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFinish = sdf.format(new Date());

        ArrayList<Object> data = new ArrayList<Object>();
        data.add(testcase);
        data.add(description);
        data.add(dateStart);
        data.add(dateFinish);
        
        sdf = new SimpleDateFormat("yyyy_MM_dd");
        String fileName = String.format("Report_%s", sdf.format(new Date()));
        String suiteName = result.getTestContext().getCurrentXmlTest().getSuite().getName();

        String pathPicture = "";
        if(status == TestResults.FAILED){
            pathPicture = config.CreatePicture();
        }
        
        Excel.WriteData(fileName, Constants.Excel.TOTALCASE_SHEET_NAME, data, status, pathPicture);
        Excel.WriteData(fileName, suiteName, data, status, pathPicture);
        
        Excel.Summary(fileName, suiteName);
        
        if(status == TestResults.FAILED ||  status == TestResults.SKIPPED){
            LogIn();
        }
        
    }
}