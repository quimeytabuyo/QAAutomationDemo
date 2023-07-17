package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.Reporter;


public class BaseTest {
    public WebDriver driver = new ChromeDriver();
    public Properties config;
	public XWPFDocument docx = new XWPFDocument();

	public BaseTest(){
		try {
			try {
				config = loadConfig();
			} catch (Exception e) {
				System.out.println("Error al leer archivo de configuraciones");
			}
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			this.driver.get(config.getProperty("baseUrl"));
			this.driver.manage().window().maximize();
		} catch (Exception e) {
			System.out.println("Error al inicializar chromedriver");
		}
	}
    
    public void auditBegin(ITestResult result) {
		Map<String, String> parameters = result.getMethod().findMethodParameters(result.getMethod().getXmlTest());
		this.audit("====================================================");
		this.audit("TEST METHOD: " + result.getMethod().getMethodName());
		this.audit("Parameters:");
		
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
		    this.audit(entry.getKey().toString() + " : " + entry.getValue().toString());
		}
		this.audit("====================================================");
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
    public void audit(String text){
		XWPFParagraph paragraph = this.docx.createParagraph();
		XWPFRun run = paragraph.createRun();
		
		run.setText(text);
		this.log(text);
		Reporter.log(text);
	}
    public void auditScreen(WebDriver driver) {
		TakesScreenshot scrShot = ((TakesScreenshot) driver);

		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

		XWPFParagraph paragraph = this.docx.createParagraph();
		Dimension dim;
		try {
			BufferedImage img = ImageIO.read(SrcFile);
			
			dim = new Dimension(img.getWidth(), img.getHeight());
			double width = dim.getWidth();
			double height = dim.getHeight();
			double scaling = 1.0;
			if (width > 72 * 6)
				scaling = (72 * 6) / width;
			InputStream in = new FileInputStream(SrcFile);
			paragraph.createRun().addPicture(in, org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_PNG, "", Units.toEMU(width * scaling), Units.toEMU(height * scaling));
			in.close();
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e) {
			this.audit("Ha fallado la captura de la screenshot");
		}
	}
    
    public void auditClose(ITestResult result, WebDriver driver){
		this.audit("---------------");
		this.audit("Finaliza " + result.getMethod().getMethodName());
		this.auditScreen(driver);
		
		
		String status = "";
		switch (result.getStatus()) {
			case (ITestResult.SUCCESS):
				status = "SUCCESS";
				break;
			default:
				status = "FAIL";
				break;
		}
		
		if(status == "FAIL") {
			this.audit("\n\nResult: " + status);
		}
		this.audit("---------------");
		
		
		FileOutputStream out;
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
		
		
		String[] getMethodParts = result.getMethod().getMethodName().split("_");
		
		String fileName = "";
		fileName += getMethodParts[getMethodParts.length - 2] + "_" + getMethodParts[getMethodParts.length-1];
		
		fileName += "_" + status
				+ "_" + simpleDate.format(new Date());
		
		String path = this.config.getProperty("evidencia.path").toString();
		BaseTest.createDirectory(path);
		String pathDoc = getEvidenceFilePath(fileName +".docx");
		System.out.println(pathDoc);
		
		try {
			out = new FileOutputStream(pathDoc);
				this.docx.write(out);
				
				out.flush();
				out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
    public static void createDirectory(String path) {
		Path pathFile = Paths.get(path);
		if (!Files.exists(pathFile)) {
			try {
				Files.createDirectories(pathFile);
				System.out.println("Se crea directorio: " + path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Directorio ya existe existente: " + path);
		}
	}
    public void log(String message) {
    	System.out.println(message);
    }
    
    public String getEvidenceFilePath(String fileName) {
        String evidenceFolderPath = "test-output/evidence/";
        Path evidencePath = Paths.get(evidenceFolderPath, fileName);
        return evidencePath.toString();
    }
}
