package com.synechron.test;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.synechron.base.AppiumWrapper;
import com.synechron.utilities.DataProviderUtils;

public class SignInTest extends AppiumWrapper {
	
	@Test(dataProviderClass = DataProviderUtils.class,dataProvider = "commonDataProvider")
	public void invalidCredentialTest(String username,String password,String expectedError) throws MalformedURLException {

		driver.findElementByXPath("//*[@text='Dismiss']").click();
		driver.findElementByXPath("//*[@text='Sign in']").click();
		driver.findElementByXPath("//*[@text='Sign in']").click();
		driver.findElementByXPath("//*[contains(@text,'address')]").sendKeys(username);
		driver.findElementByXPath("//*[@content-desc='Password']").sendKeys(password);

		if (driver.isKeyboardShown()) {
			driver.hideKeyboard();
		}

		driver.findElementByXPath("(//*[@text='Sign in'])[2]").click();
		String actualError = driver.findElementByXPath("//*[contains(@text,'issue')]").getText();

		Assert.assertEquals(actualError, expectedError);
	}
	
	

}
