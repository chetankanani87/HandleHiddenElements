package handleHiddenElements;

import java.io.IOException;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import lib.BrowserDriverUtility;
import lib.ExtentReportUtility;
import lib.ScreenshotUtility;
import lib.EmailUtility;
import lib.EmailWithAttachmentUtility;

public class HandleHiddenElements {
	WebDriver dr = BrowserDriverUtility.InvokeBrowser("webdriver.chrome.driver",
			"C:\\Chetan\\SeleniumSuite\\WebDrivers\\chromedriver.exe",
			"http://seleniumpractise.blogspot.com/2016/08/how-to-automate-radio-button-in.html");
	ExtentReports report = ExtentReportUtility.InvokeExtentReport();;
	ExtentTest logger = report.createTest("Handling Hidden Elements");
	String path;
	WebElement ele;
	List<WebElement> radio;

	@BeforeTest
	public void InvokeBrowser() {
		try {
			path = ScreenshotUtility.CaptureScreenshot(dr, "1_MainPage");
			logger.pass("Main Page - Screenshot taken.", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void MouseHover() {
		try {

			radio = dr.findElements(By.id("male"));
			System.out.println("Total radio buttons are: " + radio.size());

			for (int i = 0; i < radio.size(); i++) {
				ele = radio.get(i);
				int x = ele.getLocation().getX();
				if (x != 0) {
					ele.click();
					break;
				}
			}

			path = ScreenshotUtility.CaptureScreenshot(dr, "2_ClickedOnMaleRadio");
			logger.pass("Clicked on 'Male' radio button - Screenshot taken.",
					MediaEntityBuilder.createScreenCaptureFromPath(path).build());

			// Some extra stuff - we are clicking on language radios and Hobbies checkboxes
			radio = dr.findElements(By.name("lang"));
			for (int i = 0; i < radio.size(); i++) {
				ele = radio.get(i);
				ele.click();
				String text = ele.getAttribute("id");
				path = ScreenshotUtility.CaptureScreenshot(dr, i + 3 + "_ClickedOn"+text+"Radio");
				logger.pass("Clicked on "+text+" radio button - Screenshot taken.",
						MediaEntityBuilder.createScreenCaptureFromPath(path).build());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@AfterTest
	public void tearDown() {
		try {
			EmailWithAttachmentUtility.SendEmail("Test Case Passed", "Congratulations...!!!", path, "Screenshot of checkbox which are working fine...!!!");
			report.flush();
			dr.close();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
