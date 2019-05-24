
Feature: HomePage Validations

	
	Scenario: Verify All items on Navigation bar
		Given User open "Chrome" browser
		And Open URL
		Then User verify element "HomePage/logoImage" on screen
		And User Verify Items on Navigation Bar "HomePage/NavigationBaritems" from excel "Items"
		And User take screenshots
		
	Scenario: Verify News Box
		Then User verify element "HomePage/NewsBox" on screen
		And User take screenshots
	
	
	Scenario: Verify Contact Us link
		Then User verify element "HomePage/ContactUs" on screen
		When User Click on "HomePage/ContactUs" button
		And User wait for "3000"
		Then User verify element "ContactUsPage/WelcomeMessage" on screen
		And User take screenshots
		And User Close the browser