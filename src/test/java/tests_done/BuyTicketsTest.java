package tests_done;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.beans.Expression;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class BuyTicketsTest {
    private WebDriver driver;
    private String parentWindow;

    @BeforeTest
    private void initializeWebDriver() throws IOException {
        String rootPath = BuyTicketsTest.class.getClassLoader().getResource("").getPath();
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
    private void searchAndBuyTicket() throws InterruptedException {
        /**
         * Input departure city
         */
        WebElement inputDeparture = driver.findElement(By.id("input-station-departure-name"));
        Assert.assertNotNull(inputDeparture);
        inputDeparture.sendKeys("București Nord");
        /**
         * Input arrival city
         */
        WebElement inputArrival = driver.findElement(By.id("input-station-arrival-name"));
        Assert.assertNotNull(inputArrival);
        inputArrival.sendKeys("Sinaia");
        /**
         *
         * Click on search
         */
        WebElement searchButton = driver.findElement(By.cssSelector("#form-search > div.container > button"));
        Assert.assertNotNull(searchButton);
        searchButton.click();
        Thread.sleep(1_000);
        Set<String> allWindows = driver.getWindowHandles();
        Assert.assertNotEquals(true,allWindows.isEmpty());
        for(String childWindow : allWindows) {
            if(!parentWindow.equalsIgnoreCase(childWindow)) {
                driver.switchTo().window(childWindow);
                Thread.sleep(1000);
                /**
                 * Get the first ticket available
                 */
                List<WebElement> buttonList = driver.findElements(By.cssSelector("button.btn.mt-1.mt-xl-0.btn-outline-primary"));
                Assert.assertNotEquals(true,buttonList.isEmpty());
                for(WebElement buttons: buttonList){
                    if(buttons.isDisplayed()){
                        WebElement buyTicketButton = buttons;
                        buyTicketButton.click();
                        break;
                    }
                }
            }
        }
    }
    @AfterTest
    private void quitWebDriver() {
        driver.quit();
    }
}
