package com.fa.dispatch;

import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.main.BaseInIt;
import com.utilities.MyMethods;

public class VerifyFFDispatch extends BaseInIt {
	@BeforeSuite
	public void checkTestuiteExicution() throws Exception {
		loadproperties();
		parenttest = extent.createTest(excelfiledata.getProperty("ffdispatch"));
		parenttest.assignCategory(excelfiledata.getProperty("ffdispatch"));
		boolean output = MyMethods.checkTestSuiteExecution(ts, excelfiledata.getProperty("test_suites"),
				excelfiledata.getProperty("ffdispatch"));

		if (!output) {
			throw new SkipException(
					"Execution mode of the test suite " + excelfiledata.getProperty("ffdispatch") + " is set to NO");
		} else {
			startUP();
			MyMethods.ffLogin();
			MyMethods.waitForElementinvisible("loader_xpath");
		}
	}

	@AfterSuite
	public void test() {
		extent.flush();
	}

}
