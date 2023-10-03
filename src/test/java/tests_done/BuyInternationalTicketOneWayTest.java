package tests_done;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class BuyInternationalTicketOneWayTest {
    private WebDriver driver;

    private String parentWindow;

    private String hour = "23";
    private String minutes = "30";

    @BeforeTest
    private void initializeWebDriver() {
        System.setProperty("webdriver.chrome.driver","/home/mihaela/Selenium/chromedriver-linux64/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.cfrcalatori.ro/");
        parentWindow = driver.getWindowHandle();
    }
    @Test
    private void searchForTicket() throws InterruptedException, AWTException {
        WebElement internationalButton = driver.findElement(By.cssSelector("#menu-item-14317 > a"));
        internationalButton.click();
        Set<String> allWindows = driver.getWindowHandles();
        /**
         * Format local date
         */
        SimpleDateFormat formateDate = new SimpleDateFormat("dd.MM.yyyy");
        Date today = new Date();
        System.out.println(formateDate.format(today));
        String currentDate = formateDate.format(today);
        /**
         * Switch to the new opened tab
         */
        for(String childWindow : allWindows) {
            if (!parentWindow.equalsIgnoreCase(childWindow)) {
                driver.switchTo().window(childWindow);
                Thread.sleep(2_000);
                /**
                 * Select one way ticket
                 */
                WebElement onewayTicketButton = driver.findElement(By.cssSelector("body > rtt-app > rtt-app > ng-component > rtt-booking-search > div.container > form > div.row.trip-type > div > div > div:nth-child(1) > button"));
                onewayTicketButton.click();
                /**
                 * Input the departure city and click the city
                 */
                WebElement inputDepartureCity = driver.findElement(By.cssSelector("#departure-location-name"));
                inputDepartureCity.sendKeys("București Nord");
                Thread.sleep(2_000);
                WebElement selectDepartureCity = driver.findElement(By.cssSelector("#ngb-typeahead-0-0 > span"));
                selectDepartureCity.click();
                /**
                 * Input the destination city and click the city
                 */
                WebElement inputDestinationCity = driver.findElement(By.cssSelector("#arrival-location-name"));
                inputDestinationCity.sendKeys("Budapest");
                Thread.sleep(1_000);
                WebElement selectDestinationCity = driver.findElement(By.cssSelector("#ngb-typeahead-1-0 > span"));
                selectDestinationCity.click();
                /**
                 * Put current date in calendar
                 */
                WebElement calendar = driver.findElement(By.cssSelector("#outward-reference-date"));
                calendar.clear();
                calendar.sendKeys(currentDate);
                /**
                 * Select time
                 */
                WebElement selectHour = driver.findElement(By.cssSelector("#outward-reference-time > fieldset > div > div.ngb-tp-hour > input"));
                selectHour.clear();
                WebElement selectMinutes = driver.findElement(By.cssSelector("#outward-reference-time > fieldset > div > div.ngb-tp-minute > input"));
                selectMinutes.clear();
                selectHour.sendKeys(hour);
                selectMinutes.sendKeys(minutes);
                Thread.sleep(1_000);
                /**
                 * Click search button
                 */
                WebElement searchButton = driver.findElement(By.cssSelector("#search"));
                searchButton.click();
                Thread.sleep(2_000);
                /**
                 * Select ticket
                 */
                List<WebElement> journeyBackButtonList = driver.findElements(By.cssSelector("button.return-routes"));
                WebElement journeyBackButton = journeyBackButtonList.get(0);
                journeyBackButton.click();
                Thread.sleep(2_000);
                /**
                 * See offers
                 */
                WebElement seeOffers = driver.findElement(By.cssSelector("button.loadOffers"));
                seeOffers.click();
            }
        }
        Thread.sleep(3_000);
    }
    @AfterTest
    private void quitWebDriver() {
        driver.quit();
    }
}
