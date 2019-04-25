import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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


        List groupPost = methods.getGroupPosts(loggedDriver, "https://www.facebook.com/groups/depsuk/");

        for (Object e : groupPost) {
            System.out.println(e.toString());
        }

        loggedDriver.quit();
    }
}
