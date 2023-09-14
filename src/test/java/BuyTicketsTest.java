import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.util.Iterator;
import java.util.Set;

public class BuyTicketsTest {
    public static void main(String[] args) throws Throwable {
        System.setProperty("webdriver.chrome.driver","/home/mihaela/Selenium/chromedriver-linux64/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.cfrcalatori.ro/");
        String parentWindow = driver.getWindowHandle();

        /**
         *
         */

        driver.findElement(By.id("input-station-departure-name")).sendKeys("București Nord");
        driver.findElement(By.id("input-station-arrival-name")).sendKeys("Sinaia");
        Thread.sleep(500);

        driver.findElement(By.xpath("//*[@id=\"form-search\"]/div[3]/button")).click();
        Thread.sleep(1000);

        Set<String> allWindows = driver.getWindowHandles();
        Iterator<String> iterator = allWindows.iterator();

        while(iterator.hasNext()){
            String childWindow = iterator.next();
            if(!parentWindow.equalsIgnoreCase(childWindow)) {
                driver.switchTo().window(childWindow);
                WebElement buyTicketButton = driver.findElement(By.cssSelector("#button-itinerary-14-buy"));
                try{
                    buyTicketButton.click();
                } catch (ElementNotInteractableException e){
                    Assert.fail("Tickets not available");
                }
            }
        }



        // driver.findElement(By.cssSelector("#button-itinerary-14-buy")).click();
        /**After clicking "Cautare" it opens another window, that's why I can't find the next element
         * driver.findElement(By.cssSelector("#button-itinerary-14-buy")).click();
         */

    }
}
