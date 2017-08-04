package Pages.ATT;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class aaa {
    WebDriver driver;

    WebDriverWait wait;

    String OrgID="OrgStructureTreeViewinput";
    String FieldSearchID="searchOrgStructureTreeView";
    String ListOfValue="OrgStructureTreeView";
    String TextToSelect="- ACCOUNTING";
    

    @Test()
    public void timkiem() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver",
                "D:/Software/Chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://103.63.212.153:6061/#/Hrm_Main_Web/Att_Grade/Index");
        Thread.sleep(3000);
        driver.findElement(By.id("UserName")).sendKeys("admin");
        driver.findElement(By.id("Password")).sendKeys("15278994");
        driver.findElement(By.id("btnCheckLogin")).click();
        Thread.sleep(3000);
        // driver.findElement(By.xpath(NV)).sendKeys("Có Trừ Trễ Sớm");
        // WebElement autoOptions =
        // driver.findElement(By.id("Grade_AttendanceID_listbox"));
        // Thread.sleep(3000);
        //
        // List<WebElement> optionsToSelect =
        // autoOptions.findElements(By.tagName("li"));
        // for (WebElement option : optionsToSelect) {
        // if (option.getText().equals("Có Trừ Trễ Sớm")) {
        // option.click();
        // }
        // }
        // selectOptionWithText(NV,Listvalue,text);
        SelectOrgWithCheck(OrgID,FieldSearchID,ListOfValue,TextToSelect);


    }

    public void SelectOrgWithCheck(String OrgId, String FieldSearchID, String ListOfValue, String textToSelect) throws InterruptedException {
       
            driver.findElement(By.id(OrgId)).click();
            driver.findElement(By.id(FieldSearchID)).clear();
            driver.findElement(By.id(FieldSearchID)).sendKeys(textToSelect);
            Thread.sleep(3000);
            WebElement autoOptions = driver.findElement(By.id(ListOfValue));
            
            List<WebElement> optionsToSelect = autoOptions
                    .findElements(By.xpath("//li[@role='treeitem']"));
            System.out.println("aaaaaaaa");
            
            for (WebElement option : optionsToSelect) {
                System.out.println(option.getText());
                if (option.getText().equals(textToSelect)) {
                    WebElement check= driver.findElement(By.xpath("//span[contains(@class,'k-checkbox-wrapper')]"));
                    check.click();
                    break;
                }
            }
        
    }
    // public void selectOptionWithText(String FieldId, String ListOfValue,
    // String textToSelect) throws InterruptedException {
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
    // }
}
