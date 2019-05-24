package com.project.selenium.runner;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.runner.RunWith;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Project.Selenium.Drivers.Base;
import com.Project.Selenium.utilities.IniUtilities;
import com.Project.Selenium.utilities.Report;
import com.cucumber.listener.Reporter;

import cucumber.api.junit.Cucumber;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.api.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html",
		"pretty" }, features = "src/test/resources/features", glue = { "StepDefinations" }, monochrome = true)
// tags = {"@Regression" }
public class TestRunner extends Base{

	public static String iNIFile = "src/main/resources/resultConfig.ini";
	public String DeviceConfigINIpath = "src/main/resources/DeviceConfig.ini";

	private TestNGCucumberRunner testNGCucumberRunner;
	public String PlateformName = null;
	public String device = null;
	public String deviceName = null;

	@BeforeSuite(alwaysRun = true)
	public void setUp() throws InterruptedException, IOException {
	}

	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

	@BeforeTest
	public static void HtmlFile() {
		try {
			Report.CreatehtmlFile();

		} catch (Exception Ex) {
		}

	}

	@Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
	public void feature(CucumberFeatureWrapper cucumberFeature) {
		testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	}

	@DataProvider
	public Object[][] features() {
		System.out.println(testNGCucumberRunner.provideFeatures()[0][0]);
		return testNGCucumberRunner.provideFeatures();

	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception {
		testNGCucumberRunner.finish();
		Reporter.loadXMLConfig(new File("src/main/resources/extent-config.xml"));
		Reporter.setSystemInfo("user", System.getProperty("user.name"));
		Reporter.setSystemInfo("os", "Window 7");

	}

	@AfterSuite
public void afterSuit(){
	}

	@AfterTest
	public void CopyResult() throws IOException {
		driverQuit();
		File htmlFile = new File(IniUtilities.ReadIni(iNIFile, "Scenario", "HTMLFILE", ""));
		File CucmberReport = new File(System.getProperty("user.dir") + "/target/cucumber-reports/report.html");
		FileUtils.copyFile(CucmberReport, htmlFile);
	}

}
