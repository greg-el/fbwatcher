import org.openqa.selenium.WebDriver;

class ReturnObject {
    public final WebDriver driver;
    public final String pageSource;

    public ReturnObject(WebDriver driver, String pageSource) {
        this.driver = driver;
        this.pageSource = pageSource;
    }
}