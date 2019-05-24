package com.Project.Selenium.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.ini4j.Ini;

public class IniUtilities {
	

	/**
	 * @author anshulmadan
	 * @Description: Read the ini file
	 * @param filePath	Ini file path 
	 * @param section Section in the ini file
	 * @param key  	   element to get the value
	 * @param value  it is blank in read ini
	 * @return It return the hash map containing the object properties
	 */
	public static String ReadIni(String filePath, String section, String key, String value) {
		try {
			Ini iniReadObj = new Ini();
			FileInputStream inputReadFile = new FileInputStream(filePath);
			iniReadObj.load(inputReadFile);
			String Value = iniReadObj.get(section).get(key);
			return Value;
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * @author anshulmadan
	 * @Description: Write the ini file
	 * @param filePath	Ini file path 
	 * @param section Section in the ini file
	 * @param key  	   element to write the value
	 * @param value  value to write (if value is string)
	 * @return It return the hash map containing the object properties
	 */
	public static boolean WriteIni(String filePath, String section, String key, String value) {
		try {
			Ini iniReadObj = new Ini();
			FileInputStream inputReadFile = new FileInputStream(filePath);
			iniReadObj.load(inputReadFile);
			iniReadObj.put(section, key, value);
			iniReadObj.store(new FileOutputStream(filePath));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @author anshulmadan
	 * @Description: Write the ini file
	 * @param filePath	Ini file path 
	 * @param section Section in the ini file
	 * @param key  	   element to write the value
	 * @param value  value to write (if value is int)
	 * @return It return the hash map containing the object properties
	 */
	public static boolean WriteIni(String filePath, String section, String key, int value) {
		try {
			Ini iniReadObj = new Ini();
			FileInputStream inputReadFile = new FileInputStream(filePath);
			iniReadObj.load(inputReadFile);
			iniReadObj.put(section, key, value);
			iniReadObj.store(new FileOutputStream(filePath));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
