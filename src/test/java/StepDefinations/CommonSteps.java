package StepDefinations;

import cucumber.api.java.en.*;
import net.sourceforge.tess4j.TesseractException;

import java.io.IOException;
import java.net.MalformedURLException;

import org.testng.Assert;

import com.Project.Selenium.Drivers.Base;
import com.Project.Selenium.utilities.Excel;
import com.Project.Selenium.utilities.IniUtilities;


public class CommonSteps extends Base{

	@Given("User open \"(.*)\" browser")
	public void browser(String browser) throws MalformedURLException{
		System.out.println("in features file");
		invokeBrowser(browser);
	}
	
	@And("Open URL")
	public void openURL() throws IOException{
		String URL = getConfigurationProperties("URL");
		openURL(URL);
	}
	
	@When("User Click on \"(.*)\" button")
	public void ClickOnButton(String element){
		clickButton(element);
		System.out.println("in features file" + element);
		
	}
	
	@Then("User verify element \"(.*)\" on screen")
	public void verifyElement(String element){
		boolean bExist = verifyElementIsDisplayed(element);
		Assert.assertTrue(bExist, "Vreify Element: "+ element+" exist on screen");
	}
	
	@And("User take screenshots")
	public void UsergetScreenShots(){
		getScreenshot();
	}
	
	@And("User wait for \"(.*)\"")
	public void Userwait(String userWait) throws InterruptedException{
		
		Thread.sleep(Integer.parseInt(userWait));
	}
	
	@And("User select dropDown \"(.*)\" value \"(.*)\"")
	public void selectDropDownValue(String objectXpath,String value) {
		try{
			selectDropDown(objectXpath,value);
		}catch(Exception ex){
			Assert.assertTrue(false, ex.getMessage());
		}
	}
	
	@And("User enter value in field \"(.*)\" from excel \"(.*)\"")
	public void EnterInputfromExcel(String objectXpath,String excelColumn) {
		try{
			Excel ObjExcel = new Excel(getExcelProperties("ExcelDataPath"));
			String TestCaseName = IniUtilities.ReadIni(iNIFile, "Scenario", "CurrentScenarioValue", "");
			String value = ObjExcel.getColData(TestCaseName, excelColumn);
			setInput(objectXpath, value);
		}catch(Exception ex){
			Assert.assertTrue(false, ex.getMessage());
		}
	}
	
	@Then("User verify error toast message \"(.*)\"")
	public void verifyToastMessage(String errorMessage) throws TesseractException{
		boolean errorMessageExist = false;
		String actualMessage = readToastMsg();
		if(actualMessage.contains(errorMessage)){
			errorMessageExist = true;
		}
		Assert.assertTrue(errorMessageExist, "Verify Toast message: "+errorMessage+ " is exist" );;
	}
	@And("User Close the browser")
	public void closeBrowsers() {
		try{
			driverQuit();
		}catch(Exception ex){
			Assert.assertTrue(false, ex.getMessage());
		}
	}
}
