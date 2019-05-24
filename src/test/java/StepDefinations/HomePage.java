package StepDefinations;

import java.io.IOException;

import org.testng.Assert;

import com.Project.Selenium.Drivers.Base;
import com.Project.Selenium.utilities.Excel;
import com.Project.Selenium.utilities.IniUtilities;

import cucumber.api.java.en.And;

//import org.testng.Assert;

import cucumber.api.java.en.Then;

public class HomePage extends Base {

	
	@Then("Verify user at HomePage")
	public void verifyHomePage(){
		boolean bExist = verifyElementIsDisplayed("HomePage/logoImage");
		Assert.assertTrue(bExist, "HomePage is Open");
	}
	
	
	@And("User Verify Items on Navigation Bar \"(.*)\" from excel \"(.*)\"")
	public void verifyNavigationBarItems(String ObjectXpath,String excelColumn) throws IOException{
		boolean elementExist;
		Excel ObjExcel = new Excel(getExcelProperties("ExcelDataPath"));
		String TestCaseName = IniUtilities.ReadIni(iNIFile, "Scenario", "CurrentScenarioValue", "");
		String items = ObjExcel.getColData(TestCaseName, excelColumn);
		String[] asVerifyText = items.split(";");
		for(int i = 0;i<asVerifyText.length;i++){
			elementExist = false;
			elementExist = verifyElementByText(ObjectXpath,asVerifyText[i]);
			Assert.assertTrue(elementExist, "Verify item: "+ asVerifyText[i] + "is exist");
		}
	}
	
}
