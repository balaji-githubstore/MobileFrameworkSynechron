package com.synechron.base;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class AppiumWrapper {

	protected AndroidDriver<WebElement> driver;
	private AppiumDriverLocalService service;

	@BeforeTest
	public void appiumServerSetup() {
		
		
		service = AppiumDriverLocalService.buildDefaultService();
		service.start();
	}

	@AfterTest
	public void appiumServerTerminate() {
		service.stop();
	}

	@BeforeMethod
	public void startApp() throws MalformedURLException {
		File file = new File("src/test/resources/app/khan-academy-7-3-2.apk");
		String absolutePath = file.getAbsolutePath();
		System.out.println(absolutePath);

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Redmi");
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		cap.setCapability(MobileCapabilityType.APP, absolutePath);

		driver = new AndroidDriver<WebElement>(service, cap);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void terminateApp() {
		driver.quit();
	}

}
