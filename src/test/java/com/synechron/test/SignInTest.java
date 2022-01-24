package com.synechron.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class SignInTest {
	AndroidDriver<WebElement> driver;

	@BeforeMethod
	public void startApp() throws MalformedURLException {
		File file = new File("src/test/resources/app/khan-academy-7-3-2.apk");
		String absolutePath = file.getAbsolutePath();
		System.out.println(absolutePath);

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Redmi");
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		cap.setCapability(MobileCapabilityType.APP, absolutePath);

		driver = new AndroidDriver<WebElement>(new URL("http://localhost:4723/wd/hub"), cap);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void terminateApp() {
		driver.quit();
	}

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
