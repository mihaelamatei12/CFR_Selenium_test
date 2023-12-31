package bugs_found;

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
import java.util.Objects;
import java.util.Properties;


public class DomesticTimetableBug {
    private WebDriver driver;

    @BeforeTest
    private void initializeWebDriver() throws IOException {
        String rootPath = DomesticTimetableBug.class.getClassLoader().getResource("").getPath();
        String driverConfigPath = rootPath + "driver_path.properties";
        Properties properties = new Properties();
        properties.load(new FileInputStream(driverConfigPath));
        String driverPath = properties.getProperty("path");
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.cfrcalatori.ro/");
        /**
         * Change language to English
         */
        WebElement flag = driver.findElement(By.cssSelector("#wp-megamenu-item-wpml-ls-2-en > a > img"));
        Assert.assertNotNull(flag);
        flag.click();
    }
    @Test
    private void verifyIfTheWarningIsInEnglish() throws InterruptedException {
        /**
         * Input incomplete names
         */
        WebElement inputDeparture = driver.findElement(By.id("input-station-departure-name"));
        Assert.assertNotNull(inputDeparture);
        inputDeparture.sendKeys("Buc");
        WebElement inputArrival = driver.findElement(By.id("input-station-arrival-name"));
        Assert.assertNotNull(inputArrival);
        inputArrival.sendKeys("Si");
        Thread.sleep(1_000);
        WebElement warningDeparture = driver.findElement(By.cssSelector("#span-validation-input-station-departure-name"));
        Assert.assertNotNull(warningDeparture);
        /**
         * Verify if the warning appears
         */
        Assert.assertTrue(warningDeparture.isDisplayed());
        /**
         * The warnings are shown in Romanian and the page it's in english
         */
        Assert.assertEquals(warningDeparture.getText(),"No station with this name was found!");
        Thread.sleep(1_000);
    }
    @AfterTest
    private void quitWebDriver() {
        driver.quit();
    }
}
