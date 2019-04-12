import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;


public class test {
    public static void main(String[] args) {

        System.setProperty("webdriver.gecko.driver", "/home/greg/IdeaProjects/fbwatcher/geckodriver");

        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        WebDriver driver = new FirefoxDriver(options);


        driver.get("https://www.facebook.com/groups/guitarexchange/?epa=SEARCH_BOX");
        List<WebElement> listOfElements = driver.findElements(By.cssSelector("div[role='feed']"));

        System.out.println((listOfElements));

    }
}
