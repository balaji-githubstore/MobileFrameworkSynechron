package com.synechron.test;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.synechron.base.AppiumWrapper;
import com.synechron.utilities.DataProviderUtils;

public class SignInTest extends AppiumWrapper {
	
	@Test(dataProviderClass = DataProviderUtils.class,dataProvider = "commonDataProvider")
	public void invalidCredentialTest(String username,String password,String expectedError) throws MalformedURLException {

		if(driver.findElementsByXPath("//*[@text='Dismiss']").size()>0)
		{
			test.log(Status.INFO, "Dismiss is present");
			driver.findElementByXPath("//*[@text='Dismiss']").click();
		}
		
		
		driver.findElementByXPath("//*[@text='Sign in']").click();
		driver.findElementByXPath("//*[@text='Sign in']").click();
		driver.findElementByXPath("//*[contains(@text,'address')]").sendKeys(username);
		
		test.log(Status.INFO, "Entered Username as "+username);
		
		driver.findElementByXPath("//*[@content-desc='Password']").sendKeys(password);
		
		test.log(Status.INFO, "Entered Password as "+password);

		if (driver.isKeyboardShown()) {
			driver.hideKeyboard();
		}

		driver.findElementByXPath("(//*[@text='Sign in'])[2]").click();
		String actualError = driver.findElementByXPath("//*[contains(@text,'issue')]").getText();
		
		test.log(Status.INFO, "Error Message Displayed is"+actualError);

		Assert.assertEquals(actualError, expectedError);
		
		test.log(Status.INFO, "Assertion Successful");
	}
	
	

}
