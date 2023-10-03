package tests_done;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class FAQTest {

    private WebDriver driver;

    @BeforeTest
    private void initializeWebDriver() {
        System.setProperty("webdriver.chrome.driver","/home/mihaela/Selenium/chromedriver-linux64/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.cfrcalatori.ro/");
    }

    @Test
    private void searchFAQ() throws InterruptedException{
        /**
         * Change language to English
         */
        WebElement changeLanguage = driver.findElement(By.cssSelector("#wp-megamenu-item-wpml-ls-2-en > a > img"));
        changeLanguage.click();
        /**
         * Click FAQ
         */
        WebElement faq = driver.findElement(By.cssSelector("#menu-informatii-utile-engleza > li.btn-big-call-to-action.faq.menu-item.menu-item-type-post_type.menu-item-object-page.menu-item-7073.nav-item > a"));
        faq.click();
        /**
         * Select one question
         */
        WebElement purcashedTicket = driver.findElement(By.cssSelector("#headingfour > h5 > a > i.fa.fa-plus"));
        purcashedTicket.click();
        /**
         * Verify if the answer appears
         */
        WebElement answer = driver.findElement(By.cssSelector("#collapsefour"));
        Thread.sleep(1_000);
        if(answer.getAttribute("class").equals("collapse show")){
            System.out.println("The answer appears");
        } else {
            System.out.println("The answer doesn't appear");
        }
        Thread.sleep(2_000);
    }
    @AfterTest
    private void quitWebDriver() {
        driver.quit();
    }

}
