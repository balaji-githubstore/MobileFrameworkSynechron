package com.synechron.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumWrapper {

	protected AndroidDriver<WebElement> driver;
	private AppiumDriverLocalService service;

	public ExtentReports extent;
	public ExtentTest test;

	@BeforeSuite
	public void start() {

		extent = new ExtentReports();
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter("src/test/resources/reports/testReport.html");
		extent.attachReporter(htmlReporter);
	}

	@AfterSuite()
	public void tearDown() {
		extent.flush();
	}

	@BeforeTest
	public void appiumServerSetup() {

		AppiumServiceBuilder builder = new AppiumServiceBuilder()
//				.withIPAddress("127.0.0.1")
				.usingAnyFreePort().withArgument(GeneralServerFlag.RELAXED_SECURITY)
				.withLogFile(new File("src/test/resources/log/appium_log_"
						+ LocalDateTime.now().toString().replace(":", "-") + ".log"));

		service = AppiumDriverLocalService.buildService(builder);
		service.start();
	}

	@AfterTest
	public void appiumServerTerminate() {
		service.stop();
	}

	@BeforeMethod
	public void startApp(Method method) throws MalformedURLException {
		File file = new File("src/test/resources/app/khan-academy-7-3-2.apk");
		String absolutePath = file.getAbsolutePath();
		System.out.println(absolutePath);

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Redmi");
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		cap.setCapability(MobileCapabilityType.APP, absolutePath);

		driver = new AndroidDriver<WebElement>(service, cap);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//		driver.startRecordingScreen();
		test = extent.createTest(method.getName());
	}
	
	public void takeScreenshot() throws IOException
	{
		String fileName ="src/test/resources/screenshots/screenshot_" + LocalDateTime.now().toString().replace(":", "-") + ".png";
		File file = driver.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(fileName));
	}

	public void embedScreenshotToExtentReport()
	{
		String fileName ="screenshot_" + LocalDateTime.now().toString().replace(":", "-");
		test.addScreenCaptureFromBase64String(driver.getScreenshotAs(OutputType.BASE64),fileName); //embed screenshot in extent report
	}
	
	@AfterMethod
	public void terminateApp(ITestResult result) throws IOException {

//		String encoded=driver.stopRecordingScreen();	
//		byte[] decoded= Base64.getDecoder().decode(encoded);
//		FileOutputStream fout=new FileOutputStream("src/test/resources/video/video_"+LocalDateTime.now().toString().replace(":", "-")+".mp4");
//		fout.write(decoded);

		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " FAILED ", ExtentColor.RED));
			test.fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " PASSED ", ExtentColor.GREEN));
		} else {
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " SKIPPED ", ExtentColor.ORANGE));
			test.skip(result.getThrowable());
		}
		
		test.addScreenCaptureFromBase64String(driver.getScreenshotAs(OutputType.BASE64),result.getName()); //embed screenshot in extent report
//		takeScreenshot(); //file will be saved in screenshots folder 
	
		
		driver.quit();
	}

}
