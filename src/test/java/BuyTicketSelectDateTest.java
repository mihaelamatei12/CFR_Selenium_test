import bugs_found.AdvancedSearchBugTest;
import bugs_found.HourBug;
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
import tests_done.BuyTicketsInternationalTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BuyTicketSelectDateTest {
    private WebDriver driver;
    private String parentWindow;

    private static Logger LOGGER = LoggerFactory.getLogger(BuyTicketSelectDateTest.class);

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
    private void searchTicketsForSelectedDay() throws InterruptedException {
        WebElement inputDeparture = driver.findElement(By.id("input-station-departure-name"));
        Assert.assertNotNull(inputDeparture);
        inputDeparture.sendKeys("București Nord");
        WebElement inputArrival = driver.findElement(By.id("input-station-arrival-name"));
        Assert.assertNotNull(inputArrival);
        inputArrival.sendKeys("Sinaia ");

        Thread.sleep(1_000);
        String day = "31";
        String month = "octombrie";
        String year = "2023";

        WebElement calendar = driver.findElement(By.cssSelector("#plecare > div:nth-child(2) > div.inf-data"));
        Assert.assertNotNull(calendar);
        calendar.click();
        /**
         * Select next month
         */
        WebElement nextMont = driver.findElement(By.cssSelector("#plecare > div.buttonInchidereCalendar.bootstrap-datetimepicker-widget.dropdown-menu.usetwentyfour.bottom > ul > li.show > div > div.datepicker-days > table > thead > tr:nth-child(1) > th.next"));
        Assert.assertNotNull(nextMont);
        nextMont.click();

        List<WebElement> calendarDays = driver.findElements(By.cssSelector("td.day"));
        List<WebElement> calendarMonths = driver.findElements(By.cssSelector("span.month"));
        List<WebElement> calendarYears = driver.findElements(By.cssSelector("span.year"));

        /**
         * Remove old days from List
         */
        calendarDays.remove(driver.findElement(By.cssSelector("td.day.old")));

        HashMap<String,WebElement> calendarDaysMap = new HashMap<>();
        List<String> calendarMonthsStringList = new ArrayList<>();
        List<String> calendarYearsStringList = new ArrayList<>();
        for(WebElement dayElement: calendarDays){
            calendarDaysMap.put(dayElement.getText(),dayElement);
        }
//        for(WebElement monthElement: calendarMonths){
//            calendarMonthsStringList.add(monthElement.getText());
//        }
//        for(WebElement yearElement: calendarYears){
//            calendarYearsStringList.add(yearElement.getText());
//        }

        LOGGER.info("Zilele: " + calendarDaysMap);
        LOGGER.info("Lunile: " + calendarMonths.get(0).getText());
        LOGGER.info("Anii: " + calendarYearsStringList);
        LOGGER.info("text: " + driver.findElement(By.cssSelector("#plecare > div.buttonInchidereCalendar.bootstrap-datetimepicker-widget.dropdown-menu.usetwentyfour.bottom > ul > li.show > div > div.datepicker-months > table > tbody > tr > td > span:nth-child(1)")).getText());
        calendarDaysMap.get(day).click();
        /**
         * Click on the search button
         */
        WebElement searchButton = driver.findElement(By.cssSelector("#form-search > div.container > button"));
        Assert.assertNotNull(searchButton);
        searchButton.click();
    }

    @AfterTest
    private void quitWebDriver() {
        driver.quit();
    }
}
