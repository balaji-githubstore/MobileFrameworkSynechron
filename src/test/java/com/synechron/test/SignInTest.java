package com.synechron.test;

import java.net.MalformedURLException;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.synechron.base.AppiumWrapper;
import com.synechron.pages.DismissPage;
import com.synechron.pages.HomePage;
import com.synechron.pages.SignInPage;
import com.synechron.pages.WelcomePage;
import com.synechron.utilities.DataProviderUtils;

import io.appium.java_client.android.AndroidDriver;

public class SignInTest extends AppiumWrapper {

	@Test(dataProviderClass = DataProviderUtils.class, dataProvider = "commonDataProvider")
	public void invalidCredentialTest(String username, String password, String expectedError)
			throws MalformedURLException {

		DismissPage dismiss = new DismissPage(driver);
		dismiss.clickOnDismiss();

		test.log(Status.INFO, "Handled dismiss");

		HomePage home = new HomePage(driver);
		home.clickOnSignIn();

		WelcomePage welcome = new WelcomePage(driver);
		welcome.clickOnSignIn();

		SignInPage signIn = new SignInPage(driver);
		signIn.enterUsername(username);
		test.log(Status.INFO, "Entered Username as " + username);
		signIn.enterPassword(password);
		test.log(Status.INFO, "Entered Password as " + password);
		signIn.clickOnSignIn();

		String actualError = signIn.getInvalidErrorMessage();

		test.log(Status.INFO, "Error Message Displayed is" + actualError);

		Assert.assertEquals(actualError, expectedError);

		test.log(Status.INFO, "Assertion Successful");
	}

	// will start 3:55 PM IST

}
