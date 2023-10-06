package tests_done;

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
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class BuyTicketsNextDayTest {
    private WebDriver driver;
    private String parentWindow;

    @BeforeTest
    private void initializeWebDriver() throws IOException {
        String rootPath = BuyTicketsNextDayTest.class.getClassLoader().getResource("").getPath();
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
    private void searchAndBuyTicketsForNextDay() throws InterruptedException {
        WebElement inputDeparture = driver.findElement(By.id("input-station-departure-name"));
        Assert.assertNotNull(inputDeparture);
        inputDeparture.sendKeys("București Nord");
        WebElement inputArrival = driver.findElement(By.id("input-station-arrival-name"));
        Assert.assertNotNull(inputArrival);
        inputArrival.sendKeys("Sinaia");
        /**
         * Calendar/ Select the next day
         */
        WebElement calendar = driver.findElement(By.cssSelector("#plecare > div:nth-child(2) > div.inf-data"));
        Assert.assertNotNull(calendar);
        calendar.click();
        List<WebElement> calendarDays = driver.findElements(By.cssSelector("td.day"));
        Assert.assertNotEquals(true,calendarDays.isEmpty(),"The List is empty");
        WebElement currentDay = driver.findElement(By.cssSelector("td.day.active.today"));
        Assert.assertNotNull(currentDay);
        for(int i = 0; i < calendarDays.size(); i++){
            if(calendarDays.get(i).equals(currentDay)){
                WebElement nextDay = calendarDays.get(i + 1);
                nextDay.click();
            }
        }
        Thread.sleep(1_000);
        /**
         * Click search for ticket
         */
        WebElement searchButton = driver.findElement(By.cssSelector("#form-search > div.container > button"));
        Assert.assertNotNull(searchButton);
        searchButton.click();
        Thread.sleep(1_000);
        /**
         * Switch to new opened tab
         */
        Set<String> allWindows = driver.getWindowHandles();
        Assert.assertNotEquals(true,allWindows.isEmpty(),"The Set is empty");
        for(String childWindow : allWindows) {
            if(!parentWindow.equalsIgnoreCase(childWindow)) {
                driver.switchTo().window(childWindow);
                Thread.sleep(1000);
                /**
                 * Get the first ticket available
                 */
                List<WebElement> buttonList = driver.findElements(By.cssSelector("button.btn.mt-1.mt-xl-0.btn-outline-primary"));
                Assert.assertNotEquals(true,buttonList.isEmpty(),"The List of buttons is empty");
                WebElement buyTicketButton = buttonList.get(0);
                Assert.assertNotNull(buyTicketButton, "Tickets not found");
                buyTicketButton.click();
            }
        }
    }

    @AfterTest
    private void quitWebDriver() {
        driver.quit();
    }
}
