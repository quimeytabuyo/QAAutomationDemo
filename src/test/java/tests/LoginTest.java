package tests;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.LoginPage;
import utils.BaseTest;


public class LoginTest extends BaseTest{
	public LoginPage loginPage;
	
	
	@BeforeMethod
	public void beforeMethod(ITestResult result){	
		this.auditBegin(result);
	}
	@Test
	@Parameters({"username","password"})
	public void loginValido_01(String username, String password) {
		loginPage = new LoginPage(this.driver);
		
		this.audit("Ingresar usuario: " + username);
		loginPage.ingresarUser(username);
		
		this.audit("Ingresando contraseña");
		loginPage.ingresarPassword(password);
		loginPage.clickLoginButton();
		Assert.assertTrue(this.driver.getCurrentUrl().contains("inventory"));
	}
	
	@Test
	@Parameters({"username","password"})
	public void loginFallido_02(String username, String password) {
		loginPage = new LoginPage(this.driver);
		
		this.audit("Ingresar usuario: " + username);
		loginPage.ingresarUser(username);
		
		this.audit("Ingresando contraseña");
		loginPage.ingresarPassword(password);
		loginPage.clickLoginButton();
		Assert.assertTrue(this.driver.getCurrentUrl().contains("inventory"));
	}
	
	@AfterMethod
	public void tearDown (ITestResult result) {
    	this.auditClose(result, this.driver);
		this.driver.quit();
	}
}
