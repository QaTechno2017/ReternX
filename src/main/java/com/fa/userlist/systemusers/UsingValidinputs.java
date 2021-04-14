package com.fa.userlist.systemusers;

import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.utilities.MyMethods;

public class UsingValidinputs extends VerifySystemusers {
	@BeforeTest
	public void testTestCaseExicution() {
		boolean output = MyMethods.checkTestCaseExecution(ts3, excelfiledata.getProperty("test_cases"),
				excelfiledata.getProperty("ts3_tc1"));
		if (!output) {
			parenttest
					.skip("Execution mode of the test case " + excelfiledata.getProperty("ts3_tc1") + " is set to NO.");
			extent.flush();
			throw new SkipException(
					"Execution mode of the test case " + excelfiledata.getProperty("ts3_tc1") + " is set to NO");
		}
	}

	@DataProvider
	public Object[][] getTestDataFromXLS() {
		return MyMethods.getTestData(ts3, excelfiledata.getProperty("ts3_tc1"));
	}

	@Test(dataProvider = "getTestDataFromXLS")
	public void test(String testcaseid, String test_summary, String country, String name, String email, String mobile,
			String role, String warehousename, String maxbox, String minbox, String locationname, String pickupzone,
			String thirdparty) {
		childtest = parenttest.createNode(test_summary);
		MyMethods.waitForElementinvisible("loader_xpath");
		MyMethods.selectvalue("Create User", "panelheader_xpath", "a", "Create User");
		if (isElementPresent("role_lbl_xpath").isDisplayed() == true) {
			isElementPresent("role_btn_xpath").click();

			// For office role
			test = childtest.createNode("Office role");
			if (role.contains("Office")) {
				test.log(Status.INFO, "Selecting role...");
				MyMethods.selectvalue("Role", "role_xpath", "li", role);
				test.log(Status.INFO, "Comparing label...");
				sa.assertEquals(isElementPresent("emailstatus_lbl_xpath").getText(), "CONTROL CENTER");
				test.log(Status.INFO, "Comparing username...");
				sa.assertEquals(isElementPresent("username_txt_xpath").getText(), email);

				// For warehouse role
			} else if (role.contains("Warehouse")) {
				MyMethods.selectvalue("role", "role_xpath", "li", role);
				MyMethods.waitForElementinvisible("loader_xpath");
				if (isElementPresent("warehouse_lbl_xpath").isDisplayed()
						&& isElementPresent("warehouse_lbl_xpath").getText().equals("Warehouse Name")) {
					isElementPresent("warehouse_btn_xpath").click();
					MyMethods.selectvalue("Warehouse", "warehouse_list_xpath", "li", warehousename);
					test = childtest.createNode("Check text");
					sa.assertEquals(isElementPresent("emailstatus_lbl_xpath").getText(), "SCAN APP");
				}

				// For delivery agent role
			} else if (role.contains("Delivery Agent")) {
				MyMethods.selectvalue("role", "role_xpath", "li", role);
				if (isElementPresent("maxbox_txt_xpath").isDisplayed()
						&& isElementPresent("minbox_txt_xpath").isDisplayed()
						&& isElementPresent("pickupzone_check_xpath").isDisplayed()) {
					MyMethods.sendKeys("Max Box Capacity", "maxbox_txt_xpath", maxbox);
					MyMethods.sendKeys("Max Job Capacity", "minbox_txt_xpath", minbox);
					MyMethods.selectvalue("Pickup zone", "pickupzone_check_xpath", "label", pickupzone);
				}

				// For agent location role
			} else if (role.contains("Agent Location")) {
				MyMethods.selectvalue("role", "role_xpath", "li", role);
				if (isElementPresent("agentlocation_lbl_xpath=").isDisplayed()
						&& isElementPresent("thirdparty_check_xpath").isDisplayed()) {
					isElementPresent("agentlocation_btn_xpath").click();
					MyMethods.selectvalue("Agent Location Name", "agentlocation_list_xpath", "li", locationname);
				}
			} else if (role.contains("Branch or Agent Location")) {
				MyMethods.selectvalue("role", "role_xpath", "li", role);
				sa.assertTrue(isElementPresent("agentlocation_btn_xpath").isDisplayed());
				MyMethods.selectvalue("Agent Location Name", "agentlocation_list_xpath", "li", locationname);
			}

		}
		sa.assertAll();
	}

	@AfterMethod
	public void checkexception(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.SUCCESS) {
			MyMethods.takeScreenShot();
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, "\n" + result.getThrowable());
			MyMethods.takeScreenShot();
		} else if (result.getStatus() == ITestResult.SKIP) {

		}

	}

}
