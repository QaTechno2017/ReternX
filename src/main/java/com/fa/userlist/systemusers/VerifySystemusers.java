package com.fa.userlist.systemusers;

import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.main.BaseInIt;
import com.utilities.MyMethods;

public class VerifySystemusers extends BaseInIt{
	@BeforeSuite
	public void checkTestuiteExicution() throws Exception {
		loadproperties();
		parenttest = extent.createTest(excelfiledata.getProperty("ts3"));
		parenttest.assignCategory(excelfiledata.getProperty("ts3"));
		boolean output = MyMethods.checkTestSuiteExecution(ts, excelfiledata.getProperty("test_suites"),
				excelfiledata.getProperty("ts3"));

		if (!output) {
			throw new SkipException(
					"Execution mode of the test suite " + excelfiledata.getProperty("ts3") + " is set to NO");
		} else {
			startUP();
			MyMethods.faLogin();
			MyMethods.waitForElementinvisible("loader_xpath");
			MyMethods.selectvalue("User List", "sidemenu_xpath", "li", "User List");
			MyMethods.waitForElementinvisible("loader_xpath");
			MyMethods.selectvalue("System Users", "tab_xpath", "a", "System Users");
		}
	}

	@AfterSuite
	public void test() {
		extent.flush();
	}

}
