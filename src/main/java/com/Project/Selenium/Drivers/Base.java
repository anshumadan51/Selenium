package com.Project.Selenium.Drivers;


import static org.testng.Assert.assertTrue;

import java.io.File;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.Project.Selenium.utilities.IniUtilities;
import com.Project.Selenium.utilities.IntitializeFramework;
import com.Project.Selenium.utilities.Report;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Base extends IntitializeFramework {

	public static String commandIOs = "/bin/bash -l -c appium --session-override -p 4723";
	public static String commandAndroid = "appium -a 127.0.0.1 -p 4723";
	public String DeviceConfigINIpath = "src/main/resources/DeviceConfig.ini";
	public static WebDriver driver;
	public WebElement element;
	public static String iNIFile = "src/main/resources/ResultConfig.ini";
	public static int screenshotsCounter;
	public static String resultfolderpath;
	public static int currentLogID;

	public void invokeBrowser(String browser) throws MalformedURLException {
		try {
			if (browser.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/main/resources/chromedriver.exe");
				//WebDriverManager.chromedriver().version("2.40").setup();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("disable-infobars");
				driver = new ChromeDriver(options);
			} else if (browser.equalsIgnoreCase("FireFox")) {
				FirefoxOptions Options = new FirefoxOptions();
				Options.setCapability("marionette", true);
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "src/main/resources/geckodriver.exe");
				driver = new FirefoxDriver(Options);
			} else if (browser.equalsIgnoreCase("IE")) {
				driver = new InternetExplorerDriver();
			}
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			Report.logMessage("Pass", "Launching the Browser");
			Thread.sleep(2000);
		} catch (Exception ex) {
			ex.printStackTrace();
			Report.logMessage("Fail", "Unable to Launch the Browser" + ex.getMessage());
		}
	}

	/**
	 * @author anshulmadan
	 * @Description: Open URL
	 * @param URL
	 *            URL to open
	 */
	public void openURL(String URL) {
		try {
			driver.get(URL);
		} catch (Exception ex) {
			Report.logMessage("Fail", "Unable to Launch the Browser" + ex.getMessage());
			Assert.assertTrue(false, ex.getMessage());
		}
	}

	/**
	 * @author anshulmadan
	 * @Description: Get the Element using Id,xpath
	 * @param objectXPath
	 *            object tag name in OR.xml
	 * @return WebElement
	 */
	public WebElement getElement(String objectXPath) {
		WebElement Element = null;
		HashMap<String, String> Elementproperties = getXMLContent(objectXPath);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			//if (js.executeScript("return document.readyState", new Object[0]).equals("Complete")) {
				if (Elementproperties.containsKey("Xpath")) {
					Element = (WebElement) (driver.findElement(By.xpath(Elementproperties.get("Xpath"))));
				} else if (Elementproperties.containsKey("Id")) {
					Element = (WebElement) (driver.findElement(By.id(Elementproperties.get("Id"))));
				} else if (Elementproperties.containsKey("CSS")) {
					Element = (WebElement) (driver.findElement(By.cssSelector(Elementproperties.get("Id"))));
				}
			
		} catch (Exception ex) {
			assertTrue(false, ex.getMessage());
		}
		return Element;
	}
	
	
	/**
	 * @author anshulmadan
	 * @Description: Get the Element using Id,xpath
	 * @param objectXPath
	 *            object tag name in OR.xml
	 * @return WebElement
	 */
	public List<WebElement> getElementList(String objectXPath) {
		List <WebElement> Element = null;
		HashMap<String, String> Elementproperties = getXMLContent(objectXPath);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			//if (js.executeScript("return document.readyState", new Object[0]).equals("Complete")) {
				if (Elementproperties.containsKey("Xpath")) {
					Element =  (driver.findElements(By.xpath(Elementproperties.get("Xpath"))));
				} else if (Elementproperties.containsKey("Id")) {
					Element = (driver.findElements(By.id(Elementproperties.get("Id"))));
				} else if (Elementproperties.containsKey("CSS")) {
					Element = (driver.findElements(By.cssSelector(Elementproperties.get("Id"))));
				}
			
		} catch (Exception ex) {
			Assert.assertTrue(false, ex.getMessage());
		}
		return Element;
	}
	
	/**
	 * @author anshulmadan
	 * @Description: Click Button
	 * @param: objectXPath Object in OR
	 *  @return: boolean true if click on button              
	 */
	public boolean clickButton(String objectXPath) {
		WebElement element = null;
		boolean bClick = false;
		try {
			element = getElement(objectXPath);
			setCoordinate(element);
			element.click();
			bClick = true;
			// -----write into Report-----
			Report.logMessage("Pass", "Clicking on [" + objectXPath + "]");
			return bClick;
		} catch (Exception ex) {
			Report.logMessage("Fail", "Not able to Click on [" + objectXPath + "]");
			Assert.assertTrue(false, ex.getMessage());
		}
		return bClick;
	}
	
	
	/**
	 * @author anshulmadan
	 * @Description: Click Button
	 * @param: objectXPath Object in OR
	 *  @return: boolean true if click on button              
	 */
	public boolean verifyElementIsDisplayed(String objectXPath) {
		WebElement element = null;
		boolean bDisplayed = false;
		try {
			element = getElement(objectXPath);
			setCoordinate(element);
			if(element!=null){
				bDisplayed= true;
			}
			return bDisplayed;
		} catch (Exception ex) {
			Report.logMessage("Fail", "Not able to Click on [" + objectXPath + "]");
			Assert.assertTrue(false, ex.getMessage());
		}
		return bDisplayed;
	}

	/**
	 * @author anshulmadan
	 * @Description: Get the screen shot
	 * @description1: It take the screen shot and add the screen shot in folder
	 *                and result link
	 */
	public static void getScreenshot() {
		try {
			resultfolderpath = IniUtilities.ReadIni(iNIFile, "Scenario", "ReportFolder", "");
			screenshotsCounter = Integer
					.parseInt(IniUtilities.ReadIni(iNIFile, "resultTestdata", "screenshotsCounter", ""));
			screenshotsCounter = screenshotsCounter + 1;
			String ScreenshotName = "ScreenShot_" + screenshotsCounter + ".png";
			String sScreenShotPath = resultfolderpath + File.separator + ScreenshotName ;

			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			// The below method will save the screen shot in the drive with name
			// "screenshot.png"
			
			FileUtils.copyFile(scrFile, new File(sScreenShotPath));
			IniUtilities.WriteIni(iNIFile, "resultTestdata", "screenshotsCounter", screenshotsCounter);
			Report.addScreenCaptureFromPath(ScreenshotName);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	public void setInput(String objectXPath, String inputText) {
		WebElement element = null;
		try {
			element = getElement(objectXPath);
			element.click();
			element.clear();
			element.sendKeys(inputText);
			if (objectXPath.contains("password")) {
				inputText = "******";
			}
			Report.logMessage("Pass", "Entering value in [" + objectXPath + "] as [" + inputText + "]");
		} catch (Exception ex) {
			Report.logMessage("Fail", "Entering value in [" + objectXPath + "] is failed");
			Assert.assertTrue(false, ex.getMessage());
		}
	}

	/**
	 * @author anshulmadan
	 * @Description: select the drop down value in drop down list
	 * @param objectXPath
	 *            object tag name in OR.xml
	 * @param inputText
	 *            Value to select from drop down
	 * @return String value of element
	 */
	public void selectDropDown(String objectXPath, String inputText) {
		WebElement element = null;
		try {
			element = getElement(objectXPath);
			element.click();
			Select dropdown = new Select(element);
			dropdown.selectByVisibleText(inputText);
			Report.logMessage("Pass", "Select DropDown [" + objectXPath + "] value as [" + inputText + "]");
		} catch (Exception ex) {
			Report.logMessage("Fail", "Not able to select DropDown [" + objectXPath + "] value as [" + inputText + "]");
			Assert.assertTrue(false, ex.getMessage());
		}
	}

	
	
	/**
	 * @author anshulmadan
	 * @Description: select the drop down value in drop down list
	 * @param objectXPath
	 *            object tag name in OR.xml
	 * @param inputText
	 *            Value to select from drop down
	 * @return String value of element
	 */
	public String[] getDropDownValue(String objectXPath) {
		WebElement element = null;
		String[] DropdownValues = null;
		try {
			element = getElement(objectXPath);
			Select dropdown = new Select(element);
			List<WebElement> elements = dropdown.getOptions();
			DropdownValues = new String[elements.size()];
			for(int i= 0;i<elements.size();i++){
				DropdownValues[i]= (elements.get(i)).getText();
			}
			Report.logMessage("Pass", "DropDownValues are [" + objectXPath + "] value as " + DropdownValues);
		
		} catch (Exception ex) {
			Report.logMessage("Fail", "Not able to select DropDown [" + objectXPath + "] value as ");
			Assert.assertTrue(false, ex.getMessage());
		}
		return DropdownValues;
	}

	
	
	/**
	 * @author anshulmadan
	 * @Description: Get the Element value on screen using AccessibilityId,
	 *               xpath, index
	 * @param objectXPath
	 *            object tag name in OR.xml
	 * @return String value of element
	 */
	public String GetElementvalue(String objectXPath) {
		WebElement element = null;
		String sElement = null;
		try {
			element = getElement(objectXPath);
			sElement = element.getText();
			Report.logMessage("Pass", "Element value: " + sElement);
		} catch (Exception ex) {
			Report.logMessage("Fail", "Not able to find the element " + ex.getMessage());
			Assert.assertTrue(false, ex.getMessage());
		}

		return sElement;
	}
	
	/**
	 * @author anshulmadan
	 * @Description: Get the Element value on screen using AccessibilityId,
	 *               xpath, index
	 * @param objectXPath
	 *            object tag name in OR.xml
	 * @return String value of element
	 */
	public String GetElementAttributeValue(WebElement element,String attribute ) {
		
		String sElement = null;
		try {
			sElement = element.getAttribute(attribute);
			Report.logMessage("Pass", "Element attribute value: " + sElement);
		} catch (Exception ex) {
			Report.logMessage("Fail", "Not able to find the element " + ex.getMessage());
			Assert.assertTrue(false, ex.getMessage());
		}

		return sElement;
	}
	

	/**
	 * @author anshulmadan
	 * @Description: Click the button on screen using AccessibilityId, xpath,
	 *               index
	 * @param sAccessibilityId
	 *            AccessibilityId of the element
	 * @return return the boolean value True or false
	 */
	public boolean clickButtonByText(String objectXPath, String text) {
		boolean bClick = false;
		try {
			HashMap<String, String> Elementproperties = getXMLContent(objectXPath);
			if (Elementproperties.containsKey("Xpath")) {
				String xPath = Elementproperties.get("Xpath");
				xPath = xPath.replace("Changetext", text);
				driver.findElement(By.xpath(xPath)).click();
				bClick = true;
				Report.logMessage("Pass", "Clicking on [" + text + "]");
			}
			return bClick;
		} catch (Exception ex) {
			Report.logMessage("Fail", "not able to Click on [" + text + "]");
			Assert.assertTrue(false, ex.getMessage());
		}
		return bClick;
	}
	
	
	
	/**
	 * @author anshulmadan
	 * @Description: Verify element byt text and xpath,
	 *               index
	 * @param sAccessibilityId
	 *            AccessibilityId of the element
	 * @return return the boolean value True or false
	 */
	public boolean verifyElementByText(String objectXPath, String text) {
		boolean bClick = false;
		try {
			HashMap<String, String> Elementproperties = getXMLContent(objectXPath);
			if (Elementproperties.containsKey("Xpath")) {
				String xPath = Elementproperties.get("Xpath");
				xPath = xPath.replace("Changetext", text);
				bClick = driver.findElement(By.xpath(xPath)).isDisplayed();
				if(bClick){
					Report.logMessage("Pass", "Verify element by text:[" + text + "] is exist");
				}	
			}
			return bClick;
		} catch (Exception ex) {
			Report.logMessage("Fail", "not able to Click on [" + text + "]");
			Assert.assertTrue(false, ex.getMessage());
		}
		return bClick;
	}

	/**
	 * @author anshulmadan
	 * @Description: Accept or reject the alert
	 * @param sType
	 *            Accept or reject of alert type method
	 */
	public String AlertAccRej(String sType) {
		String sAlerMessage = null;
		try {
			// Thread.sleep(1000);
			// Navigate to 1st item

			sAlerMessage = driver.switchTo().alert().getText();
			Report.logMessage("Pass", "Alert message: " + sAlerMessage);
			if (sType.contains("Accept")) {
				driver.switchTo().alert().accept();
			}
			if (sType.contains("Dismiss")) {
				driver.switchTo().alert().dismiss();
			}
			return sAlerMessage;
		} catch (Exception ex) {
			Assert.assertTrue(false, ex.getMessage());
		}
		return sAlerMessage;
	}

	/**
	 * @author anshulmadan
	 * @Description: Scroll UpDown the Page with direct
	 * @param direction
	 *            First Object from want to scroll
	 * @param objectXPath
	 *            First object to want to scroll
	 */
	public void ScrollUPDownPage(String direction, String objectXPath) {
		WebElement element = null;
		WebElement ElementStart = null;
		try {
			Thread.sleep(1000);
			element = getElement(objectXPath);

			Actions action = new Actions(driver);
			int startX = ElementStart.getLocation().getX() + 20;
			int startY = ElementStart.getLocation().getY() + 20;
			int iDirection;
			if (direction.contains("UP")) {
				iDirection = -255;
			} else {
				iDirection = 250;
			}
			int endX = startX + iDirection;
			int endY = startY + iDirection;
//				action.clickAndHold(startX,startY).moveByOffset(endX, endY)
//				action.
//			action.press(new PointOption().withCoordinates(startX, startY))
//					.moveTo(new PointOption().withCoordinates(endX, endY)).release().perform();
			// tAction.tap(TapOptions().)
			// tAction.press(startX, startY).moveTo(endX,
			// endY).release().perform();
		} catch (Exception ex) {
			Assert.assertTrue(false, ex.getMessage());
		}
	}

	/**
	 * @author anshulmadan
	 * @Description: Quit the driver
	 * @param element
	 *            Element By id/name
	 */
	public void driverQuit() {
		driver.quit();
	}

	public void setCoordinate(WebElement element){
		try{
			Coordinates coordinate = ((Locatable) element).getCoordinates();
			coordinate.onPage();
			coordinate.inViewPort();
		}catch(Exception ex){
			
		}
	}
	public String readToastMsg() throws TesseractException
	{
	//	String imageScrShot= takeScreenshots(driver);
		getScreenshot();
	 String iNIFile = "src/main/resources/ResultConfig.ini";
	String 	resultfolderpath = IniUtilities.ReadIni(iNIFile, "Scenario", "ReportFolder", "");
	String screenshotsCounter = IniUtilities.ReadIni(iNIFile, "resultTestdata", "screenshotsCounter", "");
	String sScreenShotPath = resultfolderpath + File.separator + "ScreenShot_" + screenshotsCounter + ".png";
		String result = null;
		File imagefile= new File(sScreenShotPath);
		ITesseract instance = new Tesseract();
		
		File TestDataFolder= net.sourceforge.tess4j.util.LoadLibs.extractTessResources("tessdata");
		
		instance.setDatapath(TestDataFolder.getAbsolutePath());
		 result=instance.doOCR(imagefile);
		System.out.println(result);
		return result;				
	}
}
