package pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {
	protected static WebDriver driver;
    protected Properties config;

    public BasePage(WebDriver driver) {
        BasePage.driver = driver;
        this.config = loadConfig();

    }
    public void click(By locator) {
        WebElement element = driver.findElement(locator);
        element.click();
    }
    public WebElement find(By locator) {
        return driver.findElement(locator);
    }
    public static WebDriver getDriver() {
    	return driver;
    }
    public Properties loadConfig() {
        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream("config.properties");
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
    public List<WebElement> findElements(By locator){
        return driver.findElements(locator);
    }
    public void log(String message) {
        System.out.println(message);
    }
    public void sleep() throws InterruptedException {
    	Thread.sleep(1000 * 1);
    }
}
