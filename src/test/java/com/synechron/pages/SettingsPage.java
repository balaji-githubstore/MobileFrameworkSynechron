package com.synechron.pages;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class SettingsPage {
	private AppiumDriver<WebElement> driver;

	public SettingsPage(AppiumDriver<WebElement> driver) {
		this.driver = driver;
	}

}
