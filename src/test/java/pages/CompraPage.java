package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CompraPage extends BasePage {
    By productosAAñadirButton = By.xpath("//*[contains(text(), 'Add to cart')]");
    By productosEnCarrito = By.xpath("//span[@*= 'shopping_cart_badge']");
    By preciosProductos = By.xpath("//*[@*='inventory_item_description']");

    public CompraPage(WebDriver driver) {
        super(driver);
    }

    public void agregarProductosACarrito(int cantidad) {
    	List <WebElement> productos = this.findElements(productosAAñadirButton);
    	this.log("Añadir " + cantidad + " de productos a carrito");
    	for (int i = 0; i < cantidad; i ++) {
    		productos.get(i).click();
    	}
    }
    public int obtenerCantidadAgregada() {
    	this.log("Obteneniendo cantidad agregada");
    	 int cantidadAgregada = Integer.parseInt(this.find(productosEnCarrito).getText());
    	 return cantidadAgregada;
    }
    public float obtenerPrecioInicial() {
    	this.log("Obtener precio inicial");
    	List <WebElement> precios = this.findElements(preciosProductos);
    	this.agregarProductosACarrito(1);
    	float primerPrecioAComparar = Float.parseFloat(precios.get(0).getText().split("\\$")[1].split("\\n")[0]);
		return primerPrecioAComparar;
    }
    public float sumarYAgregarProductosYObtenerValorFinal(int cantidad) {
    	this.log("Obtener valor final");
    	List <WebElement> precios = this.findElements(preciosProductos);
    	for(int i = 0; i < cantidad; i ++) {
    		agregarProductosACarrito(i);
    	}
    	
     	float primerPrecio = Float.parseFloat(precios.get(1).getText().split("\\$")[1].split("\\n")[0]);
    	float segundoPrecio = Float.parseFloat(precios.get(1).getText().split("\\$")[1].split("\\n")[0]);
    	float tercerPrecio = Float.parseFloat(precios.get(2).getText().split("\\$")[1].split("\\n")[0]);
    	float total = segundoPrecio + tercerPrecio + primerPrecio;
    	
    	return total;
    }
}
