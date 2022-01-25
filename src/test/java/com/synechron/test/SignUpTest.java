package com.synechron.test;

import org.testng.annotations.Test;

import com.synechron.base.AppiumWrapper;
import com.synechron.pages.DismissPage;
import com.synechron.pages.HomePage;
import com.synechron.utilities.DataProviderUtils;

public class SignUpTest extends AppiumWrapper {

	@Test(dataProviderClass = DataProviderUtils.class,dataProvider = "commonDataProvider")
	public void validRegistrationTest(String firstname,String lastname,String date,String email,String password) {
		DismissPage dismiss = new DismissPage(driver);
		dismiss.clickOnDismiss();
		
		HomePage home = new HomePage(driver);
		home.clickOnSettings();
		
		driver.findElementByXPath("//*[@text='Sign in']").click();
		driver.findElementByXPath("//*[@text='Sign up with email']").click();
		driver.findElementByXPath("//*[@text='First name']").sendKeys(firstname);
		driver.findElementByXPath("//*[@text='Last name']").sendKeys(lastname);

		driver.findElementByXPath("//*[@text='Birthday']").click();

		String[] split=date.split("/");
		
		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[1]").click();
		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[1]").clear();
		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[1]").sendKeys(split[0]);

		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[2]").click();
		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[2]").clear();
		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[2]").sendKeys(split[1]);

		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[3]").click();
		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[3]").clear();
		driver.findElementByXPath("(//*[@resource-id='android:id/numberpicker_input'])[3]").sendKeys(split[2]);

		driver.findElementByXPath("//*[@text='OK']").click();

		driver.findElementByXPath("//*[@text='Email address']").sendKeys(email);
		driver.findElementByXPath("//*[@text='Password']").sendKeys(password);
		
		//click create 
		//assert on valid registration 
	}

}
