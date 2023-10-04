package tests_done;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
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
    private void searchForInternationalOneWayTicket() throws InterruptedException {
        /**
         * Format local date
         */
        SimpleDateFormat formateDate = new SimpleDateFormat("dd.MM.yyyy");
        Date today = new Date();
        String currentDate = formateDate.format(today);
        /**
         * Select international ticket
         */
        WebElement internationalButton = driver.findElement(By.cssSelector("#menu-item-14317 > a"));
        Assert.assertNotNull(internationalButton);
        internationalButton.click();
        /**
         * Switch to the new opened tab
         */
        Set<String> allWindows = driver.getWindowHandles();
        Assert.assertNotEquals(true,allWindows.isEmpty(),"The Set is empty");
        for(String childWindow : allWindows) {
            if (!parentWindow.equalsIgnoreCase(childWindow)) {
                driver.switchTo().window(childWindow);
                Thread.sleep(2_000);
                /**
                 * Select one way search button
                 */
                WebElement onewayTicketButton = driver.findElement(By.cssSelector("body > rtt-app > rtt-app > ng-component > rtt-booking-search > div.container > form > div.row.trip-type > div > div > div:nth-child(1) > button"));
                Assert.assertNotNull(onewayTicketButton);
                onewayTicketButton.click();
                /**
                 * Input the departure city and select the city
                 */
                WebElement inputDepartureCity = driver.findElement(By.cssSelector("#departure-location-name"));
                Assert.assertNotNull(inputDepartureCity);
                inputDepartureCity.sendKeys("București Nord");
                Thread.sleep(2_000);
                WebElement selectDepartureCity = driver.findElement(By.cssSelector("#ngb-typeahead-0-0 > span"));
                Assert.assertNotNull(selectDepartureCity);
                selectDepartureCity.click();
                /**
                 * Input the destination city and select the city
                 */
                WebElement inputDestinationCity = driver.findElement(By.cssSelector("#arrival-location-name"));
                Assert.assertNotNull(inputDestinationCity);
                inputDestinationCity.sendKeys("Budapest");
                Thread.sleep(1_000);
                WebElement selectDestinationCity = driver.findElement(By.cssSelector("#ngb-typeahead-1-0 > span"));
                Assert.assertNotNull(selectDestinationCity);
                selectDestinationCity.click();
                /**
                 * Put current date in calendar
                 */
                WebElement calendar = driver.findElement(By.cssSelector("#outward-reference-date"));
                Assert.assertNotNull(calendar);
                calendar.clear();
                calendar.sendKeys(currentDate);
                /**
                 * Select time
                 */
                WebElement selectHour = driver.findElement(By.cssSelector("#outward-reference-time > fieldset > div > div.ngb-tp-hour > input"));
                Assert.assertNotNull(selectHour);
                selectHour.clear();
                selectHour.sendKeys(hour);
                WebElement selectMinutes = driver.findElement(By.cssSelector("#outward-reference-time > fieldset > div > div.ngb-tp-minute > input"));
                Assert.assertNotNull(selectMinutes);
                selectMinutes.clear();
                selectMinutes.sendKeys(minutes);
                Thread.sleep(1_000);
                /**
                 * Click search button
                 */
                WebElement searchButton = driver.findElement(By.cssSelector("#search"));
                Assert.assertNotNull(searchButton);
                searchButton.click();
                Thread.sleep(2_000);
                /**
                 * Select ticket
                 */
                List<WebElement> journeyBackButtonList = driver.findElements(By.cssSelector("button.return-routes"));
                Assert.assertNotNull(journeyBackButtonList);
                WebElement journeyBackButton = journeyBackButtonList.get(0);
                journeyBackButton.click();
                Thread.sleep(2_000);
                /**
                 * See offers
                 */
                WebElement seeOffers = driver.findElement(By.cssSelector("button.loadOffers"));
                Assert.assertNotNull(seeOffers);
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
