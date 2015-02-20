package com.aconex.core.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import com.aconex.core.utils.DriverManager;
import com.aconex.core.utils.PropertyReader;

public class TasksPage extends LoadableComponent<TasksPage> {

	private static String pageUrl;
	private static WebDriver driver;

	@FindBy(how=How.XPATH, using="//div[@class='nav-barRow']")
	WebElement navigationBar;

	@FindBy(how=How.ID, using="summaryViewPageheader")
	WebElement headerText;

	@FindBy(how=How.ID, using="frameMain")
	WebElement mainFrame;

	public TasksPage(){
		this.driver=DriverManager.getDriver();
		this.pageUrl=PropertyReader.getAppUrl();
		PageFactory.initElements(driver, this);
		this.get();
	}

	@Override
	protected void load(){
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.get(pageUrl);
	}

	@Override
	protected void isLoaded() {
		driver.switchTo().frame(mainFrame);
	}

	public String getTitle() {
		driver.switchTo().frame(mainFrame);
		System.out.println("*************--"+ driver.getPageSource()+"--*************");
		return "";
//				mainFrame.getText();
	}

}

