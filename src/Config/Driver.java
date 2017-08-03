package Config;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class Driver {

    static WebDriver driver;

    public void SetValueByXPath(String xpath, Object value) {
        driver.findElement(By.xpath(xpath)).sendKeys(String.valueOf(value));
    }

    public void SelectValueByXPath(String xpath, Object value) {
        driver.findElement(By.xpath(xpath)).sendKeys(String.valueOf(value));
    }

    public void SetValueById(String id, Object value) {
        if (CheckElementExistById(id)) {
            driver.findElement(By.id(id)).sendKeys(String.valueOf(value));
        } else {
            // ghi log
        }

    }

    public void SetValueByClassName(String className, Object value) {
        if (CheckElementExistByClassName(className)) {
            driver.findElement(By.className(className))
                    .sendKeys(String.valueOf(value));
        } else {

        }
    }

    public void ClickById(String id) {
        if (CheckElementExistById(id)) {
            driver.findElement(By.id(id)).click();
        } else {

        }
    }

    public void ClickByClassName(String className) {
        if (CheckElementExistByClassName(className)) {
            driver.findElement(By.className(className)).click();
        } else {

        }
    }

    public void ClickByXpath(String xpath) {
        if (CheckElementExistByXpath(xpath)) {
            driver.findElement(By.xpath(xpath)).click();
        } else {

        }
    }

    public void ClickByLink(String link) {
        if (CheckElementExistByLink(link)) {
            driver.findElement(By.linkText(link)).click();
        } else {

        }
    }

    public void Switch()
    {
        driver.get(driver.getCurrentUrl());
    }
    
    private Boolean CheckElementExistById(String id) {
        Boolean check = false;
        try {
            driver.findElement(By.id(id));
            check = true;
        } catch (NoSuchElementException ex) {
        }
        return check;
    }

    private Boolean CheckElementExistByClassName(String className) {
        Boolean check = false;
        try {
            driver.findElement(By.className(className));
            check = true;
        } catch (NoSuchElementException ex) {
        }
        return check;
    }

    private Boolean CheckElementExistByXpath(String xpath) {
        Boolean check = false;
        try {
            driver.findElement(By.xpath(xpath));
            check = true;
        } catch (NoSuchElementException ex) {
        }
        return check;
    }

    private Boolean CheckElementExistByLink(String link) {
        Boolean check = false;
        try {
            driver.findElement(By.linkText(link));
            check = true;
        } catch (NoSuchElementException ex) {
        }
        return check;
    }
}
