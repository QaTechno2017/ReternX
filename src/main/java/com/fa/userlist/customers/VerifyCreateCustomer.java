package com.fa.userlist.customers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.utilities.MyMethods;

public class VerifyCreateCustomer extends VerifyCustomers {
	@BeforeTest
	public void testTestCaseExicution() {
		boolean output = MyMethods.checkTestCaseExecution(ts2, excelfiledata.getProperty("test_cases"),
				excelfiledata.getProperty("ts2_tc1"));
		if (!output) {
			parenttest
					.skip("Execution mode of the test case " + excelfiledata.getProperty("ts2_tc1") + " is set to NO.");
			extent.flush();
			throw new SkipException(
					"Execution mode of the test case " + excelfiledata.getProperty("ts2_tc1") + " is set to NO");
		}
	}

	@DataProvider
	public Object[][] getTestDataFromXLS() {
		return MyMethods.getTestData(ts2, excelfiledata.getProperty("ts2_tc1"));
	}

	@Test(dataProvider = "getTestDataFromXLS")
	public void test(String testcaseID, String summary,String country, String fullname, String email, String mobile, String address1, String address2) {
		childtest = parenttest.createNode(excelfiledata.getProperty("ts2_tc1"));
		MyMethods.waitForElementinvisible("loader_xpath");
		isElementPresent("createcustomer_btn_xpath").click();
		
	}
}
