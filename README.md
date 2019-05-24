# Selenium
Automation Framework for Selenium with cucumber

# Test cases
1. Scenario: Verify Home page
  i)Verify All items on Navigation bar
  ii)Verify News Box
  iii)Verify Contact Us link
2. Scenario: Verify Contact US Page
  i) Verify Mandatory Fields for contact us
  ii)Verify initial value of Select Sub Product field
  iii)Verify <I am looking to> field dropDown values
  iv)Verify SubProduct DropDown Value
  v)Verify Mobile Number input fields 

# Installation Pre-requisites:
Install java, Eclipse ,TestNg and Maven.

# Steps to execute the project

## Using Eclipse
1. Open project in Eclipse
2. Right Click on Pom.xml and Select Run As and select Maven Test

## Using CMD
1. Open cmd and go to POM.Xml path
4. Enter command 'mvn test'

# Reports
Report will be generated with latest date and time folder in the following folder: target\cucumber-reports.
Complete logs will be saved in src\main\resources\logfile.txt
