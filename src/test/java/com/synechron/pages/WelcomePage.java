package com.synechron.pages;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class WelcomePage {
	private AppiumDriver<WebElement> driver;

	public WelcomePage(AppiumDriver<WebElement> driver) {
		this.driver = driver;
	}

	public void clickOnSignIn() {
		driver.findElementByXPath("//*[@text='Sign in']").click();
	}

	public void ClickOnConitnueWithApple() {
		driver.findElementByXPath("//*[@text='Continue with Apple']").click();
	}

}
