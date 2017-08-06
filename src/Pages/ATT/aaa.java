package Pages.ATT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import Config.Excel;
import Config.Helper;

public class aaa {
    WebDriver driver;

    WebDriverWait wait;

    // String OrgID="OrgStructureTreeViewinput";
    // String FieldSearchID="searchOrgStructureTreeView";
    // String ListOfValue="OrgStructureTreeView";
    // String TextToSelect="- TEST";

    String OrgID = "OrgStructureIDinput";

    String FieldSearchID = "searchOrgStructureID";

    String ListOfValue = "OrgStructureID";

    String TextToSelect = "- TEST";

    @Test()
    public void timkiem() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver",
                "D:/Software/Chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://103.63.212.153:6061/#/Hrm_Main_Web/Hre_Profile/Index");
        // driver.get("http://103.63.212.153:6061/#/Hrm_Main_Web/Hre_Profile/Create/");
        Thread.sleep(3000);
        driver.findElement(By.id("UserName")).sendKeys("admin");
        driver.findElement(By.id("Password")).sendKeys("15278994");
        driver.findElement(By.id("btnCheckLogin")).click();

        Thread.sleep(3000);
        
        ArrayList<Object> result = Excel.ReadDataByColumn(Helper.GetPath("/data/test.xlsx"), "Sheet1", 0);
        String[] data = new String[result.size()];
        WebElement input = driver.findElement(By.id("Hre_Profile__Index__SCodeEmp"));
        for(int i = 0; i < result.size(); i++)
        {
            data[i] = result.get(i).toString();
        }
        input.sendKeys(String.join(",", data));
    }
    // HÀM NHẬP NHIỀU MÃ NHÂN VIÊN

    // HÀM NHẬP NHIỀU MÃ NHÂN VIÊN, SAU ĐÓ CHỌN NHÂN VIÊN
    /*
    driver.findElement(By.id("btnCreate")).click();
    driver.findElement(By.xpath("//div[@id='windowAtt_Grade']/parent::div"))
            .click();

    WebElement input = driver.findElement(By.xpath(
            "//ul[@id='VnrSelectProfileOrOrgStructure_Profile_SelectProfileOrOrgStructureGradeInfo_taglist']/following-sibling::input"));
    ArrayList<HashMap<Integer, Object>> data = Excel
            .ReadDataAll(Helper.GetPath("/data/test.xlsx"), "Sheet1");

    for (HashMap<Integer, Object> row : data) {
        input.sendKeys(row.get(0).toString());
        Thread.sleep(1000);
        List<WebElement> lis = driver.findElement(By.id("VnrSelectProfileOrOrgStructure_Profile_SelectProfileOrOrgStructureGradeInfo_listbox")).findElements(By.tagName("li"));
        lis.get(0).click();
        Thread.sleep(1000); 
        for (int i = 1; i < lis.size(); i++) {
            input.sendKeys(row.get(0).toString());
            Thread.sleep(1000);             
            lis = driver.findElement(By.id("VnrSelectProfileOrOrgStructure_Profile_SelectProfileOrOrgStructureGradeInfo_listbox")).findElements(By.tagName("li"));
            lis.get(i).click();
        }
    }
    */

    // public void SetValueForFiled(String FieldId, String ListOfValue, String
    // textToSelect){
    // try {
    // driver.findElement(By.xpath(FieldId)).sendKeys(textToSelect);
    // WebElement autoOptions = driver.findElement(By.id(ListOfValue));
    // Thread.sleep(2000);
    // List<WebElement> optionsToSelect =
    // autoOptions.findElements(By.tagName("li"));
    // for(WebElement option : optionsToSelect){
    // if(option.getText().equals(textToSelect)) {
    // option.click();
    // break;
    // }
    // }
    // } catch (Exception e) {
    //
    // }
    // }

    // public void SelectOrgNoCheck(String OrgId, String FieldSearchID, String
    // textToSelect) throws InterruptedException {
    //
    // driver.findElement(By.id(OrgId)).click();
    // driver.findElement(By.id(FieldSearchID)).clear();
    // driver.findElement(By.id(FieldSearchID)).sendKeys(textToSelect);
    // Thread.sleep(3000);
    // WebElement autoOptions = driver.findElement(By.id("OrgStructureID"));
    // List<WebElement> checkboxs = autoOptions
    // .findElements(By.xpath(String.format("//span[contains(text(), '%s') and
    // @class='k-in']", textToSelect)));
    // System.out.println("aaaa");
    // for (WebElement checkbox : checkboxs){
    // System.out.println(checkbox.getText());
    // checkbox.click();
    // Thread.sleep(500);
    // break;
    // }
    // JavascriptExecutor executor = (JavascriptExecutor)driver;
    // executor.executeScript("document.getElementById('divOrgStructureID').style.display='none';");
    // }
    //
    // public void SelectOrgWithCheck(String OrgId, String FieldSearchID, String
    // textToSelect) throws InterruptedException {
    //
    // driver.findElement(By.id(OrgId)).click();
    // driver.findElement(By.id(FieldSearchID)).clear();
    // driver.findElement(By.id(FieldSearchID)).sendKeys(textToSelect);
    // Thread.sleep(3000);
    // WebElement autoOptions =
    // driver.findElement(By.id("OrgStructureTreeView"));
    //
    // List<WebElement> checkboxs = autoOptions
    // .findElements(By.xpath(String.format("//span[contains(text(), '%s') and
    // @class='k-in']/preceding-sibling::span/child::input[1]", textToSelect)));
    //
    // for (WebElement checkbox : checkboxs)
    // {
    // if(checkbox.getAttribute("checked") == null)
    // {
    // checkbox.click();
    // Thread.sleep(500);
    // }
    // }
    // JavascriptExecutor executor = (JavascriptExecutor)driver;
    // executor.executeScript("document.getElementById('divOrgStructureTreeView').style.display='none';");
    // }

}
