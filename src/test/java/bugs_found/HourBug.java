package bugs_found;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class HourBug {

    private WebDriver driver;

    private String parentWindow;

    private String initialHour = "00";

    private List<String> hoursList = new ArrayList<>();

    private static Logger LOGGER = LoggerFactory.getLogger(HourBug.class);

    @BeforeTest
    private void initializeWebDriver() throws IOException {
        String rootPath = HourBug.class.getClassLoader().getResource("").getPath();
        String driverConfigPath = rootPath + "driver_path.properties";
        Properties properties = new Properties();
        properties.load(new FileInputStream(driverConfigPath));
        String driverPath = properties.getProperty("path");
        System.setProperty("webdriver.chrome.driver", driverPath);
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
    private void verifyIfAllHoursAppearWhenClickingOnLeftAndRightButtons() throws InterruptedException {
        /**
         * Select
         */
        WebElement internationalButton = driver.findElement(By.cssSelector("#menu-item-14317 > a"));
        Assert.assertNotNull(internationalButton);
        internationalButton.click();
        /**
         * Switch to the new opened tab
         */
        Set<String> allWindows = driver.getWindowHandles();
        Assert.assertNotEquals(true,allWindows.isEmpty(),"The Set is empty");
        for (String childWindow : allWindows) {
            if (!parentWindow.equalsIgnoreCase(childWindow)) {
                driver.switchTo().window(childWindow);
                Thread.sleep(2_000);
                /**
                 * Select search for One Way Ticket
                 */
                WebElement onewayButton = driver.findElement(By.cssSelector("body > rtt-app > rtt-app > ng-component > rtt-booking-search > div.container > form > div.row.trip-type > div > div > div:nth-child(1) > button"));
                Assert.assertNotNull(onewayButton);
                onewayButton.click();
                /**
                 * Input Hour
                 */
                WebElement selectHour = driver.findElement(By.cssSelector("#outward-reference-time > fieldset > div > div.ngb-tp-hour > input"));
                Assert.assertNotNull(selectHour);
                selectHour.clear();
                selectHour.sendKeys(initialHour);
                Thread.sleep(500);

                /**
                 * Click right arrow. Created List
                 */
                WebElement rightArrow = driver.findElement(By.cssSelector("#outward-reference-time > fieldset > div > div.ngb-tp-hour > button:nth-child(1) > span.chevron"));
                Assert.assertNotNull(rightArrow);
                List<String> countNumberList = new ArrayList<>();
                /**
                 *  Count ascending. Add appeared hours to List.
                 */
                for(int i = 0; i < 24; i++) {
                    Assert.assertNotNull(selectHour.getAttribute("value"));
                    countNumberList.add(selectHour.getAttribute("value"));
                    rightArrow.click();
                }
                LOGGER.info(countNumberList.toString());
                /**
                 * See if countNumberList contains each element of hourList
                 */
                for(String hour : hoursList){
                    Assert.assertTrue(countNumberList.contains(hour), hour + " doesn't appear");
                    LOGGER.error(hour + " doesn't appear");
                }
                Thread.sleep(1_000);
                /**
                 * Countdown. Add appeared hours to List.
                 */
                WebElement leftArrow = driver.findElement(By.cssSelector("#outward-reference-time > fieldset > div > div.ngb-tp-hour > button:nth-child(3) > span.chevron.bottom"));
                Assert.assertNotNull(leftArrow);
                List<String> countdownNumberList = new ArrayList<>();
                for(int i = 0; i < 24; i++){
                    Assert.assertNotNull(selectHour.getAttribute("value"));
                    countdownNumberList.add(selectHour.getAttribute("value"));
                    leftArrow.click();
                }
                LOGGER.info("Countdown: " + countdownNumberList);
                /**
                 * See if countdownNumberList contains each element of hourList
                 */
                for(String hour: hoursList){
                    Assert.assertTrue(countdownNumberList.contains(hour), hour + " doesn't appear");
                    LOGGER.error(hour + " doesn't appear");
                }
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
