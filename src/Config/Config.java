package Config;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class Config {
	private static WebDriver driver;
	private static Boolean Login = false;
	private static String url;
	public String className;

	public Config(String className) {
		this.className = className;
		Login = false;
	}

	public void LogIn(Boolean value) {
		Login = value;
	}

	public void GetDriver(String url) {
		if (driver == null) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			System.setProperty("webdriver.chrome.driver",
					Helper.GetPath("/Driver/chromedriver.exe"));
			driver = new ChromeDriver(options);
			// driver.manage().window().maximize();
			// driver = new FirefoxDriver();
		}
		driver.get(url);
		Config.url = url;
		WriteLog(String.format("\nURL: [%s]\n", Config.url));
	}

	public void SetValueByID(String id, Object value) {
		verifyPageLoaded(id);
		driver.findElement(By.id(id)).clear();
		driver.findElement(By.id(id)).sendKeys(String.valueOf(value));
		if (!driver.getCurrentUrl().toLowerCase().equals(Config.url.toLowerCase())) {
			Config.url = driver.getCurrentUrl();
			WriteLog(String.format("\nURL: [%s]\n", Config.url));
		}
		WriteLog(String.format("\tSet Value By Xpath: [%s] = [%s]", id, value));
	}
	
	public void SetValueByXpath(String Xpath, Object value) {
        verifyPageLoaded(Xpath);
        driver.findElement(By.xpath(Xpath)).clear();
        driver.findElement(By.xpath(Xpath)).sendKeys(String.valueOf(value));
        if (!driver.getCurrentUrl().toLowerCase().equals(Config.url.toLowerCase())) {
            Config.url = driver.getCurrentUrl();
            WriteLog(String.format("\nURL: [%s]\n", Config.url));
        }
        WriteLog(String.format("\tSet Value By Xpath: [%s] = [%s]", Xpath, value));
    }
	
	public void SetAndSelectValueByXpath(String Xpath, String value){
	    driver.findElement(By.xpath(Xpath)).clear();
        driver.findElement(By.xpath(Xpath)).sendKeys(value);
        Select select = new Select(driver.findElement(By.xpath(Xpath)));
        select.selectByVisibleText(value);
	}


	public void ClearValueByID(String id) {
		verifyPageLoaded(id);
		driver.findElement(By.id(id)).clear();
	}

	public void ClickById(String id) {
		driver.findElement(By.id(id)).click();
		if (!driver.getCurrentUrl().toLowerCase().equals(Config.url.toLowerCase())) {
			Config.url = driver.getCurrentUrl();
			WriteLog(String.format("\nURL: [%s]\n", Config.url));
		}
		WriteLog(String.format("\tClick By ID: [%s]", id));
		// CreatePicture();
	}

	public void ClickByXpath(String xpath) {
		try {
			driver.findElement(By.xpath(xpath)).click();
			Thread.sleep(1000);
			if (!driver.getCurrentUrl().toLowerCase().equals(Config.url.toLowerCase())) {
				Config.url = driver.getCurrentUrl();
				WriteLog(String.format("\nURL: [%s]\n", Config.url));
			}
			WriteLog(String.format("\tClick By XPath: [%s]", xpath));
			// CreatePicture();
		} catch (Exception e) {

		}

	}

	public void ClickAndWaitByXpath(String xpath) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).click();
		} catch (Exception e) {

		}

	}

	public void ClickByName(String name) {
		driver.findElement(By.name(name)).click();
		if (!driver.getCurrentUrl().toLowerCase().equals(Config.url.toLowerCase())) {
			Config.url = driver.getCurrentUrl();
			WriteLog(String.format("\nURL: [%s]\n", Config.url));
		}
		WriteLog(String.format("\tClick By ID: [%s]", name));
	}

	public void SelectByXpath(String id, String value) {
		verifyPageLoaded(id);
		Select select = new Select(driver.findElement(By.xpath(id)));
		select.selectByVisibleText(value);
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}
		if (!driver.getCurrentUrl().toLowerCase().equals(Config.url.toLowerCase())) {
			Config.url = driver.getCurrentUrl();
			WriteLog(String.format("\nURL: [%s]\n", Config.url));
		}
		WriteLog(String.format("\tSelect By XPath: [%s]", id));
	}

	public void Switch() {
		driver.navigate().to(driver.getCurrentUrl());
		if (!driver.getCurrentUrl().toLowerCase().equals(Config.url.toLowerCase())) {
			Config.url = driver.getCurrentUrl();
			WriteLog(String.format("\nReload URL: [%s]\n", Config.url));
		}
	}

	public void quitbrowser() {
		driver.quit();
	}

	public void SwitchToFrame(String name) {
		verifyPageLoadedByName(name);
		driver.switchTo().frame(driver.findElement(By.name(name)));
	}

	public void SearchByXpath(String id, String value, String button) {
		verifyPageLoaded(id);
		SetValueByXpath(id, value);
		ClickByXpath(button);
	}

	public String getTextByXpath(String xpath) {
		verifyPageLoadedXpath(xpath);
		WebElement elm = driver.findElement(By.xpath(xpath));
		WriteLog(String.format("\tGet By Xpath: [%s]", elm.getText()));
		return elm.getText();

	}

	public String getAttributeByXpath(String id, String value) {
		WebElement elm = driver.findElement(By.xpath(id));
		WriteLog(String.format("\tGet By Attribute: [%s]", elm.getAttribute(value)));
		return elm.getAttribute(value);

	}

	public void Navigate(String url) {
		driver.navigate().to(url);
		Config.url = url;
		WriteLog(String.format("\nURL: [%s]\n", Config.url));
	}

	public void StartTest(String methodName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String content = sdf.format(new Date());
		WriteLog("");
		WriteLog(String.format("Start New Test for method [%s] - Date: [%s]\n", methodName, content));
	}

	public void EndTest() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String content = sdf.format(new Date());
		WriteLog("\nEnd Test: " + content);
		WriteLog("");
	}

	public void verifyPageLoaded(String id) {
		WebElement element = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.elementToBeClickable(By.xpath(id)));
	}

	public void verifyPageLoadedXpath(String xpath) {
		WebElement element = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}

	public void verifyPageLoadedByName(String name) {
		WebElement element = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.elementToBeClickable(By.name(name)));
	}

	public void Log() {
		WriteLog("\n=======================================================================================");
		WriteLog("=======================================================================================\n");
	}

	private void WriteLog(String content) {
		Logger logger = Logger.getLogger(className);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
		String formatFileName = sdf.format(new Date());
		String folder = className;

		File file = new File(".\\Log\\" + folder);
		if (!file.exists()) {
			file.mkdirs();
		}

		Properties properties = new Properties();
		properties.setProperty("log4j.rootLogger", "INFO,MyFile");
		properties.setProperty("log4j.rootCategory", "INFO");
		properties.setProperty("log4j.appender.MyFile", "org.apache.log4j.RollingFileAppender");
		properties.setProperty("log4j.appender.MyFile.File", ".\\Log\\" + folder + "\\" + formatFileName + ".log");
		properties.setProperty("log4j.appender.MyFile.MaxFileSize", "100KB");
		properties.setProperty("log4j.appender.MyFile.MaxBackupIndex", "1");
		properties.setProperty("log4j.appender.MyFile.layout", "org.apache.log4j.PatternLayout");
		properties.setProperty("log4j.appender.MyFile.layout.ConversionPattern", "%m%n");
		PropertyConfigurator.configure(properties);
		if (!Login) {
			logger.info(content);
		}
	}

	public String CreatePicture() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		File file = new File(".\\Pictures\\" + dateFormat.format(date));
		String name = className;
		String screenshoot = (System.getProperty("user.dir") + "\\Pictures\\" + dateFormat.format(date) + "\\" + name
				+ ".png").replaceAll(" ", "%20");
		if (!file.exists()) {
			file.mkdirs();
		}
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(screenshoot));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return screenshoot;
	}
}