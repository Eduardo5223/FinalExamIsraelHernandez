package com.mayab.quality.functional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.webdriver.WebDriverBrowser;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.time.Duration;

public class SeleniumTest {
	
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  JavascriptExecutor js;

  @Before
  public void setUp() throws Exception {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void testUntitledTestCase() throws Exception {
    driver.get("https://www.google.com/search?gs_ssp=eJzj4tTP1TcwMU02T1JgNGB0YPBiS8_PT89JBQBASQXT&q=google&rlz=1C1CHZN_enMX1079MX1079&oq=go&gs_lcrp=EgZjaHJvbWUqEwgBEC4YgwEYxwEYsQMY0QMYgAQyBggAEEUYOTITCAEQLhiDARjHARixAxjRAxiABDINCAIQABiDARixAxiABDINCAMQABiDARixAxiABDIKCAQQABixAxiABDIGCAUQRRg8MgYIBhBFGDwyBggHEEUYPNIBCDE1OTdqMGo3qAIAsAIA&sourceid=chrome&ie=UTF-8");
    driver.findElement(By.xpath("//div[@id='rso']/div/div/div/div/div/div/div/span/a/h3")).click();
    driver.get("https://www.google.com.mx/?hl=es-419");
    driver.get("https://www.google.com.mx/search?q=facebook&sca_esv=ac1a0fd35070d5f1&hl=es-419&source=hp&ei=Lu0zZ-udGM_LkPIPvsaN4Q8&iflsig=AL9hbdgAAAAAZzP7Puoh_fQEzlfo80qqPY5wPuHpT4Kh&gs_ssp=eJzj4tLP1TfIyK1MKy5TYDRgdGDw4khLTE5Nys_PBgBmYAfL&oq=face&gs_lp=Egdnd3Mtd2l6IgRmYWNlKgIIADIREC4YgAQYsQMY0QMYgwEYxwEyCxAAGIAEGLEDGIMBMgsQABiABBixAxiDATILEAAYgAQYsQMYgwEyCxAAGIAEGLEDGIMBMggQABiABBixAzILEAAYgAQYsQMYgwEyCxAAGIAEGLEDGIMBMggQABiABBixAzIIEAAYgAQYsQNIrgpQAFjDA3AAeACQAQGYAYoCoAHpBKoBBTEuMi4xuAEDyAEA-AEBmAIDoAL7AsICDhAuGIAEGLEDGNEDGMcBwgIIEC4YgAQYsQPCAgUQABiABJgDAJIHAzAuM6AH_yo&sclient=gws-wiz");
    driver.findElement(By.id("APjFqb")).clear();
    driver.findElement(By.id("APjFqb")).sendKeys("facebook");
    driver.findElement(By.xpath("//div[@id='rso']/div/div/div/div/div/div/div/div/div/div/span/a/h3")).click();
    driver.get("https://www.facebook.com/?locale=es_LA");
    driver.get("https://www.w3schools.com/python/python_lambda.asp#:~:text=A%20lambda%20function%20is%20a,can%20only%20have%20one%20expression.");
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      //fail(verificationErrorString);
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