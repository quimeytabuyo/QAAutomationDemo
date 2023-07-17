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
import org.testng.annotations.Test;

public class ServiceWebTest {
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
}
