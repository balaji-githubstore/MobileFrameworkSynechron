package com.synechron.test;

import org.testng.annotations.Test;

import com.synechron.base.AppiumWrapper;

public class SignUpTest extends AppiumWrapper {

	@Test
	public void validRegistrationTest() {
		driver.findElementByXPath("//*[@text='Dismiss']").click();
		driver.findElementByAccessibilityId("Settings").click();
		driver.findElementByXPath("//*[@text='Sign in']").click();
		driver.findElementByXPath("//*[@text='Sign up with email']").click();
		driver.findElementByXPath("//*[@text='First name']").sendKeys("john");
		driver.findElementByXPath("//*[@text='Last name']").sendKeys("wick");

		driver.findElementByXPath("//*[@text='Birthday']").click();

		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[1]").click();
		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[1]").clear();
		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[1]").sendKeys("02");

		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[2]").click();
		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[2]").clear();
		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[2]").sendKeys("Aug");

		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[3]").click();
		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[3]").clear();
		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[3]").sendKeys("1992");

		driver.findElementByXPath("//*[@text='OK']").click();

		driver.findElementByXPath("//*[@text='Email address']").sendKeys("wick@gmail.com");
		driver.findElementByXPath("//*[@text='Password']").sendKeys("bala@123");
		
		//click create 
		//assert on valid registration 
	}

}
