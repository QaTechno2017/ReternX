package com.fa.userlist.customers;

import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.utilities.MyMethods;

public class CreateCustoemrValidInputs extends VerifyCustomers {

	@BeforeTest
	public void testTestCaseExicution() {
		boolean output = MyMethods.checkTestCaseExecution(ts2, excelfiledata.getProperty("test_cases"),
				excelfiledata.getProperty("ts2_tc2"));
		if (!output) {
			parenttest
					.skip("Execution mode of the test case " + excelfiledata.getProperty("ts2_tc2") + " is set to NO.");
			extent.flush();
			throw new SkipException(
					"Execution mode of the test case " + excelfiledata.getProperty("ts2_tc2") + " is set to NO");
		}
	}

	@DataProvider
	public Object[][] getTestDataFromXLS() {
		return MyMethods.getTestData(ts2, excelfiledata.getProperty("ts2_tc2"));
	}

	@Test(dataProvider = "getTestDataFromXLS")
	public void test(String testcaseID, String summary, String country, String fullname, String email, String mobile,
			String address1, String address2) {
		childtest = parenttest.createNode(summary);
		MyMethods.waitForElementinvisible("loader_xpath");
		isElementPresent("createcustomer_btn_xpath").click();
		MyMethods.selectvalue("Couuntry", "country_xpath", "label", country);
		isElementPresent("fullname_xpath").sendKeys(fullname);
		isElementPresent("email_xpath").sendKeys(email);
		isElementPresent("mobile_xpath").sendKeys(mobile);
		isElementPresent("address1_txt_xpath").sendKeys(address1);
		isElementPresent("adress2_txt_xpath").sendKeys(address2);
		isElementPresent("save_xpath").click();
		MyMethods.waitForElementinvisible("loader_xpath");
		test = childtest.createNode("Check Confirmatiob dialog");
		if (isElementPresent("confirmation_mdl_xpath").isDisplayed() == true) {
			test.pass("Confirmation dialog appears");
			if (isElementPresent("confirmation_body_xpath").getText()
					.equals(elementdata.getProperty("Confirmation_body"))) {
				test.pass("Confirmation content is okay.");
			} else {
				test.fail("[Actual]" + isElementPresent("confirmation_body_xpath").getText() + "[Expected]"
						+ elementdata.getProperty("Confirmation_body"));
			}
			test=childtest.createNode("Check DO Later button");
			isElementPresent("dolater_btn_xpath").click();
			if (driver.getCurrentUrl().contains("contact-list")) {
				test.pass("The customer list screen appears.");
			} else {
				test.fail("The customer list screen not appeaed.");
			}
		} else {
			test.fail("Confirmation dialog not appeared.");
			if (driver.getCurrentUrl().contains("/customer/create")) {
				test.pass("The customer list screen appears.");
			} else {
				test.fail("The customer list screen not appeaed.");
			}
		}

	}
}
