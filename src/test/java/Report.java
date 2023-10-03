import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Set;

public class Report {

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
    private void findBug() throws InterruptedException{
        WebElement report = driver.findElement(By.cssSelector("#menu-item-2715 > a"));
        report.click();
        Thread.sleep(1_000);
        Set<String> allWindows = driver.getWindowHandles();
        for(String childWindow : allWindows){
            if(!parentWindow.equalsIgnoreCase(childWindow)){
                driver.findElement(By.cssSelector("#menu-item-36802 > a")).click();
                /**
                 * Insert mail
                 */
                Thread.sleep(1_000);
                WebElement mail = driver.findElement(By.cssSelector("#field_eykhw"));
                mail.click();
                mail.sendKeys("aaa");
                /**
                 * Check hover
                 */
//                Actions actions = new Actions(driver);
//                actions.moveToElement(mail);
            }
        }
    }
}
