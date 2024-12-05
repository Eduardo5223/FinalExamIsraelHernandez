package com.mayab.quality.functional;

import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.junit.jupiter.api.BeforeEach;
import org.apache.commons.io.FileUtils;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterEach;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CRUDSeleniumTest {

	private static WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	JavascriptExecutor js;
	  
	@BeforeEach
	  public void setUp() throws Exception {
	    
		WebDriverManager.chromedriver().setup();
	    driver = new ChromeDriver();
	    baseUrl = "https://www.google.com/";
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
	    js = (JavascriptExecutor) driver;
	  }

	  @Test
	  public void test1_mernCreate() throws Exception {
	    driver.get(baseUrl + "chrome://newtab/");
	    driver.get("https://mern-crud-mpfr.onrender.com/");
	    driver.findElement(By.cssSelector(".ui.green.button")).click();
	    driver.findElement(By.name("name")).clear();
	    driver.findElement(By.name("name")).sendKeys("eduardo");
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("edu@email.com");
	    driver.findElement(By.name("age")).clear();
	    driver.findElement(By.name("age")).sendKeys("22");
	    driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/div[3]/div[2]/div")).click();
	    driver.findElement(By.xpath("//div[@role='listbox']//span[text()='Male']")).click();
	    driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/button")).click();
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("/html/body/div[3]/div/div[2]/form/div[4]/div/p")));
	    String result = messageElement.getText();
	    assertThat(result, is("Successfully added!"));
	    driver.findElement(By.xpath("/html/body/div[3]/div/i")).click();
	    takeScreenshot("create");
	  }
	  
	  @Test
	  public void test2_mernexistingEmail() throws Exception {
	    driver.get(baseUrl + "chrome://newtab/");
	    driver.get("https://mern-crud-mpfr.onrender.com/");
	    driver.findElement(By.cssSelector(".ui.green.button")).click();
	    driver.findElement(By.name("name")).clear();
	    driver.findElement(By.name("name")).sendKeys("edu");
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("edu@email.com");
	    driver.findElement(By.name("age")).clear();
	    driver.findElement(By.name("age")).sendKeys("22");
	    driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/div[3]/div[2]/div")).click();
	    driver.findElement(By.xpath("//div[@role='listbox']//span[text()='Male']")).click();
	    driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/button")).click();
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("//p[text()='That email is already taken.']")));
	    String result = messageElement.getText();
	    assertThat(result, is("That email is already taken."));
	    takeScreenshot("existingemail");
	  }
	  
	  @Test
	  public void test3_mernmodifyAge() throws Exception {
	    driver.get(baseUrl + "chrome://newtab/");
	    driver.get("https://mern-crud-mpfr.onrender.com/");
	    driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/table/tbody/tr[1]/td[5]/button[1]")).click();
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    Thread.sleep(2000); // 2 segundos de pausa 
	    driver.findElement(By.name("age")).clear();
	    driver.findElement(By.name("age")).sendKeys("19");
	    driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/button")).click();
	    WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
		        By.xpath("//p[text()='Successfully updated!']")));
		String result = messageElement.getText();
		assertThat(result, is("Successfully updated!"));
		takeScreenshot("updateage");
	  }
	  
	  @Test
	  public void test4_mernsearchbyName() throws Exception {
	      driver.get(baseUrl + "chrome://newtab/");
	      driver.get("https://mern-crud-mpfr.onrender.com/");
	      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	      WebElement nameCell = wait.until(ExpectedConditions.visibilityOfElementLocated(
	          By.xpath("//td[text()='Eduardo']"))); 
	      assertThat(nameCell.getText(), is("Eduardo"));
	      System.out.println("El nombre " + nameCell.getText() + " fue encontrado");
	      takeScreenshot("searchname");
	  }
	  
	  @Test
	  public void test5_mernErase() throws Exception {
	    driver.get(baseUrl + "chrome://newtab/");
	    driver.get("https://mern-crud-mpfr.onrender.com/");
	    driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/table/tbody/tr[1]/td[5]/button[2]")).click();
	    driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/button[1]")).click();
	    takeScreenshot("erase");
	  }
	  
	@Test
	public void test6_mernfindAll() throws Exception {
	    driver.get(baseUrl + "chrome://newtab/");
	    driver.get("https://mern-crud-mpfr.onrender.com/");
	    
	    List<String> expectedUsers = Arrays.asList("Eduardo", "Juan", "Anibal");
	   
	    // Agregar usuarios
	    driver.findElement(By.cssSelector(".ui.green.button")).click();
	    driver.findElement(By.name("name")).clear();
	    driver.findElement(By.name("name")).sendKeys("eduardo");
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("edu@email.com");
	    driver.findElement(By.name("age")).clear();
	    driver.findElement(By.name("age")).sendKeys("22");
	    driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/div[3]/div[2]/div")).click();
	    driver.findElement(By.xpath("//div[@role='listbox']//span[text()='Male']")).click();
	    driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/button")).click();
	    driver.findElement(By.xpath("/html/body/div[3]/div/i")).click();

	    driver.findElement(By.cssSelector(".ui.green.button")).click();
	    driver.findElement(By.name("name")).clear();
	    driver.findElement(By.name("name")).sendKeys("juan");
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("juan@email.com");
	    driver.findElement(By.name("age")).clear();
	    driver.findElement(By.name("age")).sendKeys("21");
	    driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/div[3]/div[2]/div")).click();
	    driver.findElement(By.xpath("//div[@role='listbox']//span[text()='Male']")).click();
	    driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/button")).click();
	    driver.findElement(By.xpath("/html/body/div[3]/div/i")).click();

	    
	    Thread.sleep(2000); // 2 segundos de pausa 

	    driver.findElement(By.cssSelector(".ui.green.button")).click();
	    driver.findElement(By.name("name")).clear();
	    driver.findElement(By.name("name")).sendKeys("anibal");
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("anibal@email.com");
	    driver.findElement(By.name("age")).clear();
	    driver.findElement(By.name("age")).sendKeys("20");
	    driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/div[3]/div[2]/div")).click();
	    driver.findElement(By.xpath("//div[@role='listbox']//span[text()='Male']")).click();
	    driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/button")).click();
	    driver.findElement(By.xpath("/html/body/div[3]/div/i")).click();
	    
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement tableBody = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//table/tbody")));
	    List<WebElement> rows = tableBody.findElements(By.tagName("tr"));

	    List<String> nombresEnTabla = new ArrayList<>();
	    for (WebElement row : rows) {
	        WebElement nameCell = row.findElement(By.xpath(".//td[1]")); 
	        nombresEnTabla.add(nameCell.getText().trim());
	    }

	    // Log de nombres encontrados
	    System.out.println("Nombres en tabla: " + nombresEnTabla);

	    // Validar si cada usuario esperado está en la tabla
	    for (String usuario : expectedUsers) {
	        assertThat("El usuario " + usuario + " no fue encontrado en la tabla.",
	                   nombresEnTabla.contains(usuario), is(true));
	        System.out.println("El usuario " + usuario + " se encuentra en la tabla.");
	    }
	    takeScreenshot("findall");
	}

	  public static void takeScreenshot(String fileName) throws IOException{
		  File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		  
		  FileUtils.copyFile(file, new File("src/screenshots/" + fileName + ".jpg"));
	  }


	  @AfterEach
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }

}
