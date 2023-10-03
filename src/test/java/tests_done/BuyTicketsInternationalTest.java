package tests_done;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

public class BuyTicketsInternationalTest {
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
    private void searchForTickets() throws InterruptedException {
        WebElement internationalButton = driver.findElement(By.cssSelector("#menu-item-14317 > a"));
        internationalButton.click();
        Set<String> allWindows = driver.getWindowHandles();
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
                inputDepartureCity.sendKeys("București Nord");
                Thread.sleep(2000);
                WebElement selectDepartureCity = driver.findElement(By.cssSelector("#ngb-typeahead-0-0 > span"));
                selectDepartureCity.click();
                /**
                 * Input the destination city and click the city
                 */
                WebElement inputDestinationCity = driver.findElement(By.cssSelector("#arrival-location-name"));
                inputDestinationCity.sendKeys("Budapest");
                Thread.sleep(2000);
                WebElement selectDestinationCity = driver.findElement(By.cssSelector("#ngb-typeahead-1-0 > span"));
                selectDestinationCity.click();
                /**
                 * Search for tickets
                 */
                Thread.sleep(1000);
                WebElement searchButton = driver.findElement(By.cssSelector("#search"));
                searchButton.click();
                Thread.sleep(5000);
                /**
                 * Select journey back button
                 */
                List<WebElement> journeyBackButtonList = driver.findElements(By.cssSelector("button.return-routes"));
                WebElement journeyBackButton = journeyBackButtonList.get(0);
                journeyBackButton.click();
                Thread.sleep(5000);
                /**
                 * Search for route back
                 */
                String offerButtonIsLoadedClass = "body > rtt-app > rtt-app > ng-component > ng-component > ng-component > div:nth-child(1) > div > div > div.col-12.journey-list > rtt-journey-detail > div > div.journey-actions > button.return-routes.charge-button";
                List<WebElement> loadedOfferButton = driver.findElements(By.cssSelector(offerButtonIsLoadedClass));
                List <WebElement> seeOffersButtonList = driver.findElements(By.cssSelector("button.return-routes.charge-button"));
                String returnUrl = "https://bileteinternationale.cfrcalatori.ro/ro/booking/select/return";
                while (driver.getCurrentUrl().equals(returnUrl)) {
                    Thread.sleep(5000);
                    System.out.println("Check for ticket");
                    try {
                        WebElement viewOfferButton = seeOffersButtonList.get(0);
                        viewOfferButton.click();
                    } catch (Exception e) {
                        System.out.println("error");
                    }
                }
                /**
                 * Select 2 adults
                 */
                WebElement numberOfAdults = driver.findElement(By.cssSelector("#Adults"));
                numberOfAdults.click();
                WebElement selectTwoAdults = driver.findElement(By.cssSelector("body > rtt-app > rtt-app > ng-component > ng-component > ng-component > rtt-offer-selection > div.container.passangersTypes.passangersSelect.card.break.b-bottom.headTitle > div > div:nth-child(1) > div > button:nth-child(3)"));
                selectTwoAdults.click();
                /**
                 * "Vezi oferte" - See offers
                 */
                Thread.sleep(2000);
                WebElement seeOffers = driver.findElement(By.cssSelector("body > rtt-app > rtt-app > ng-component > ng-component > ng-component > rtt-offer-selection > button"));
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
