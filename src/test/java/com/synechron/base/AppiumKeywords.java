package com.synechron.base;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class AppiumKeywords {

	protected AppiumDriver<WebElement> driver;

	public AppiumKeywords(AppiumDriver<WebElement> driver) {
		this.driver = driver;
	}

	public void clickUsingLocator(By by) {
		driver.findElement(by).click();
	}

	public void typeUsingLocator(By by, String text) {
		driver.findElement(by).sendKeys(text);
	}

	public int getLocatorCount(By by) {
		return driver.findElements(by).size();
	}

	public void scrollAndClickUsingLocator(By by) {
		Dimension dim = driver.manage().window().getSize();

		int width = dim.width;
		int height = dim.height;
		System.out.println(width);
		System.out.println(height);

		int x1 = (int) (width * (0.5));
		int y1 = (int) (height * (0.75));

		int x2 = (int) (width * (0.5));
		int y2 = (int) (height * (0.25));
		TouchAction action = new TouchAction(driver);
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		while (driver.findElements(by).size() == 0) {
			action.press(PointOption.point(x1, y1)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
					.moveTo(PointOption.point(x2, y2)).release().perform();

		}
		driver.findElement(by).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

}
