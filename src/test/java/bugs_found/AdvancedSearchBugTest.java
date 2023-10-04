package bugs_found;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Set;

public class AdvancedSearchBugTest {
    private WebDriver driver;

    private String parentWindow;

    @BeforeTest
    private void initializeWebDriver() {
        System.setProperty("webdriver.chrome.driver","/home/mihaela/Selenium/chromedriver-linux64/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.cfrcalatori.ro/");
        parentWindow = driver.getWindowHandle();
    }

    @Test
    private void verifyIfTheSecondPageRemainsInRomanian() throws InterruptedException{
        /**
         * First page is in romanian automaticaly
         */
        WebElement flag = driver.findElement(By.cssSelector("#wp-megamenu-item-wpml-ls-2-en > a > img"));
        Assert.assertNotNull(flag);
        Assert.assertEquals(flag.getAttribute("alt"),"en","Page in Romanian");
        /**
         * Click on calendar
         */
        WebElement calendar = driver.findElement(By.cssSelector("#plecare > div:nth-child(2) > div.inf-data"));
        Assert.assertNotNull(calendar);
        calendar.click();
        /**
         * Click on the up and down arrow symbol
         */
        WebElement arrowsSymbol = driver.findElement(By.cssSelector("#swap > i"));
        Assert.assertNotNull(arrowsSymbol);
        arrowsSymbol.click();
        /**
         * Click on "advanced search"
         */
        WebElement advancedSearchButton = driver.findElement(By.cssSelector("#linkCautareAvansata"));
        Assert.assertNotNull(advancedSearchButton);
        advancedSearchButton.click();
        /**
         * Switch to new page
         */
        Set<String> allWindows = driver.getWindowHandles();
        Assert.assertNotEquals(true,allWindows.isEmpty());
        for(String childWindow : allWindows){
            if(!parentWindow.equalsIgnoreCase(childWindow)){
                driver.switchTo().window(childWindow);
                Thread.sleep(1_000);
                /**
                 * Verify that the second page opened is in romanian
                 */
                WebElement romanianButton = driver.findElement(By.id("button-culture-ro-RO"));
                Assert.assertNotNull(romanianButton);
                Assert.assertEquals(romanianButton.getAttribute("class"),"active");
            }
        }
        /**
         * Normaly if in the first page language is selected to be romanian,
         * the second page should be the same language, but after this set of moves,
         * the second page it's shown in English.
         */
    }
    @AfterTest
    private void quitWebDriver() {
        driver.quit();
    }
}
