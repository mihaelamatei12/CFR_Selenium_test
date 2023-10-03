package bugs_found;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class HourBug {

    private WebDriver driver;

    private String parentWindow;

    private String initialHour = "00";

    private List<String> hoursList = new ArrayList<>();

    @BeforeTest
    private void initializeWebDriver() {
        System.setProperty("webdriver.chrome.driver","/home/mihaela/Selenium/chromedriver-linux64/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.cfrcalatori.ro/");
        parentWindow = driver.getWindowHandle();
        hoursList.add("00");
        hoursList.add("01");
        hoursList.add("02");
        hoursList.add("03");
        hoursList.add("04");
        hoursList.add("05");
        hoursList.add("06");
        hoursList.add("07");
        hoursList.add("08");
        hoursList.add("09");
        hoursList.add("10");
        hoursList.add("11");
        hoursList.add("12");
        hoursList.add("13");
        hoursList.add("14");
        hoursList.add("15");
        hoursList.add("16");
        hoursList.add("17");
        hoursList.add("18");
        hoursList.add("19");
        hoursList.add("20");
        hoursList.add("21");
        hoursList.add("22");
        hoursList.add("23");
    }
    @Test
    private void searchForTicket() throws InterruptedException {
        WebElement internationalButton = driver.findElement(By.cssSelector("#menu-item-14317 > a"));
        internationalButton.click();
        Set<String> allWindows = driver.getWindowHandles();
        /**
         * Switch to the new opened tab
         */
        for (String childWindow : allWindows) {
            if (!parentWindow.equalsIgnoreCase(childWindow)) {
                driver.switchTo().window(childWindow);
                Thread.sleep(2_000);
                WebElement onewayTicketButton = driver.findElement(By.cssSelector("body > rtt-app > rtt-app > ng-component > rtt-booking-search > div.container > form > div.row.trip-type > div > div > div:nth-child(1) > button"));
                onewayTicketButton.click();
                WebElement selectHour = driver.findElement(By.cssSelector("#outward-reference-time > fieldset > div > div.ngb-tp-hour > input"));
                selectHour.clear();
                selectHour.sendKeys(initialHour);
                Thread.sleep(500);

                /**
                 * Click right arrow. Created List
                 */
                WebElement rightArrow = driver.findElement(By.cssSelector("#outward-reference-time > fieldset > div > div.ngb-tp-hour > button:nth-child(1) > span.chevron"));
                List<String> countNumberList = new ArrayList<>();
                /**
                 *  Count ascending. Add appeared hours to List.
                 */
                for(int i = 0; i < 24; i++) {
                    countNumberList.add(selectHour.getAttribute("value"));
                    rightArrow.click();
                }
                /**
                 * See if countNumberList contains each element of hourList
                 */
                for(String hour : hoursList){
                    if(countNumberList.contains(hour)){
                        System.out.println("It contains " + hour);
                    } else {
                        System.out.println("It doesn't contains " + hour);
                    }
                }
                System.out.println("Count: " + countNumberList);
                Thread.sleep(1_000);
                /**
                 * Countdown. Add appeared hours to List.
                 */
                WebElement leftArrow = driver.findElement(By.cssSelector("#outward-reference-time > fieldset > div > div.ngb-tp-hour > button:nth-child(3) > span.chevron.bottom"));
                List<String> countdownNumberList = new ArrayList<>();
                for(int i = 0; i < 24; i++){
                    countdownNumberList.add(selectHour.getAttribute("value"));
                    leftArrow.click();
                }
                /**
                 * See if countdownNumberList contains each element of hourList
                 */
                for(String hour: hoursList){
                    if(countdownNumberList.contains(hour)){
                        System.out.println("It contains " + hour);
                    } else {
                        System.out.println("It doesn't contains " + hour);
                    }
                }
                System.out.println("Countdown: " + countdownNumberList);
            }
        }
        /**
         * We can see in the Count list that the number 23 doesn't appear, from 21 it jumps directly to 00
         * In the Countdown list shown that after 23 it's 00
         */
    }
    @AfterTest
    private void quitWebDriver() {
        driver.quit();
    }
}
