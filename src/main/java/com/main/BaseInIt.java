package com.main;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.utilities.ExcelFileReader;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseInIt {
	public static WebDriver driver;
	public static Properties sitedata, elementdata, excelfiledata;
	public static ExcelFileReader ts, ts1, ts2;
	public static Logger logs;
	public static FileInputStream fi;
	public static ExtentSparkReporter htmlReporter;
	public static ExtentReports extent = new ExtentReports();
	public static ExtentTest test, parenttest, childtest;
	public static File f;
	public static ITestResult result;
    public static SoftAssert sa = new SoftAssert();
    
    //Create a method to load properties file and data
    public static void loadproperties() throws IOException {		
        //create SiteData properties files object
        sitedata = new Properties();

        //Creates a FileInputStream object
        fi = new FileInputStream(
                System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator
                        + "resources" + File.separator + "propertiesfiles" + File.separator + "sitedata.properties");
        //call properties file properties.load() method
        sitedata.load(fi);

        //create ExcelData properties files object
        excelfiledata = new Properties();

        //Create A FileInputStream Object for ExcelData File
        fi = new FileInputStream(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
                + File.separator + "resources" + File.separator + "propertiesfiles" + File.separator
                + "excelfiledata.properties");
        //call properties file properties.load() method
        excelfiledata.load(fi);

        //create ElementData properties files object
        elementdata = new Properties();

        //Create A FileInputStream Object for ElementData File
        fi = new FileInputStream(
                System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator
                        + "resources" + File.separator + "propertiesfiles" + File.separator + "elementdata.properties");

        //call properties file properties.load() method
        elementdata.load(fi);

      	
    	htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator + "src" + File.separator
				+ "main" + File.separator + "resources" + File.separator + "reports" + File.separator + sitedata.getProperty("project_name")+".html");
		extent.attachReporter(htmlReporter);
		
        //Create ExcelFileReader Object for TestSuites.xlsx file
        ts = new ExcelFileReader(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
                + File.separator + "resources" + File.separator + "excelfiles" + File.separator + excelfiledata.getProperty("test_suites")+".xlsx");
        
        ts1 = new ExcelFileReader(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
                + File.separator + "resources" + File.separator + "excelfiles" + File.separator + excelfiledata.getProperty("ts1")+".xlsx");
       
        ts2 = new ExcelFileReader(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
                + File.separator + "resources" + File.separator + "excelfiles" + File.separator + excelfiledata.getProperty("ts2")+".xlsx");
    }

    //Create A Start Up Method To Launch Browser
    public static void startUP() throws Exception {
        //Call LoadProperties Method From the Main Class
        BaseInIt.loadproperties();
        
        //Create A String Variable To Get Browser Name From The SiteData Properties File
        String browserVal = sitedata.getProperty("browser_name");
        if (browserVal.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();

        } else if (browserVal.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

        } else if (browserVal.equalsIgnoreCase("ie")) {
            driver = new InternetExplorerDriver();
        }

        // For maximize browser window
        driver.manage().window().maximize();
        driver.get(sitedata.getProperty("weburl"));
    }

    //Create A Method To Check Element Is Present Or Not
    public static WebElement isElementPresent(String elementKey) {
        try {
            if (elementKey.contains("xpath")) {
                return driver.findElement(By.xpath(elementdata.getProperty(elementKey)));
            } else if (elementKey.contains("name")) {
                return driver.findElement(By.name(elementdata.getProperty(elementKey)));
            } else if (elementKey.contains("id")) {
                return driver.findElement(By.id(elementdata.getProperty(elementKey)));
            } else if (elementKey.contains("linkText")) {
                return driver.findElement(By.linkText(elementdata.getProperty(elementKey)));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
