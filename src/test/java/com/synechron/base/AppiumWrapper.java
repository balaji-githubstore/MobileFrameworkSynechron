package com.synechron.base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
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
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.synechron.utilities.PropUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumWrapper {

	protected AppiumDriver<WebElement> driver;
	private AppiumDriverLocalService service;

	public ExtentReports extent;
	public ExtentTest test;

	private String system;
	
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
	public void appiumServerSetup() throws IOException {

		system = PropUtils.getValue("src/test/resources/testdata/data.properties", "system");
		if(system.equalsIgnoreCase("local"))
		{
			AppiumServiceBuilder builder = new AppiumServiceBuilder()
//					.withIPAddress("127.0.0.1")
					.usingAnyFreePort().withArgument(GeneralServerFlag.RELAXED_SECURITY)
					.withLogFile(new File("src/test/resources/log/appium_log_"
							+ LocalDateTime.now().toString().replace(":", "-") + ".log"));

			service = AppiumDriverLocalService.buildService(builder);
			service.start();
		}
		
	}

	@AfterTest
	public void appiumServerTerminate() {
		if(service.isRunning())
		{
			service.stop();
		}
	}

	@BeforeMethod
	@Parameters({"udid"})
	public void startApp(String udid,Method method) throws IOException {

		String appFile = PropUtils.getValue("src/test/resources/testdata/data.properties", "app");

		File file = new File(appFile);
		String absolutePath = file.getAbsolutePath();
		System.out.println(absolutePath);

		String platform = PropUtils.getValue("src/test/resources/testdata/data.properties", "platform");
		
		if (system.equalsIgnoreCase("local")) {
			if (platform.equalsIgnoreCase("android")) {
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Redmi");
				cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
				cap.setCapability(MobileCapabilityType.UDID, udid);
				cap.setCapability(MobileCapabilityType.APP, absolutePath);

				driver = new AndroidDriver<WebElement>(service, cap);
			} else {
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Redmi");
				cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "ios");
				cap.setCapability(MobileCapabilityType.APP, absolutePath);

				driver = new IOSDriver<WebElement>(service, cap);
			}
		} else {
			if (platform.equalsIgnoreCase("android")) {
				DesiredCapabilities caps = new DesiredCapabilities();

				// Set your access credentials
				caps.setCapability("browserstack.user", "dinakaranbalaji1");
				caps.setCapability("browserstack.key", "6yXRE4nK1fyvTHWA2kPD");

				// Set URL of the application under test
				caps.setCapability("app", "bs://73d49796be5962a033f357638057c91b4baae252");

				// Specify device and os_version for testing
				caps.setCapability("device", "Google Pixel 3");
				caps.setCapability("os_version", "9.0");

				// Set other BrowserStack capabilities
				caps.setCapability("project", "First Java Project - Synechron");
				caps.setCapability("build", "Java Android-Synechron");
				caps.setCapability("name", "first_test-Synechron");

				// Initialise the remote Webdriver using BrowserStack remote URL
				// and desired capabilities defined above
				driver = new AndroidDriver<WebElement>(new URL("http://hub.browserstack.com/wd/hub"), caps);
			} else {
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability("browserstack.user", "dinakaranbalaji1");
				caps.setCapability("browserstack.key", "6yXRE4nK1fyvTHWA2kPD");
				caps.setCapability("app", "bs://73e2c758dfbe0fd01e5ceb4eb043f481261affcb");
				caps.setCapability("device", "iPhone 11 Pro");
				caps.setCapability("os_version", "13");

				caps.setCapability("project", "Java IOS SYNECHRON");
				caps.setCapability("build", "Java iOS SYNECHRON");
				caps.setCapability("name", "first_test_SYNECHRON");

				driver = new IOSDriver<WebElement>(new URL("http://hub-cloud.browserstack.com/wd/hub"), caps);
			}

		}

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//		driver.startRecordingScreen();
		test = extent.createTest(method.getName());
	}

	public void takeScreenshot() throws IOException {
		String fileName = "src/test/resources/screenshots/screenshot_"
				+ LocalDateTime.now().toString().replace(":", "-") + ".png";
		File file = driver.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(fileName));
	}

	public void embedScreenshotToExtentReport() {
		String fileName = "screenshot_" + LocalDateTime.now().toString().replace(":", "-");
		test.addScreenCaptureFromBase64String(driver.getScreenshotAs(OutputType.BASE64), fileName); // embed screenshot
																									// in extent report
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

		test.addScreenCaptureFromBase64String(driver.getScreenshotAs(OutputType.BASE64), result.getName()); // embed
																											// screenshot
																											// in extent
																											// report
//		takeScreenshot(); //file will be saved in screenshots folder 

		driver.quit();
	}

}
