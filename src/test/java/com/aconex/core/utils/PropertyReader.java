package com.aconex.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PropertyReader {
	public static String browserName;
	public static String appUrl;
	public static String ieDriverPath;
	public static String chromeDriverPath;

	static
	{
		try {
			BufferedReader reader = new BufferedReader(new FileReader("./src/test/resources/config.properties"));
			String currentLine;
			while((currentLine = reader.readLine())!=null){
				if(!currentLine.startsWith("#")){ //ignore commented lines
					String variable = currentLine.substring(0, currentLine.indexOf("="));
					String value=currentLine.substring(currentLine.indexOf("=")+1, currentLine.length());
					initializeVar(variable,value);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void initializeVar(String variable, String value) {
		switch(variable){
		case "browserName": setBrowserName(value); break;
		case "appUrl": setAppUrl(value); break;
		case "webdriver.ie.driver": setIeDriverPath(value); break;
		case "webdriver.chrome.driver": setChromeDriverPath(value); break;
		default: System.out.println("Invalid property name encountered. Please check the property name"); 
		}
	}

	public static String getIeDriverPath() {
		return ieDriverPath;
	}

	private static void setIeDriverPath(String ieDriverPath) {
		try {
			PropertyReader.ieDriverPath = new File(ieDriverPath).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getChromeDriverPath() {
		return chromeDriverPath;
	}

	private static void setChromeDriverPath(String chromeDriverPath) {
		try {
			PropertyReader.chromeDriverPath = new File(chromeDriverPath).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getBrowserName() {
		return browserName;
	}

	private static void setBrowserName(String browserName) {
		PropertyReader.browserName = browserName;
	}
	
	public static String getAppUrl() {
		return appUrl;
	}

	private static void setAppUrl(String appUrl) {
		PropertyReader.appUrl = appUrl;
	}

}
