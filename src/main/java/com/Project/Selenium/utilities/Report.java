package com.Project.Selenium.utilities;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.Project.Selenium.Drivers.Base;
import com.cucumber.listener.Reporter;

public class Report extends Base{

	public static String iNIFile = "src/main/resources/ResultConfig.ini";
	public static String TestCaseName  = IniUtilities.ReadIni(iNIFile, "Sceanrio","CurrentScenarioValue" , "");
	public static Date d = new Date();
	public static String folderName = d.toString().replace(":", "_").replace(" ", "_");
	public static int screenshotsCounter;
	public static String resultfolderpath;
	public static int currentLogID;
	
	
	/**
	 * @author anshulmadan
	 * @Description: Add step log to report
	 */
	public static void addStepLog(String logs){
		Reporter.addStepLog(logs);
	}
	
	public static void addTestCases(String testCaseName){
		
		Logging.startTestCase(testCaseName);
	}
	
	
	public static void endTestCases(){
		Logging.startTestCase(TestCaseName);
	}
	/**
	 * @author anshulmadan
	 * @Description: Add scenario log to report
	 */
	public static void addScenarioLog(String logs){
		Reporter.addScenarioLog(logs);
	}
	
	/**
	 * @author anshulmadan
	 * @Description: Add screen shots to report
	 */
	public static void addScreenCaptureFromPath(String path) throws IOException{
		try{
			Reporter.addScreenCaptureFromPath(path);
		}catch(Exception ex){
			Reporter.addStepLog("screenshots path does not exist");
		}
	}
	
	public static void CreatehtmlFile() {
		try {
			String resultfolderpath = System.getProperty("user.dir") + "/target/cucumber-reports/" + folderName;
			File resultfolder = new File(resultfolderpath);
			boolean success = (resultfolder).mkdir();
			if (success) {
				String fileName = folderName + ".HTML";
				String htmlFile = resultfolderpath + File.separator + fileName;
				IniUtilities.WriteIni(iNIFile, "Scenario","ReportFolder" , resultfolderpath);
				IniUtilities.WriteIni(iNIFile, "Scenario","HTMLFILE" , htmlFile);
			}
		} catch (Exception Ex) {
		}

	}
	
	public static void logMessage(String status, String additionalinformation) {
		try {
			switch (status) {
			case "Pass":
				Logging.info(additionalinformation);
				addStepLog(additionalinformation);
				break;
			case "Fail":
				Logging.error(additionalinformation);
				addStepLog(additionalinformation);
				getScreenshot();
				break;
			case "Warning":
				Logging.warn(additionalinformation);
				addStepLog(additionalinformation);
				break;
			case "Skip":
				Logging.info(additionalinformation);
				addStepLog(additionalinformation);
				break;
			case "None":
				Logging.info(additionalinformation);
				addStepLog(additionalinformation);
				break;
			case "INFO":
				Logging.info(additionalinformation);
				addStepLog(additionalinformation);
				break;
			}

		} catch (Exception Ex) {
		}

	}
	
	
	
}
