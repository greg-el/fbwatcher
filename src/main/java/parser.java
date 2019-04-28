import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;


public class parser {
    public static void main (String[] args) {
        //chrome webdriver executable location
        System.setProperty("webdriver.chrome.driver", "/home/greg/IdeaProjects/orgfbtestwatcher/chromedriver");

        //creating methods instance
        final Methods methods = new Methods();

        //webdriver options
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--disable-notifications");
        WebDriver driver = new ChromeDriver(options);

        WebDriver loggedDriver = methods.login("gr3gl@hotmail.com", "Bobfriend1", driver);

        UI ui = new UI();
        ui.create(loggedDriver);

        loggedDriver.quit();
    }
}
