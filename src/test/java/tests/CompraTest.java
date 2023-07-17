package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.CompraPage;
import pages.LoginPage;
import utils.BaseTest;


public class CompraTest extends BaseTest{

	@Test
	@Parameters({"username" , "password" , "cantidadProductosAAgregar", "cantidadEsperada"})
	public void agregarACarrito_03(String username, String password, int cantidadProductosAAgregar, int cantidadEsperada) {
		LoginPage loginPage = new LoginPage(this.driver);
		
    	this.audit("Ingresar usuario: " + username);
		loginPage.ingresarUser(username);
		
    	this.audit("Ingresar password");
		loginPage.ingresarPassword(password);
		loginPage.clickLoginButton();
		
		CompraPage compraPage = new CompraPage(this.driver);
		this.audit("Agregar " + cantidadProductosAAgregar + " a carrito");
		compraPage.agregarProductosACarrito(cantidadProductosAAgregar);

		int cantidadAgregada = compraPage.obtenerCantidadAgregada();
		
		Assert.assertEquals(cantidadAgregada, cantidadEsperada, "La cantidad de productos agregados al carrito es la esperada");
	}
	
	@Test
	@Parameters({"username" , "password" , "cantidadProductos"})
	public void sumarTotalDeCompra_04(String username, String password, int cantidadProductos) {
		LoginPage loginPage = new LoginPage(this.driver);
    	
		this.audit("Ingresar usuario: " + username);
		loginPage.ingresarUser(username);
    	
		this.audit("Ingresar password");
		loginPage.ingresarPassword(password);
		loginPage.clickLoginButton();
		
		CompraPage compraPage = new CompraPage(this.driver);
		float precioInicial = compraPage.obtenerPrecioInicial();
		float precioTotal = compraPage.sumarYAgregarProductosYObtenerValorFinal(cantidadProductos);
		
		this.audit("Comparando valor inicial con el valor final");

		Assert.assertTrue(precioInicial < precioTotal, "No se sumo el resto de productos");
		}
	
	
	@AfterClass
	public void tearDown () {
		this.driver.quit();
	}
}
