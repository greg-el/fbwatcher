import org.openqa.selenium.WebDriver;

public class Login {
    private WebDriver driver;
    private Boolean loginSuccessful;

    public Login (WebDriver driver, Boolean loginSuccessful) {
        this.driver = driver;
        this.loginSuccessful = loginSuccessful;
    }

    public Login() {
        this.driver = null;
        this.loginSuccessful = null;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setLoginSuccessful(Boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }

    public Boolean getLoginSuccessful() {
        return loginSuccessful;
    }
}
