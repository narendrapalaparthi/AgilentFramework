package com.agilent.cps.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FieldActions {
	public Map<String, Method> methodsMap = new  HashMap<String, Method>();
	
	public static final FieldActions fieldActions = new FieldActions();
	public static ElementManager elementManager = ElementManager.getInstance();
	
	/*
	 * Singleton Implementation
	 */
	public static FieldActions getInstance()
	{
		return fieldActions;
	}

	public void textField(String fieldName, String fieldValue) {
		System.out.println();
		System.out.println("Inside Text");
		System.out.println();
		if (!("".equals(fieldValue) || "null".equals(fieldValue)|| "EMPTY".equals(fieldValue))){
			WebElement element = elementManager.getWebElement(fieldName, "Text", true);
			System.out.println("WebElement created");
			if(!"true".equals(element.getAttribute("readonly"))){
				if("".equals(fieldValue.trim())){
					element.clear();
					System.out.println("Text cleared");
				}
				else{
					element.sendKeys(Keys.chord(Keys.CONTROL, "a"), fieldValue+Keys.TAB);
					System.out.println("Text entered in Text field");
				}
			}
		}
		else
			System.out.println("No Action Performed as Field Value is : "+fieldValue);

	}
	
	public void TextArea(String fieldName, String fieldValue) {
		System.out.println("Inside TextArea");
		if (!("".equals(fieldValue) || "null".equals(fieldValue)|| "EMPTY".equals(fieldValue))){
			WebElement element = elementManager.getWebElement(fieldName, "TextArea", true);
			System.out.println("WebElement created");
			element.clear();
			System.out.println("Text cleared");
			element.sendKeys(fieldValue+Keys.TAB);
			System.out.println("Text entered in Text Area");
		}
		else
			System.out.println("No Action Performed as Field Value is : "+fieldValue);
	}
	
	public void RadioButtonSingleSelect(String fieldName, String fieldValue) {
		System.out.println("Inside RadioButtonSingleSelect");
		if (!("".equals(fieldValue) || "null".equals(fieldValue)|| "EMPTY".equals(fieldValue))){
			WebElement element = elementManager.getWebElement("//fieldset[@id='"+fieldName+"']", "RadioButtonSingleSelect", false);
			System.out.println("WebElement created");
			List<WebElement> inputElements = element.findElements(By.tagName("input"));
			if(Pattern.matches("[0-9]*", fieldValue))
				elementManager.getJSExecutor().executeScript("arguments[0].click();",inputElements.get(Integer.parseInt(fieldValue)));
			else
				for(WebElement ele : inputElements)
				{
					if(fieldValue.equals(ele.getAttribute("value")) || fieldValue.equals(ele.getAttribute("title"))){
						elementManager.getJSExecutor().executeScript("arguments[0].click();",ele);
						break;
					}
				}
			
			System.out.println("Radio Button Clicked");
		}
		else
			System.out.println("No Action Performed as Field Value is : "+fieldValue);
	}
	
	public void Radio(String fieldName, String fieldValue) {
		System.out.println("Inside Radio");
		if (!("".equals(fieldValue) || "null".equals(fieldValue)|| "EMPTY".equals(fieldValue))){
			int index = fieldName.lastIndexOf("_");
			String identifier = "//input[@name='"+fieldName.substring(0,index)+"' and @value='"+fieldName.substring(index+1)+"']";
			elementManager.getWebElement(identifier, "Radio", false).click();
			System.out.println("Radio Button Clicked");
		}
		else
			System.out.println("No Action Performed as Field Value is : "+fieldValue);
	}
	
	public void CheckBox(String fieldName, String fieldValue) {
		System.out.println("Inside CheckBox");
		if (!("".equals(fieldValue) || "null".equals(fieldValue)|| "EMPTY".equals(fieldValue))){
			WebElement element = elementManager.getWebElement(fieldName, "CheckBox", true);
			System.out.println("WebElement created");
			if(element.isSelected() ^ Boolean.parseBoolean(fieldValue))
				element.click();
			System.out.println("Checkbox Checked");
		}else
			System.out.println("No Action Performed as Field Value is : "+fieldValue);
	}
	
	public void Dropdown(String fieldName, String fieldValue) {
		System.out.println("Inside Dropdown");
		if (!("".equals(fieldValue) || "null".equals(fieldValue)|| "EMPTY".equals(fieldValue))){
			WebElement element = elementManager.getWebElement(fieldName, "Dropdown", true);
			System.out.println("WebElement created");
			Select select = new Select(element);
			if(Pattern.matches("[0-9]*", fieldValue))
				select.selectByIndex(Integer.parseInt(fieldValue));
			else
				select.selectByVisibleText(fieldValue);
			System.out.println("Option Selected");
		}else
			System.out.println("No Action Performed as Field Value is : "+fieldValue);
	}
	
	public void MultiSelect(String fieldName, String fieldValue){
		System.out.println("Inside MultiSelect"+ "");
		if (!("".equals(fieldValue) || "null".equals(fieldValue)|| "EMPTY".equals(fieldValue))){
			WebElement element = elementManager.getWebElement(fieldName, "Dropdown", true);
			System.out.println("WebElement created");
			Select select = new Select(element);
			for(WebElement ele : select.getAllSelectedOptions())
				ele.click();
			element = elementManager.getWebElement(fieldName, "Dropdown", true);
			select = new Select(element);
			if(Pattern.matches("[0-9]*", fieldValue))
				select.getOptions().get(Integer.parseInt(fieldValue)).click();
			else
				select.selectByVisibleText(fieldValue);
			System.out.println("Option Selected");
		}else
			System.out.println("No Action Performed as Field Value is : "+fieldValue);
	}
	
	public void OptionSelector(String fieldName, String fieldValue){
		System.out.println("Inside OptionSelector"+ "");
		if (!("".equals(fieldValue) || "null".equals(fieldValue)|| "EMPTY".equals(fieldValue))){
			WebElement element = elementManager.getWebElement(fieldName+".Options", "Dropdown", true);
			System.out.println("WebElement created");
			Select select = new Select(element);
			if(Pattern.matches("[0-9]*", fieldValue))
				select.selectByIndex(Integer.parseInt(fieldValue));
			else
				select.selectByVisibleText(fieldValue);
			click("action."+fieldName+".OptionSelectorAdd");
			System.out.println("Option Selected");
		}else
			System.out.println("No Action Performed as Field Value is : "+fieldValue);
	}
	
	public void click(String fieldName){
		System.out.println("Inside Click");
		String elementLocator = "//input[@name='" + fieldName + "' or " + "@value='" + fieldName + "' or " + "@id='"
				+ fieldName + "' or @title='" + fieldName + "' ] | //button[contains(text(),'" + fieldName + "')]";
		WebElement element = elementManager.getWebElement(elementLocator, "Button", false);
		System.out.println("WebElement Created");
		element.click();
		System.out.println("Click Successful");
		elementManager.waitForPageToLoad();
	}
	
	public void clickButton(String fieldName){
		System.out.println("Inside clickButton");
		String identifier = "//button[contains(text(),'" + fieldName + "')]";
		WebElement element = elementManager.getWebElement(identifier, "Button", false);
		System.out.println("WebElement Created");
		element.click();
		System.out.println("Click Successful");
		elementManager.waitForPageToLoad();
	}
	
	public void clickLink(String fieldName){
		System.out.println("Inside clickLink");
		WebElement element = elementManager.getActiveDriver().findElement(By.linkText(fieldName));
		System.out.println("WebElement Created");
		element.click();
		System.out.println("Click Successful");
		elementManager.waitForPageToLoad();
	}
	
	public void type(String fieldName, String fieldValue){
		System.out.println("Inside type");
		if (!("".equals(fieldValue) || "null".equals(fieldValue)|| "EMPTY".equals(fieldValue))){
			WebElement element = elementManager.getWebElement(fieldName, "Text", true);
			System.out.println("WebElement Created");
			element.clear();
			System.out.println("Text Cleared");
			if (!("".equals(fieldValue) || "null".equals(fieldValue)|| "EMPTY".equals(fieldValue)))
				element.sendKeys(fieldValue+Keys.TAB);
			System.out.println("Text entered in Text field");
		}else
			System.out.println("No Action Performed as Field Value is : "+fieldValue);
	}
	
	public String getText(String fieldName, String fieldType, Boolean xpathConstructor){
		System.out.println("Inside getText");
		WebElement element = elementManager.getWebElement(fieldName, fieldType, false);
		System.out.println("WebElement Created");
		return element.getText();
	}
	
	public String getAttribute(String fieldName, String fieldType, Boolean xpathConstructor, String attribute){
		System.out.println("Inside getAttribute");
		WebElement element = elementManager.getWebElement(fieldName, fieldType, xpathConstructor);
		System.out.println("WebElement Created");
		return element.getAttribute(attribute);
	}
	
	public void sleep(double timeInSeconds)
	{
		elementManager.sleep(timeInSeconds);
	}
}
