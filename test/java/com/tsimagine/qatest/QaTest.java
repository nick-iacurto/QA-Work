package com.tsimagine.qatest;

import net.objecthunter.exp4j.ExpressionBuilder;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;

class QaTest {
   private static final WebDriver driver = new ChromeDriver();
    @BeforeAll
    static void setupClass() {
       System.setProperty("webdriver.Chrome.driver", "chromedriver");
    }

   @AfterAll
   static void teardownClass() {
      driver.close();
   }

    @BeforeEach
    void setupTest() {
       driver.get("https://tsdev-infrastructure-001.ue.r.appspot.com/");
    }

    @AfterEach
    void teardown() {

    }

    @Test
    @DisplayName( "sum result check for correct result" )
    void testCorrect() {

       TSPage tspage = new TSPage(driver);
       String exp = tspage.getSumString();

       //remove the '=' from the end of the string,
       exp = exp.substring(0,exp.length()-1);

       //evaluate the sum of the expression and convert the double value to string
       double result = new ExpressionBuilder(exp)
          .build()
          .evaluate();
       String resultString = Double.toString(result);

       //input sum and click verify button
       tspage.inputSum(resultString);
       tspage.pressVerifyButton();

       //get the result string and ensure that "CORRECT" appears
       String status = tspage.getResultStatus(exp, true);
       assert(status.contains("CORRECT"));
    }

   @Test
   @DisplayName( "sum result check for wrong result" )
   void testWrong() {

      TSPage tspage = new TSPage(driver);
      String exp = tspage.getSumString();

      //remove the '=' from the end of the string,
      exp = exp.substring(0,exp.length()-1);

      //evaluate the sum of the expression, add 1 to ensure incorrect result, and convert the double value to string
      double result = new ExpressionBuilder(exp)
         .build()
         .evaluate();
      String resultString = Double.toString(result + 1);

      //input sum and click verify button
      tspage.inputSum(resultString);
      tspage.pressVerifyButton();

      //get the result string and ensure that "WRONG" appears
      String status = tspage.getResultStatus(exp, false);
      assert(status.contains("WRONG"));
   }
}
