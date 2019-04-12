import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public class test {
    public static void main(String[] args) {

    System.setProperty("webdriver.gecko.driver", "/home/greg/IdeaProjects/fbwatch/geckodriver");

    FirefoxOptions options = new FirefoxOptions();
    options.setHeadless(true);
    WebDriver driver = new FirefoxDriver(options);


    driver.get("https://www.facebook.com/");


    System.out.println(driver.getTitle());


    }
}



