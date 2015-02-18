package com.aconex.core.pages;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

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
		driver.switchTo().frame(mainFrame);
		if(!headerText.isDisplayed()){
			driver.findElement(By.id("nav-bar-TASK")).click();
		}
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(headerText.isDisplayed());
	}

	public void validateTasksPage() {
		Assert.assertTrue(navigationBar.isDisplayed());
		driver.switchTo().frame(mainFrame);
		Assert.assertEquals(headerText.getText(), "My Tasks","Expected: "+"My Tasks"+ "but found: "+ headerText.getText());
	}

}

