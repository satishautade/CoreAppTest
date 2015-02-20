package com.aconex.core.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aconex.core.utils.DriverManager;
import com.aconex.core.utils.PropertyReader;


public class LoginPage extends LoadableComponent<LoginPage>{

	private static String appUrl;
	private static WebDriver driver;

	@FindBy(how=How.ID, using="userName")
	WebElement inputUserName;
	
	@FindBy(how=How.ID, using="password")
	WebElement inputPassword;
	
	@FindBy(how=How.ID, using="login")
	WebElement buttonLogin;

	public LoginPage(){
		this.driver=DriverManager.getDriver();
		this.appUrl=PropertyReader.getAppUrl();
		PageFactory.initElements(driver, this);
		this.get();
	}
	
	@Override
	protected void load(){
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.get(appUrl);
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void isLoaded() throws Error {
		String currentUrl=driver.getCurrentUrl();
		if(!currentUrl.equalsIgnoreCase(appUrl)){
			driver.get(appUrl);
		}
	}
	
	public void quit() {
		driver.close();
		driver.quit();
	}

	public LoginPage typeUserName(String userName){
		new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.id("userName")));
		inputUserName.sendKeys(userName);
		return this;
	}
	
	public LoginPage typePassword(String password){
		inputPassword.sendKeys(password);
		return this;
	}
	
	public LoginPage doLoginExpectingFailure(){
		buttonLogin.click();
		return this;
	}

	public void verifyErrorMessage(String expecterMessageText) throws InterruptedException {
		String actualMessage = driver.findElement(By.xpath("//ul[@class='messagePanel']")).getText();
		Assert.assertTrue(actualMessage.contains(expecterMessageText),"Expected:"+expecterMessageText+"but found:"+actualMessage);
	}

	public TasksPage doLoginExpectingSuccess() throws InterruptedException {
		buttonLogin.click();
		Thread.sleep(5000);
		return PageFactory.initElements(driver, TasksPage.class);
	}
}
