package com.aconex.core.tests;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aconex.core.pages.LoginPage;
import com.aconex.core.pages.TasksPage;

public class TestLogin {
	
	LoginPage loginPage;
	
	@BeforeSuite
	public void preCondition() throws InterruptedException{
		loginPage = new LoginPage();
	}
	
	@Test(groups="negative")
	public void testLoginErrorMessages() throws InterruptedException{
		
		//error message when either username or password is empty
		String checkUsernamePasswordMessage="Please enter your login name and password";

		loginPage.typeUserName("poleary").typePassword("").doLoginExpectingFailure();
		loginPage.verifyErrorMessage(checkUsernamePasswordMessage);
		loginPage.typeUserName("").typePassword("invalid").doLoginExpectingFailure();
		loginPage.verifyErrorMessage(checkUsernamePasswordMessage);
		
		//error message when either of the fields are incorrect: Your login name or password is incorrect.
		String incorrectUsernamePasswordMessage="Your login name or password is incorrect.";
		
		loginPage.typeUserName("poleary1").typePassword("invalid1").doLoginExpectingFailure();
		loginPage.verifyErrorMessage(incorrectUsernamePasswordMessage);
		
		//error message when password is incorrect for first time : Your login name or password is incorrect.
	    //Login names and passwords are case sensitive so check that "Caps Lock" is not on.
		String incorrectPasswordMessage="Login names and passwords are case sensitive so check that \"Caps Lock\" is not on.";
		loginPage.typeUserName("poleary").typePassword("invalid1").doLoginExpectingFailure();
		loginPage.verifyErrorMessage(incorrectPasswordMessage);
		
		//error message when password is incorrect for second and third time: Your login name or password is still incorrect.
	    //Login names and passwords are case sensitive so check that "Caps Lock" is not on.
		String incorrectPasswordMessage23 = "Have you forgotten your login / password?";
		loginPage.typeUserName("poleary").typePassword("invalid2").doLoginExpectingFailure();
		loginPage.verifyErrorMessage(incorrectPasswordMessage23);
		loginPage.typeUserName("poleary").typePassword("invalid3").doLoginExpectingFailure();
		loginPage.verifyErrorMessage(incorrectPasswordMessage23);

	}

	@Test(dependsOnGroups="negative")
	public void testSuccessfulLogin() throws InterruptedException{
		
		TasksPage tasksPage = loginPage.typeUserName("poleary").typePassword("ac0n3x72").doLoginExpectingSuccess();
		Thread.sleep(5000);
		Assert.assertEquals(tasksPage.getTitle(),"Status Page","Expected: "+"My Tasks"+ "but found: "+tasksPage.getTitle());

	}

	@AfterSuite
	public void postCondition(){
		loginPage.quit();
	}
}