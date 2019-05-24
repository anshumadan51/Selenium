Feature: Verify Contact US Page

	
	Scenario: Verify Mandatory Fields for contact us
		Given User open "Chrome" browser
		And Open URL
		When User Click on "HomePage/ContactUs" button
		When User Click on "ContactUsPage/SubmitButton" button
		And User wait for "1000"
		Then User verify error toast message "Please select an item in the list."
		And User get the list of mandatory fields "ContactUsPage/MandatoryDropDown"
		And User get the list of mandatory fields "ContactUsPage/MandatoryInputBox"
		

	Scenario: Verify initial value of Select Sub Product field 
		Then User verify field "ContactUsPage/SubProduct" value "-- Select Sub Product --"
		And User take screenshots
		
		
		
			
	Scenario: Verify <I am looking to> field dropDown values 
		And User verify drop Down "ContactUsPage/LookingTo" values from excel "Items"
		And User take screenshots
		
		
	Scenario: Verify SubProduct DropDown Value
		And User select dropDown "ContactUsPage/Product" value "Loans"
		And User verify drop Down "ContactUsPage/SubProduct" values from excel "Items"
		And User verify total drop Down "ContactUsPage/SubProduct" value "7"
		And User take screenshots
		
		
	Scenario: Verify Mobile Number input fields 
		And User enter value in field "ContactUsPage/Mobile" from excel "MobileNumber1"
		Then User verify element "ContactUsPage/MobileNumberErrorMessage" on screen
		And User take screenshots
		And User wait for "2000"
		And User enter value in field "ContactUsPage/Mobile" from excel "MobileNumber2"
		Then User verify element "ContactUsPage/MobileNumberErrorMessageHide" on screen
		And User take screenshots
		And User Close the browser
		
	
	
		
	