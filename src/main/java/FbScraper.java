import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FbScraper {
    WebDriver createDriver() {
        //chrome webdriver executable location
        System.setProperty("webdriver.chrome.driver", "/home/greg/IdeaProjects/orgfbtestwatcher/chromedriver");

        //webdriver options
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--disable-notifications");
        new ChromeDriver(options).manage();
        return new ChromeDriver(options);
    }

    Login login(WebDriver driver, String email, String password) {
        Login login = new Login();
        driver.get("https://www.facebook.com/");
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@id='pass']")).sendKeys(password);
        driver.findElement(By.xpath("//input[starts-with(@id, 'u_0_')][@value='Log In']")).click();
        if (!getLoggedInStatus(driver)) {
            login.setLoginSuccessful(false);
        } else {
            login.setLoginSuccessful(true);
        }
        login.setDriver(driver);
        return login;
    }

    public WebDriver getDriverOnGroup(WebDriver driver, String urlNumber) {
        String url = "http://www.facebook.com/groups/" + urlNumber;
        driver.get(url);
        return driver;
    }

    List<Post> getGroupPosts(WebDriver driver) {
        List<Post> returnList = new ArrayList<>();


        List<WebElement> clickSeeMore = driver.findElements(By.cssSelector("a[class='see_more_link'"));
        Actions actions = new Actions(driver);
        for (WebElement e : clickSeeMore) {
            actions.moveToElement(e).click().build().perform();
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        }


        List<WebElement> postHolder = driver.findElements(By.cssSelector("div[class*='_4-u2 mbm _4mrt _5jmm _5pat _5v3q _7cqq _4-u8']"));
        for (WebElement e : postHolder) {
            Post post = new Post();
            try {
                post.setDescription(e.findElement(By.cssSelector("div[class*='_5pbx']")).getText());
            } catch(NoSuchElementException ex) {
                post.setTitle(null);
            }

            try {
                post.setTitle(e.findElement(By.cssSelector("div[class='_l53']")).getText());
            } catch(NoSuchElementException ex) {
                post.setTitle(null);
            }


            try {
                post.setPrice(e.findElement(By.cssSelector("div[class='_l57']")).getText());
            } catch(NoSuchElementException ex) {
                post.setPrice(null);
            }

            try {
                post.setLocation(e.findElement(By.cssSelector("div[class='_l58']")).getText());
            } catch(NoSuchElementException ex) {
                post.setLocation(null);
            }

            // datetime
            long unixTime = 0;
            try {
                unixTime = Integer.parseInt(e.findElement(By.cssSelector("abbr[class='_5ptz timestamp livetimestamp']")).getAttribute("data-utime"));
            } catch(NoSuchElementException ex) { }

            // sometimes its a different way? thanks facebook
            try {
                unixTime = Integer.parseInt(e.findElement(By.cssSelector("abbr[class='_5ptz']")).getAttribute("data-utime"));
            } catch(NoSuchElementException ex) {

            }

            // to ms
            java.sql.Date date = new java.sql.Date(unixTime*1000L);
            // formatting
            //SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //dt.setTimeZone(TimeZone.getTimeZone("UTC"));
            //post.setDatetime(dt.format(date));
            post.setDatetime(date);

            // post url
            try {
                post.setUrl(e.findElement(By.cssSelector("a[class='_5pcq']")).getAttribute("href"));
            } catch(NoSuchElementException ex) { }

            // also sometimes a different way
            try {
                WebElement urlElement = e.findElement(By.cssSelector("span[class='fsm fwn fcg']"));
                post.setUrl(urlElement.findElement(By.cssSelector("a")).getAttribute("href"));
            } catch(NoSuchElementException ex) {

            }

            returnList.add(post);

        }
        return returnList;
    }

    List<Group> getGroupsFromFacebook(WebDriver driver) {
        List<Group> groupsList = new ArrayList<>();
        String pattern = "(\\/)(\\d+)(?=\\/)";
        Pattern urlNumber = Pattern.compile(pattern);

        //Getting group page html
        driver.get("https://www.facebook.com/groups/?category=membership");

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        boolean stop = false;
        while (!stop) {
            try {
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                WebElement seeMoreButton = driver.findElement(By.linkText("See more..."));
                seeMoreButton.click();
                jse.executeScript("document.getElementsByClassName('uiScrollableAreaWrap scrollable')[0].focus();");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                jse.executeScript("document.getElementsByClassName('uiScrollableAreaWrap scrollable')[0].scrollTop += 1100;");
            } catch (org.openqa.selenium.NoSuchElementException e) {
                stop = true;
            }
        }

        List<WebElement> groupInfo = driver.findElements(By.cssSelector("div[class='_2yaa"));
        for (WebElement e : groupInfo) {
            Group tempGroup = new Group();
            String url = e.findElement(By.className("_2yau")).getAttribute("href");
            String name = e.findElement(By.className("_2yav")).getText();
            Matcher m = urlNumber.matcher(url);
            if (m.find()) {
                tempGroup.setUrl(m.group(2));
            } else {
                System.out.println("No match");
            }
            tempGroup.setName(name);
            groupsList.add(tempGroup);
        }
        return groupsList;
    }

    boolean getLoggedInStatus(WebDriver driver) {
        try {
            driver.findElement(By.xpath("//input[starts-with(@id, 'u_0_')][@value='Log In']"));
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }



}

