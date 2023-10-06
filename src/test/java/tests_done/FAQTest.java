package tests_done;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class FAQTest {

    private WebDriver driver;

    private static Logger LOGGER = LogManager.getLogger(FAQTest.class);

    @BeforeTest
    private void initializeWebDriver() throws IOException {
        /**
         * Properties properties = new Properties();
         *         properties.load(TestFile.class.getClassLoader().getResourceAsStream("my_props.properties"));
         *         System.out.println(properties.getProperty("fortza"));
         */
        Properties properties = new Properties();
        properties.load(FAQTest.class.getClassLoader().getResourceAsStream("driver_path.properties"));
        String driverPath = properties.getProperty("path");
        System.setProperty("webdriver.chrome.driver", driverPath);
        LOGGER.info("Logger: {}", FAQTest.class.getClassLoader().getResource("driver_path.properties").getPath());
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.cfrcalatori.ro/");
    }

    @Test
    private void searchFAQAndCheckIfTheAnswerAppears() throws InterruptedException{
        /**
         * Change language to English
         */
        WebElement changeLanguage = driver.findElement(By.cssSelector("#wp-megamenu-item-wpml-ls-2-en > a > img"));
        Assert.assertNotNull(changeLanguage);
        changeLanguage.click();
        Thread.sleep(500);
        /**
         * Click FAQ
         */
        WebElement faq = driver.findElement(By.cssSelector("#menu-informatii-utile-engleza > li.btn-big-call-to-action.faq.menu-item.menu-item-type-post_type.menu-item-object-page.menu-item-7073.nav-item > a"));
        Assert.assertNotNull(faq);
        faq.click();
        /**
         * Select one question
         */
        WebElement purchasedTicket = driver.findElement(By.cssSelector("#headingfour > h5 > a > i.fa.fa-plus"));
        Assert.assertNotNull(purchasedTicket);
        purchasedTicket.click();
        /**
         * Verify if the answer appears
         */
        WebElement answer = driver.findElement(By.cssSelector("#collapsefour"));
        Assert.assertNotNull(answer);
        Thread.sleep(1_000);
        Assert.assertEquals(answer.getAttribute("class"),"collapse show");
        Thread.sleep(2_000);
    }
    @AfterTest
    private void quitWebDriver() {
        driver.quit();
    }

}
