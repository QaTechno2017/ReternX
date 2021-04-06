// This is a TestSuite.

package com.fa.shipment;

import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.utilities.MyMethods;
import com.main.BaseInIt;

public class VerifyShipment extends BaseInIt {

	@BeforeSuite
	public void checkTestuiteExicution() throws Exception {
		loadproperties();
		parenttest = extent.createTest(excelfiledata.getProperty("ts1"));
		parenttest.assignCategory(excelfiledata.getProperty("ts1"));
		boolean output = MyMethods.checkTestSuiteExecution(ts, excelfiledata.getProperty("test_suites"),
				excelfiledata.getProperty("ts1"));

		if (!output) {
			throw new SkipException(
					"Execution mode of the test suite " + excelfiledata.getProperty("ts1") + " is set to NO");
		} else {
			startUP();
			MyMethods.Login();
		}
	}
	
	@AfterSuite
	public void test() {
		extent.flush();
	}

}
