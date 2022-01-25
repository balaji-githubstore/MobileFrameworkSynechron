package com.synechron.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.synechron.base.AppiumKeywords;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;

public class DismissPage extends AppiumKeywords {

	private By dismissLocator = MobileBy.xpath("//*[@text='Dismiss']");

	public DismissPage(AppiumDriver<WebElement> driver) {
		super(driver);
	}

	public void clickOnDismiss() {
		if (getLocatorCount(dismissLocator) > 0) {
			clickUsingLocator(dismissLocator);
		}
	}

}
