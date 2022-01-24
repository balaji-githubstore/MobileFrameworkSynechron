package com.synechron.test;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.synechron.base.AppiumWrapper;

public class SignInTest extends AppiumWrapper {
	
	@Test
	public void invalidCredentialTest() throws MalformedURLException {

		driver.findElementByXPath("//*[@text='Dismiss']").click();
		driver.findElementByXPath("//*[@text='Sign in']").click();
		driver.findElementByXPath("//*[@text='Sign in']").click();
		driver.findElementByXPath("//*[contains(@text,'address')]").sendKeys("bala");
		driver.findElementByXPath("//*[@content-desc='Password']").sendKeys("bala123");

		if (driver.isKeyboardShown()) {
			driver.hideKeyboard();
		}

		driver.findElementByXPath("(//*[@text='Sign in'])[2]").click();
		String actualError = driver.findElementByXPath("//*[contains(@text,'issue')]").getText();

		Assert.assertEquals(actualError, "There was an issue signing in");
	}
	
	

}
