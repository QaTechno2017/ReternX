/*Test Case to create a customer
Enter valid inputs to all respective fields
Check validation messages
Submit data
*/

package com.fa.userlist.customers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.utilities.MyMethods;

public class CreateCustomerBlankInputs extends VerifyCustomers {
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
	public void test(String testcaseID, String summary, String country, String fullname, String email, String mobile,
			String address1, String address2) throws Exception {
		childtest = parenttest.createNode(excelfiledata.getProperty("ts2_tc1"));
		MyMethods.waitForElementinvisible("loader_xpath");
		test = childtest.createNode("Check Validations");
		isElementPresent("createcustomer_btn_xpath").click();
		isElementPresent("save_xpath").click();
		MyMethods.checkvalidation("Fullanme", "firstname_err_xpath", email);
		MyMethods.checkvalidation("email", "email_err_xpath", email);
		MyMethods.checkvalidation("Mobile Number", "phone_err_xpath", mobile);
		MyMethods.checkvalidation("Address Line 2", "add2_err_xpath", address2);
		MyMethods.checkvalidation("Username", "username_err_xpath", email);
	}
}
