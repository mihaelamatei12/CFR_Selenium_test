package tests_done;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;
//import org.testng.log4testng.Logger;

public class BuyTicketsInternationalTest {
    private WebDriver driver;

    private String parentWindow;
    private static Logger LOGGER = LogManager.getLogger(BuyTicketsInternationalTest.class);

    @BeforeTest
    private void initializeWebDriver() throws IOException {
        String rootPath = BuyTicketsInternationalTest.class.getClassLoader().getResource("").getPath();
        String driverConfigPath = rootPath + "driver_path.properties";
        Properties properties = new Properties();
        properties.load(new FileInputStream(driverConfigPath));
        String driverPath = properties.getProperty("path");
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.cfrcalatori.ro/");
        parentWindow = driver.getWindowHandle();
    }

    @Test
    private void searchForInternationalTickets() throws InterruptedException {
        WebElement internationalButton = driver.findElement(By.cssSelector("#menu-item-14317 > a"));
        Assert.assertNotNull(internationalButton);
        internationalButton.click();
        Set<String> allWindows = driver.getWindowHandles();
        Assert.assertNotEquals(true,allWindows.isEmpty(),"The Set is empty");
        /**
         * Switch to the new opened tab
         */
        for(String childWindow : allWindows){
            if(!parentWindow.equalsIgnoreCase(childWindow)){
                driver.switchTo().window(childWindow);
                /**
                 * Input the city for departure, and click the city
                 */
                WebElement inputDepartureCity = driver.findElement(By.cssSelector("#departure-location-name"));
                Assert.assertNotNull(inputDepartureCity);
                inputDepartureCity.sendKeys("București Nord");
                Thread.sleep(2000);
                WebElement selectDepartureCity = driver.findElement(By.cssSelector("#ngb-typeahead-0-0 > span"));
                Assert.assertNotNull(selectDepartureCity);
                selectDepartureCity.click();
                /**
                 * Input the destination city and click the city
                 */
                WebElement inputDestinationCity = driver.findElement(By.cssSelector("#arrival-location-name"));
                Assert.assertNotNull(inputDestinationCity);
                inputDestinationCity.sendKeys("Budapest");
                Thread.sleep(2000);
                WebElement selectDestinationCity = driver.findElement(By.cssSelector("#ngb-typeahead-1-0 > span"));
                Assert.assertNotNull(selectDestinationCity);
                selectDestinationCity.click();
                /**
                 * Search for tickets
                 */
                Thread.sleep(1000);
                WebElement searchButton = driver.findElement(By.cssSelector("#search"));
                Assert.assertNotNull(searchButton);
                searchButton.click();
                Thread.sleep(5000);
                /**
                 * Select journey back button (First ticket)
                 */
                List<WebElement> journeyBackButtonList = driver.findElements(By.cssSelector("button.return-routes"));
                Assert.assertNotEquals(true,journeyBackButtonList.isEmpty(),"List is empty");
                WebElement journeyBackButton = journeyBackButtonList.get(0);
                journeyBackButton.click();
                Thread.sleep(5000);
                /**
                 * Select for route back (Second ticket)
                 */
                List <WebElement> seeOffersButtonList = driver.findElements(By.cssSelector("button.return-routes.charge-button"));
                Assert.assertNotEquals(true,seeOffersButtonList.isEmpty());
                WebElement viewOfferButton = seeOffersButtonList.get(0);
                Assert.assertNotNull(viewOfferButton);
                String returnUrl = "https://bileteinternationale.cfrcalatori.ro/ro/booking/select/return";
                while (driver.getCurrentUrl().equals(returnUrl)) {
                    Thread.sleep(5000);
                    LOGGER.info("Check for ticket");
                    try {
                        viewOfferButton.click();
                    } catch (Exception e) {
                        Assert.assertNotNull(viewOfferButton);
                        LOGGER.error("Ticket not found");
                    }
                }
                /**
                 * Select 2 adults
                 */
                WebElement numberOfAdults = driver.findElement(By.cssSelector("#Adults"));
                Assert.assertNotNull(numberOfAdults);
                numberOfAdults.click();
                WebElement selectTwoAdults = driver.findElement(By.cssSelector("body > rtt-app > rtt-app > ng-component > ng-component > ng-component > rtt-offer-selection > div.container.passangersTypes.passangersSelect.card.break.b-bottom.headTitle > div > div:nth-child(1) > div > button:nth-child(3)"));
                Assert.assertNotNull(selectTwoAdults);
                selectTwoAdults.click();
                /**
                 * "Vezi oferte" - See offers
                 */
                Thread.sleep(2000);
                WebElement seeOffers = driver.findElement(By.cssSelector("body > rtt-app > rtt-app > ng-component > ng-component > ng-component > rtt-offer-selection > button"));
                Assert.assertNotNull(seeOffers);
                seeOffers.click();
                Thread.sleep(10000);
            }
        }
    }
    @AfterTest
    private void quitWebDriver() {
        driver.quit();
    }
}
