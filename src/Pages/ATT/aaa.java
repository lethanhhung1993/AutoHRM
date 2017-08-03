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
    String NV="//input[contains(@aria-owns, 'Grade_AttendanceID_taglist Grade_AttendanceID_listbox')]"; 
    String Listvalue="Grade_AttendanceID_listbox";
    String text="Có Trừ Trễ Sớm";
    
    @Test()
    public void timkiem() throws InterruptedException{
        
        System.setProperty("webdriver.chrome.driver",
                "D:/Software/Chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(
                "http://103.63.212.153:6061/#/Hrm_Main_Web/Att_Grade/Index");
        Thread.sleep(3000);
        driver.findElement(By.id("UserName")).sendKeys("admin");
        driver.findElement(By.id("Password")).sendKeys("15278994");
        driver.findElement(By.id("btnCheckLogin")).click();
        Thread.sleep(3000);
//        driver.findElement(By.xpath(NV)).sendKeys("Có Trừ Trễ Sớm");
//        WebElement autoOptions = driver.findElement(By.id("Grade_AttendanceID_listbox"));
//        Thread.sleep(3000);
//        
//        List<WebElement> optionsToSelect = autoOptions.findElements(By.tagName("li"));
//        for (WebElement option : optionsToSelect) {
//            if (option.getText().equals("Có Trừ Trễ Sớm")) {
//                option.click();
//            }
//        }
        selectOptionWithText(NV,Listvalue,text);
    }
    
    public void selectOptionWithText(String FieldId, String ListOfValue, String textToSelect) throws InterruptedException {
        driver.findElement(By.xpath(FieldId)).sendKeys(textToSelect);
        WebElement autoOptions = driver.findElement(By.id(ListOfValue));
        Thread.sleep(2000);
        List<WebElement> optionsToSelect = autoOptions.findElements(By.tagName("li"));
        for(WebElement option : optionsToSelect){
            if(option.getText().equals(textToSelect)) {
                option.click();
                break;
            }
        }
    }
}
