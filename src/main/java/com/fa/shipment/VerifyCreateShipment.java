package com.fa.shipment;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
//import org.testng.ITestResult;
import org.testng.SkipException;
//import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.utilities.MyMethods;

public class VerifyCreateShipment extends VerifyShipment {
	@BeforeTest
	public void testTestCaseExicution() {
		boolean output = MyMethods.checkTestCaseExecution(ts1, excelfiledata.getProperty("test_cases"),
				excelfiledata.getProperty("ts1_tc1"));
		if (!output) {
			parenttest
					.skip("Execution mode of the test case " + excelfiledata.getProperty("ts1_tc1") + " is set to NO.");
			extent.flush();
			throw new SkipException(
					"Execution mode of the test case " + excelfiledata.getProperty("ts1_tc1") + " is set to NO");
		}
	}

	@DataProvider
	public Object[][] getTestDataFromXLS() {
		return MyMethods.getTestData(ts1, excelfiledata.getProperty("ts1_tc1"));
	}

	@Test(dataProvider = "getTestDataFromXLS")
	public void test(String testcaseID, String summary, String size, String oport, String dport, String sdate, String adate)
			throws Exception {
		childtest = parenttest.createNode(excelfiledata.getProperty("ts1_tc1"));
		MyMethods.selectvalue("Shipment", "sidemenu_xpath", "li", "Shipments");
		MyMethods.click("close_xpath");
		MyMethods.click("createshipment_xpath");
		MyMethods.sendKeys("OriginPort", "originport_xpath", oport);
		isElementPresent("sailingdate_xpath").click();
		MyMethods.selectDate("26/mar/2021");
		MyMethods.sendKeys("DestinationPort", "destinationport_xpath", dport);
		isElementPresent("arriveddate_xpath").click();
		MyMethods.selectDate("28/mar/2021");
		MyMethods.click("save_xpath");
		sa.assertAll();
	}

	/*
	 * @AfterMethod public void checkexception(ITestResult result) throws Exception
	 * { if (result.getStatus() == ITestResult.SUCCESS) {
	 * MyMethods.takeScreenShot(); } else if (result.getStatus() ==
	 * ITestResult.FAILURE) { childtest.log(Status.FAIL, "\n" +
	 * result.getThrowable()); parenttest.fail("\n" +
	 * excelfiledata.getProperty("ts1_tc1") + " is failed.");
	 * MyMethods.takeScreenShot(); } else if (result.getStatus() ==
	 * ITestResult.SKIP) {
	 * 
	 * }
	 * 
	 * }
	 */
}
