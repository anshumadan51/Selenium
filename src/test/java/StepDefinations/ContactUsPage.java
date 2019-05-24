package StepDefinations;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.Project.Selenium.Drivers.Base;
import com.Project.Selenium.utilities.Excel;
import com.Project.Selenium.utilities.IniUtilities;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class ContactUsPage extends Base {
	
	public static String iNIFile = "src/main/resources/ResultConfig.ini";
	
	@Then("User verify field \"(.*)\" value \"(.*)\"")
	public void verifyInitialFieldValue(String objectXpath, String value) {
		String ActualValue = GetElementvalue(objectXpath);
		Assert.assertEquals(ActualValue, value, "Verify element:" + objectXpath + " value");
	}

	@And("User verify drop Down \"(.*)\" values from excel \"(.*)\"")
	public void VerifyDropDownValues(String objectXpath,String excelColumn) {
		try{
		Excel ObjExcel = new Excel(getExcelProperties("ExcelDataPath"));
		String TestCaseName = IniUtilities.ReadIni(iNIFile, "Scenario", "CurrentScenarioValue", "");
		String items = ObjExcel.getColData(TestCaseName, excelColumn);
		String[] expectedValues = items.split(";");
		String[] dorpDownvalues = getDropDownValue(objectXpath);
		for (int i = 0; i < expectedValues.length; i++) {
			boolean bExist = false;
			for (int j = 0; j < dorpDownvalues.length; j++) {
				if (expectedValues[i].equalsIgnoreCase(dorpDownvalues[j])) {
					bExist = true;
					break;
				}
			}
			Assert.assertTrue(bExist, "Verify DropDownValue: "+ expectedValues[i] + "is exist");
		}
		}catch(Exception ex){
			Assert.assertTrue(false, ex.getMessage());
		}
	}

	
	@And("User verify total drop Down \"(.*)\" value \"(.*)\"")
	public void VerifytotalDropDownValues(String objectXpath,String number) {
		try{
		String[] dorpDownvalues = getDropDownValue(objectXpath);
		Assert.assertEquals(dorpDownvalues.length,Integer.parseInt(number), "Verify total dropDown value: " + number +" is exist");
		}catch(Exception ex){
			Assert.assertTrue(false, ex.getMessage());
		}
	}
	
	@And("User get the list of mandatory fields \"(.*)\"")
	public void getMandatoryFields(String objectXpath) {
		try{
			List<WebElement> elements = getElementList(objectXpath);
				for(int i = 0;i<elements.size();i++){
					GetElementAttributeValue(elements.get(i),"id");
				}
			
		}catch(Exception ex){
			Assert.assertTrue(false, ex.getMessage());
		}
	}
	
}
