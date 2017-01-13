package com.prodigy.report;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

@Test(groups = { "report" })
public class CheckSonarReportTest {
	private WebDriver driver;

	@Test
	public void takeScreenshot() throws Exception {
		driver = new FirefoxDriver();
		driver.get("http://localhost:9000/overview/debt?id=com.prodigy%3Aprodigy-api-automation-test-lib");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Wait.waitUntilDisplay(driver, driver.findElement(By.cssSelector(".overview-cards-list")), 60);

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("target/sonar_report/screenshot_sonar.png"));
		driver.close();
	}

}
