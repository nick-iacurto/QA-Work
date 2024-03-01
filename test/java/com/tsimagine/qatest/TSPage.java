package com.tsimagine.qatest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TSPage extends PageObject {

   @FindBy( css = ".equation-label" )
   private WebElement sumString;

   @FindBy( css = ".btn" )
   private WebElement verify_button;

   @FindBy( id = "result")
   private WebElement result;

   @FindBy( css = ".equation-label" )
   private WebElement ResultStatus;

   public TSPage(WebDriver driver) {
      super(driver);
   }

   public String getSumString() {return this.sumString.getText();}
   public void inputSum(String value) {this.result.sendKeys(value);}
   public void pressVerifyButton() {this.verify_button.click();}
   public String getResultStatus(String exp, boolean val) {

      String valString = new String();

      if (val == true)
         valString = "= CORRECT";
      else
         valString = "= WRONG";

      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
      // wait for the original element to go stale... change
      wait.until(ExpectedConditions.textToBePresentInElement(this.sumString, exp + valString));
      return this.sumString.getText();
   }
}
