package bugs_found;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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
    private void findBug() throws InterruptedException{
        /**
         * Primary page is in romanian automaticaly
         */
        WebElement flag = driver.findElement(By.cssSelector("#wp-megamenu-item-wpml-ls-2-en > a > img"));
        if(flag.getAttribute("alt").equals("en")){
            System.out.println( "\"" + driver.getTitle() + "\"" +" - The page is in Romanian");
        } else if(flag.getAttribute("alt").equals("ro")){
            System.out.println("\"" + driver.getTitle() + "\"" + " - The page is in english");
        }
        /**
         * Click on calendar
         */
        WebElement calendarButton = driver.findElement(By.cssSelector("#plecare > div:nth-child(2) > div.inf-data"));
        calendarButton.click();
        /**
         * Click on the up and down arrow symbol
         */
        WebElement arrowsSymbol = driver.findElement(By.cssSelector("#swap > i"));
        arrowsSymbol.click();
        /**
         * Click on "advanced search"
         */
        WebElement advancedSearchButton = driver.findElement(By.cssSelector("#linkCautareAvansata"));
        advancedSearchButton.click();
        Set<String> allWindows = driver.getWindowHandles();
        /**
         * Switch to new webpage
         */
        for(String childWindow : allWindows){
            if(!parentWindow.equalsIgnoreCase(childWindow)){
                driver.switchTo().window(childWindow);
                Thread.sleep(1_000);
                /**
                 * Verify that the second page opened is in english
                 */
                WebElement englishButton = driver.findElement(By.id("button-culture-en-GB"));
                if(englishButton.getAttribute("class").equals("active")){
                    System.out.println(driver.getTitle() + " - The Page is in English");
                } else {
                    System.out.println(driver.getTitle() + " - It isn't in English");
                }
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
