package tests;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.BaseTest;

public class ServiceWebTest extends BaseTest{
	@BeforeMethod
	public void beforeMethod(ITestResult result){	
		this.auditBegin(result);
	}
	
	@Test
	public void verificarDepartamentosMercadoLibre_05() {
 	CloseableHttpClient httpClientCustom = HttpClients.custom().disableCookieManagement().build();

 	HttpGet request = new HttpGet("https://www.mercadolibre.com.ar/menu/departments");
 	HttpResponse response;
 	try {
 		response = httpClientCustom.execute(request);
 		HttpEntity entity = response.getEntity();
 		String departmentsResponse = EntityUtils.toString(entity);
 		Assert.assertTrue(departmentsResponse.contains("departments"), "No se encuentran departamentos");
	} catch (ClientProtocolException e) {
		e.printStackTrace();
		throw new AssertionError("No se encuentran departamentos");
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	}
	
	@AfterMethod
	public void tearDown (ITestResult result) {
    	this.auditClose(result, this.driver);
		this.driver.quit();
	}
}
