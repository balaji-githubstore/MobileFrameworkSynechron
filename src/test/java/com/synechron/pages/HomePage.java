package com.synechron.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.synechron.base.AppiumKeywords;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;

public class HomePage extends AppiumKeywords {

	private By signInLocator = MobileBy.xpath("//*[@text='Sign in' or @name='Sign in']");
	private By searchLocator = MobileBy.xpath("//*[@text='Search']");
	private By settingsLocator = MobileBy.AccessibilityId("Settings");


	public HomePage(AppiumDriver<WebElement> driver) {
		super(driver);
	}

	public void clickOnSignIn() {
		clickUsingLocator(signInLocator);
	}

	public void clickOnSearch() {
		clickUsingLocator(searchLocator);
	}

	public void clickOnSettings() {
		clickUsingLocator(settingsLocator);
	}

}
