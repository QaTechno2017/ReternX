package com.utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

import org.apache.http.util.TextUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import com.main.BaseInIt;
import com.asprise.ocr.Ocr;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class MyMethods extends BaseInIt {

	public static void faLogin() {
		driver.get(sitedata.getProperty("weburl"));

		isElementPresent("email_xpath").sendKeys(sitedata.getProperty("fa_email"));
		isElementPresent("password_xpath").sendKeys(sitedata.getProperty("fa_password"));
		isElementPresent("loginbtn_xpath").click();

	}

	public static void ffLogin() {
		driver.get(sitedata.getProperty("weburl"));

		isElementPresent("email_xpath").sendKeys(sitedata.getProperty("ff_email"));
		isElementPresent("password_xpath").sendKeys(sitedata.getProperty("ff_password"));
		isElementPresent("loginbtn_xpath").click();

	}

	
	public static void Logout() {
		MyMethods.click("logout_btn_xpath");
		MyMethods.click("logout_xpath");
	}

	public static String getdata(ExcelFileReader data, String sheetName) {
		Object[][] getestdata = (MyMethods.getTestData(data, sheetName));
		StringBuilder sb = new StringBuilder();
		for (Object[] row : getestdata) {
			sb.append(Arrays.toString(row));
		}
		String result = sb.toString();
		return result;
	}

	public static void waitForElement(String elementXpath) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementXpath)));
	}

	public static void waitForElementinvisible(String elementXpath) {
		WebDriverWait wait = new WebDriverWait(driver, 500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(elementdata.getProperty(elementXpath))));
	}

	public static void checkBorkenPage() throws Exception {
		String linkUrl = driver.getCurrentUrl();
		URL url = new URL(linkUrl);

		HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();

		httpURLConnect.setConnectTimeout(3000);

		httpURLConnect.connect();
		test = test.createNode("Check exception warning or broken URL");
		if (httpURLConnect.getResponseCode() == 200) {
			test.log(Status.PASS, "Response Code: " + httpURLConnect.getResponseCode());
		} else {
			test.log(Status.WARNING, "Response Code: " + httpURLConnect.getResponseCode());
		}
	}

	public static void checkInputNull(String fieldName, String input, String element_key) {
		if (!TextUtils.isEmpty(input)) {
			isElementPresent(element_key).sendKeys(input);
		} else {
			test.log(Status.WARNING, fieldName + " is blank.");
		}
	}

	public static void sendKeys(String fieldName, String element, String input) {
		if (!TextUtils.isEmpty(input)) {
			isElementPresent(element).sendKeys(input);
		} else {
			test.log(Status.INFO, fieldName + " is blank.");
		}
	}

	public static void selectvalue(String fieldname, String element, String tagname, String input) {
		if (!TextUtils.isEmpty(input)) {
			List<WebElement> elementlist = isElementPresent(element).findElements(By.tagName(tagname));
			for (int i = 0; i < elementlist.size(); i++) {
				if (elementlist.get(i).getText().contains(input)||elementlist.get(i).getText().equalsIgnoreCase(input)) {
					elementlist.get(i).click();
					break;
				} else if (i == elementlist.size()) {
					test.log(Status.WARNING, fieldname + "  is not in list.");
				}
			}
		}
	}

	public static void goTo(String tabname, WebElement element, String option) {
		List<WebElement> elementList = element.findElements(By.tagName(option));
		for (int i = 0; i < elementList.size(); i++) {
			String tabn = elementList.get(i).getText();
			if (elementList.get(i).getText().contains(tabname)) {
				elementList.get(i).click();
				break;
			}
		}
	}

	public static boolean isNumber(String s) {
		for (int i = 0; i < s.length(); i++)
			if (Character.isDigit(s.charAt(i)) == false)
				return false;

		return true;
	}

	public static void click(String element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", isElementPresent(element));
	}

	public static void delete() {
		isElementPresent("delete_xpath").click();
		isElementPresent("delete_yes_xpath").click();
	}

	public static void select(String fieldName, String element_key, String value) {
		if (!TextUtils.isEmpty(value)) {
			Select option = new Select(isElementPresent(element_key));
			option.selectByVisibleText(value);
		} else {
			test.log(Status.INFO, fieldName + " is blank.");
		}
	}

	public static boolean checkTestSuiteExecution(ExcelFileReader data, String sheetName, String testSuiteID) {

		int rows = data.totalRow(sheetName);

		for (int row = 1; row < rows; row++) {

			String testSuiteName = data.getData(sheetName, row, 0);

			if (testSuiteName.equalsIgnoreCase(testSuiteID)) {

				String exeMode = data.getData(sheetName, row, 2);

				if (exeMode.equalsIgnoreCase("y"))

					return true;
				else
					return false;
			}

		}
		return false;

	}

	public static boolean checkTestCaseExecution(ExcelFileReader data, String sheetName, String testCaseID) {

		int rows = data.totalRow(sheetName);

		for (int row = 1; row < rows; row++) {

			String testCaseName = data.getData(sheetName, row, 0);

			if (testCaseName.equalsIgnoreCase(testCaseID)) {

				String exeMode = data.getData(sheetName, row, 2);

				if (exeMode.equalsIgnoreCase("y"))

					return true;
				else
					return false;
			}

		}
		return false;

	}

	public static Object[][] getTestData(ExcelFileReader data, String sheetName) {

		int cols = data.totalColumn(sheetName);
		int rows = data.totalRow(sheetName);

		Object myData[][] = new Object[rows - 1][cols];

		for (int row = 1; row < rows; row++) {

			for (int col = 0; col < cols; col++) {

				myData[row - 1][col] = data.getData(sheetName, row, col);
			}
		}

		return myData;
	}

	public static String takeScreenShot() throws Exception {
		Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(driver);
		final BufferedImage image = fpScreenshot.getImage();
		String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator
				+ "resources" + File.separator + "screenshots" + File.separator + System.currentTimeMillis() + ".png";
		ImageIO.write(image, "PNG", new File(path));
		return path;
	}

	public static void checkvalidation(String fieldname, String err_key, String inputs) throws Exception {
		if (fieldname.contains("email")) {
			if (TextUtils.isEmpty(inputs)) {
				test.info(fieldname + " is blank.");
				if (isElementPresent(err_key) != null) {

					if (isElementPresent(err_key).getText().equals("Please enter email.")
							|| isElementPresent(err_key).getText().equals("This field is required.")) {
						childtest.pass("Email validation message is appeared.");
					} else {
						childtest.fail("[Actual]" + isElementPresent(err_key).getText()
								+ "[Expected]This field is required.");
					}
				} else {
					childtest.warning(fieldname + " validation message does not appear.");
				}

			} else if (inputs.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
				test.pass(fieldname + " is filled correctly.");
			} else {
				test.warning(fieldname + " Have wrong inputs:- " + inputs);
				childtest.warning(fieldname + " should have valid inputs.");
				if (isElementPresent(err_key) != null) {

					if (isElementPresent(err_key).getText().equals("This field is required.")) {
						childtest.pass(fieldname + " validation message is appeared.");
					} else {
						childtest.fail("[Actual]" + isElementPresent(err_key).getText()
								+ "[Expected]This field is required. Or Please enter your valid " + fieldname + ".");
					}
				} else {
					childtest.warning(fieldname + " validation message does not appear.");
				}

			}
		} else {
			if (TextUtils.isEmpty(inputs)) {
				test.info(fieldname + " is blank.");
				if (isElementPresent(err_key) != null) {

					if (isElementPresent(err_key).getText().equals("Please enter " + fieldname + ".")
							|| isElementPresent(err_key).getText().equals("This field is required.")) {
						childtest.pass(fieldname + " validation message is appeared.");
					} else {
						childtest.fail("[Actual]" + isElementPresent(err_key).getText()
								+ "[Expected]This field is required. or Please enter " + fieldname + ".");
					}
				} else {
					childtest.warning(fieldname + " validation message does not appear.");
				}

			} else {
				test.log(Status.PASS, fieldname + " is filled correctly.");
			}
		}
	}

	/*
	 * public static void checkvalidation(String fieldname, String element_key,
	 * String inputs) throws Exception { if (fieldname.contains("email")) { if
	 * (TextUtils.isEmpty(inputs)) { test.log(Status.INFO, fieldname +
	 * " is blank."); if (isElementPresent(element_key) != null) {
	 * sa.assertEquals(isElementPresent(element_key).getText(), "Please enter " +
	 * fieldname, fieldname + " validation message does not match."); } else {
	 * childtest.warning(fieldname + " validation message does not appear."); }
	 * 
	 * } else if (inputs.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
	 * test.log(Status.PASS, fieldname + " is filled correctly."); } else {
	 * test.log(Status.INFO, fieldname + " Have wrong inputs:- " + inputs);
	 * test.log(Status.WARNING, fieldname + " should have valid email format."); if
	 * (isElementPresent(element_key) != null) {
	 * sa.assertEquals(isElementPresent(element_key).getText(),
	 * "Please enter valid " + fieldname + ".", fieldname +
	 * " validation message does not match."); } else { childtest.warning(fieldname
	 * + " validation message does not appear."); } }
	 * 
	 * } else { if (TextUtils.isEmpty(inputs)) { test.log(Status.INFO, fieldname +
	 * " is blank."); if (isElementPresent(element_key) != null) {
	 * sa.assertEquals(isElementPresent(element_key).getText(), "Please enter " +
	 * fieldname, fieldname + " validation message does not match."); } else {
	 * childtest.warning(fieldname + " validation message does not appear."); } }
	 * else { childtest.warning(fieldname + " validation message does not appear.");
	 * } } sa.assertAll(); }
	 */

	// Take captcha screen shot
	public static void captcha(String element) throws Exception {
		String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "com.fiiviq.main"
				+ File.separator + "resources" + File.separator + "screenshots" + File.separator + "captcha.png";
		WebElement webElement = isElementPresent(element);
		Screenshot screenshot = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver,
				webElement);
		ImageIO.write(screenshot.getImage(), "PNG", new File(path));
		Ocr ocr = new Ocr(); // create a new OCR engine
		ocr.startEngine("eng", Ocr.SPEED_FASTEST); // English
		// path of the image
		String s = ocr.recognize(new File[] { new File(path) }, Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
		System.out.println(s);
		ocr.stopEngine();
	}

	public static void verifyToolTips() {
		isElementPresent("optionbutton_xpath").click();
		List<WebElement> menuList = driver.findElement(By.xpath("// [@role='menu']")).findElements(By.tagName("a"));
		for (int i = 0; i < menuList.size(); i++) {
			String menuToolTip = menuList.get(i).getAttribute("title");
			if (!TextUtils.isEmpty(menuToolTip)) {
				test.log(Status.PASS, "ToolTip: " + menuToolTip);
			} else {
				test.log(Status.WARNING, "Tool tip is missing for class: " + menuList.get(i).getAttribute("class"));
			}
		}

	}

	public static void createHtmlReport(String reportFileName) {
		htmlReporter = new ExtentSparkReporter(
				System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator
						+ "resources" + File.separator + "reports" + File.separator + reportFileName + ".html");
		extent.attachReporter(htmlReporter);
	}

	public static void checkException(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.WARNING, result.getThrowable());
		}
	}

	public static void checkPageURL(String expectedURL) {
		sa.assertEquals(driver.getCurrentUrl(), expectedURL);
	}

	public static void selectDate(String date) throws InterruptedException {
		String date_dd_MM_yyyy[] = (date.split(" ")[0]).split("/");
		driver.findElement(By.xpath("/html/body/div[6]/div[1]/table/thead/tr[1]/th[2]")).click();
		driver.findElement(By.xpath("/html/body/div[6]/div[2]/table/thead/tr/th[2]")).click();
		MyMethods.selectvalue("Year", "year_xpath", "span", date_dd_MM_yyyy[2]);
		MyMethods.selectvalue("Month", "month_xpath", "span", date_dd_MM_yyyy[1]);
		List<WebElement> elementlist = isElementPresent("date_xpath").findElements(By.tagName("td"));
		for (int i = 0; i < elementlist.size(); i++) {
			if (elementlist.get(i).getText().equalsIgnoreCase(date_dd_MM_yyyy[0])
					&& elementlist.get(i).getAttribute("class").equals("day")) {
				elementlist.get(i).click();
				break;
			} else if (i == elementlist.size()) {
				test.log(Status.WARNING, "Date is not in list.");
			}

		}

	}


}