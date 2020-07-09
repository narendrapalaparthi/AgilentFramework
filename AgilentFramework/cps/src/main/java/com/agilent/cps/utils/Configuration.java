package com.agilent.cps.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class Configuration {
	
	public static String browser = "firefox";
	public static String chromeDriver;
	public static String firefoxDriver;
	public static String ieDriver;
	public static String edgeDriver;
	public static String isRCServer = "false";
	public static String port = "4444";
	public static String pageLoadTimeout = "180";
	public static String implicitWait = "5";
	public static String logPath;
	public static String logName = "AdfLog";
	public static String timeStamp = "fasle";
	public static String success_robot = "false";
	public static String success_scroll = "false";
	public static String failure_robot = "false";
	public static String failure_scroll = "true";
	public static String error_robot = "false";
	public static String error_scroll = "true";
	public static String templateFile;
	public static String openLogFile = "true";
	
	
	public Configuration()
	{
		try {
			assignconfigArguments();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void assignconfigArguments() throws IOException {
		File file = new File("config/setup.properties");
		FileInputStream fis = new FileInputStream(file);
		Properties config = new Properties();
		config.load(fis);
		if(null != config.getProperty(Constants.browser))
			browser = config.getProperty(Constants.browser);
		if(null != config.getProperty(Constants.CHROME_DRIVER))
			chromeDriver = config.getProperty(Constants.CHROME_DRIVER);
		if(null != config.getProperty(Constants.FIREFOX_DRIVER))
			firefoxDriver = config.getProperty(Constants.FIREFOX_DRIVER);
		if(null != config.getProperty(Constants.IE_DRIVER))
			ieDriver = config.getProperty(Constants.IE_DRIVER);
		if(null != config.getProperty(Constants.EDGE_DRIVER))
			edgeDriver = config.getProperty(Constants.EDGE_DRIVER);
		if(null != config.getProperty(Constants.isRCServer))
			isRCServer = config.getProperty(Constants.isRCServer);
		if(null != config.getProperty(Constants.port))
			port = config.getProperty(Constants.port);
		if(null != config.getProperty(Constants.pageLoadTimeout))
			pageLoadTimeout = config.getProperty(Constants.pageLoadTimeout);
		if(null != config.getProperty(Constants.implicitWait))
			implicitWait = config.getProperty(Constants.implicitWait);
		if(null != config.getProperty(Constants.logPath))
			logPath = config.getProperty(Constants.logPath)+"\\Adflogs\\"+(new SimpleDateFormat("MM-dd-yyyy").format(System.currentTimeMillis()));
		else
			logPath = System.getProperty("user.dir")+"\\target\\Adflogs\\"+(new SimpleDateFormat("MM-dd-yyyy").format(System.currentTimeMillis()));
		if(null != config.getProperty(Constants.logName))
			logName = config.getProperty(Constants.logName);
		if(null != config.getProperty(Constants.timeStamp))
			timeStamp = config.getProperty(Constants.timeStamp);
		if(Boolean.parseBoolean(timeStamp))
			logName +="_"+System.currentTimeMillis();
		if(null != config.getProperty(Constants.success_robot))
			success_robot = config.getProperty(Constants.success_robot);
		if(null != config.getProperty(Constants.success_scroll))
			success_scroll = config.getProperty(Constants.success_scroll);
		if(null != config.getProperty(Constants.failure_robot))
			failure_robot = config.getProperty(Constants.failure_robot);
		if(null != config.getProperty(Constants.failure_scroll))
			failure_scroll = config.getProperty(Constants.failure_scroll);
		if(null != config.getProperty(Constants.error_robot))
			error_robot = config.getProperty(Constants.error_robot);
		if(null != config.getProperty(Constants.error_scroll))
			error_scroll = config.getProperty(Constants.error_scroll);
		if(null != config.getProperty(Constants.openLogFile))
			openLogFile = config.getProperty(Constants.openLogFile);
		if(null != config.getProperty(Constants.templateFile))
			templateFile = config.getProperty(Constants.templateFile);
	}

}
 