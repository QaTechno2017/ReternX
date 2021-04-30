package com.fa.userlist.customers;

import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.main.BaseInIt;
import com.utilities.MyMethods;

public class VerifyCustomers extends BaseInIt{
	@BeforeSuite
	public void checkTestuiteExicution() throws Exception {
		loadproperties();
		parenttest = extent.createTest(excelfiledata.getProperty("ts2"));
		parenttest.assignCategory(excelfiledata.getProperty("ts2"));
		boolean output = MyMethods.checkTestSuiteExecution(ts, excelfiledata.getProperty("test_suites"),
				excelfiledata.getProperty("ts2"));

		if (!output) {
			throw new SkipException(
					"Execution mode of the test suite " + excelfiledata.getProperty("ts2") + " is set to NO");
		} else {
			startUP();
			MyMethods.faLogin();
			MyMethods.waitForElementinvisible("loader_xpath");
			MyMethods.selectvalue("User List", "sidemenu_xpath", "li", "User List");
		}
	}

	@AfterSuite
	public void test() {
		extent.flush();
	}

}
