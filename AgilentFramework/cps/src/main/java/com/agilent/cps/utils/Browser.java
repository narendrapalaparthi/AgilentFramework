package com.agilent.cps.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Browser{
	
	public static ElementManager elementManager = ElementManager.getInstance();
	
	/*
	 * To Initiate and Load URL into Browser
	 */
	public void startBrowser(String startUrl)
	{
		WebDriver driver = initializeBrowser();
		System.out.println("Browser launched Successfully");
		elementManager.setActiveDriver(driver);
		driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(Configuration.pageLoadTimeout), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Configuration.implicitWait), TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(startUrl);
		System.out.println("Browser loaded with url : "+startUrl);
	}
	
	/*
	 * To Load URL into existing browser
	 */
	public void loadUrl(String loadUrl)
	{
		elementManager.getActiveDriver().navigate().to(loadUrl);
	}
	
	/*
	 * To Initialize Browser based on Browser selected in Setup Properties File
	 */
	private WebDriver initializeBrowser()
	{
		String browser = Configuration.browser;
		switch (browser) {
		case "Internet Explorer":
			return getIEDriver();
			
		case "Chrome":
			return getChromeDriver();
			
		case "Edge":
			return getEdgeDriver();
			
		case "Mobile":
			return getAndroidDriver();

		default:
			return getFFDriver();
		}
	}

	public WebDriver getFFDriver()
	{
		WebDriver driver = null;
		if(null == System.getProperty("webdriver.gecko.driver"))
			System.setProperty("webdriver.gecko.driver", Configuration.firefoxDriver);
		if(Boolean.parseBoolean(Configuration.isRCServer))
		{
			DesiredCapabilities DC_FF = DesiredCapabilities.firefox();
			DC_FF.setJavascriptEnabled(true);
			String url = "http://localhost:"+Configuration.port+"/wd/hub";
			
			try {
				driver = new RemoteWebDriver(new URL(url), DC_FF);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		else
			driver = new FirefoxDriver();
			
		return driver;
	}

	public WebDriver getIEDriver()
	{
		WebDriver driver = null;
		if(null == System.getProperty("webdriver.ie.driver"))
			System.setProperty("webdriver.ie.driver", Configuration.ieDriver);
		if(Boolean.parseBoolean(Configuration.isRCServer))
		{
			DesiredCapabilities DC_IE = DesiredCapabilities.internetExplorer();
			DC_IE.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			DC_IE.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
			DC_IE.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			DC_IE.setCapability("ignoreProtectedModeSettings",true);
			String url = "http://localhost:"+Configuration.port+"/wd/hub";
			
			try {
				driver = new RemoteWebDriver(new URL(url), DC_IE);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		else
			driver = new InternetExplorerDriver();
		return driver;
	}
	
	public WebDriver getEdgeDriver()
	{
		WebDriver driver = null;
		if(null == System.getProperty("webdriver.edge.driver"))
			System.setProperty("webdriver.edge.driver", Configuration.edgeDriver);
		if(Boolean.parseBoolean(Configuration.isRCServer))
		{
			DesiredCapabilities DC_Edge = DesiredCapabilities.edge();
			DC_Edge.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			DC_Edge.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
			DC_Edge.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			DC_Edge.setCapability("ignoreProtectedModeSettings",true);
			String url = "http://localhost:"+Configuration.port+"/wd/hub";
			
			try {
				driver = new RemoteWebDriver(new URL(url), DC_Edge);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		else
			driver = new EdgeDriver();
		return driver;
	}
	
	public WebDriver getChromeDriver()
	{
		WebDriver driver = null;
		if(null == System.getProperty("webdriver.chrome.driver"))
			System.setProperty("webdriver.chrome.driver", Configuration.chromeDriver);
		
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("safebrowsing.enabled", "true");
		chromePrefs.put("download.default_directory", System.getProperty("user.dir")+"\\metadata");
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--no-sandbox");
		DesiredCapabilities DC_Chrome = DesiredCapabilities.chrome();
		DC_Chrome.setCapability("enableNativeEvents", true);
		DC_Chrome.setCapability(ChromeOptions.CAPABILITY, options);
		if(Boolean.parseBoolean(Configuration.isRCServer))
		{
			String url = "http://localhost:"+Configuration.port+"/wd/hub";
			
			try {
				driver = new RemoteWebDriver(new URL(url), DC_Chrome);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		else
			driver = new ChromeDriver(DC_Chrome);
			
		return driver;
	}
	
	public WebDriver getAndroidDriver()
	{
		WebDriver driver = null;
//		DesiredCapabilities DC_Android = DesiredCapabilities.android();
//		DC_Android.setJavascriptEnabled(true);
////		DC_Android.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
//		DC_Android.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
//		DC_Android.setCapability(MobileCapabilityType.DEVICE_NAME, "My Phone");
		
		if(null == System.getProperty("webdriver.chrome.driver"))
			System.setProperty("webdriver.chrome.driver", Configuration.chromeDriver);
		
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("androidPackage","com.android.chrome");
		
		try {
			driver = new ChromeDriver(chromeOptions);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}

}
