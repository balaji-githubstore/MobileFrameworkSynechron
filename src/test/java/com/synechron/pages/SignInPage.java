package com.synechron.pages;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class SignInPage {
	private AppiumDriver<WebElement> driver;

	public SignInPage(AppiumDriver<WebElement> driver) {
		this.driver = driver;
	}

	public void enterUsername(String username) {
		driver.findElementByXPath("//*[contains(@text,'address')]").sendKeys(username);
	}

	public void enterPassword(String password) {
		driver.findElementByXPath("//*[@content-desc='Password']").sendKeys(password);
	}

	public void clickOnSignIn() {
		if (((AndroidDriver<WebElement>) driver).isKeyboardShown()) {
			driver.hideKeyboard();
		}
		driver.findElementByXPath("(//*[@text='Sign in'])[2]").click();
	}
	
	public String getInvalidErrorMessage()
	{
		return driver.findElementByXPath("//*[contains(@text,'issue')]").getText().trim();
	}

}
