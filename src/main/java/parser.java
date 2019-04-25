import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Scanner;

public class parser {

    public static void main (String[] args) {
        //chrome webdriver executable location
        System.setProperty("webdriver.chrome.driver", "/home/greg/IdeaProjects/orgfbtestwatcher/chromedriver");

        //creating methods instance
        Methods methods = new Methods();

        //webdriver options
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--disable-notifications");
        WebDriver driver = new ChromeDriver(options);

        WebDriver loggedDriver = methods.login("gr3gl@hotmail.com", "Bobfriend1", driver);
        List listGroups = methods.getMemberGroups(loggedDriver);




        Scanner groups = new Scanner(System.in);
        System.out.println("What group do you want to scrape today: " + listGroups);
        String groupInput = groups.nextLine();
        methods.getGroupPosts(loggedDriver, groupInput);

        loggedDriver.quit();
    }
}
