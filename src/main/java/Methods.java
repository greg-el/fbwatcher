import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class Methods {


    public WebDriver login(String email, String password, WebDriver driver) {
        driver.get("https://www.facebook.com/");
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@id='pass']")).sendKeys(password);
        driver.findElement(By.xpath("//input[starts-with(@id, 'u_0_')][@value='Log In']")).click();
        return driver;
    }

    public List getMemberGroups(WebDriver driver) {
        List groupsList = new ArrayList();

        //Getting group page html
        driver.get("https://www.facebook.com/groups/?category=membership");
        List<WebElement> groupInfo = driver.findElements(By.cssSelector("div[class='_266w"));
        for (WebElement e : groupInfo) {
            GroupObject tempGroup = new GroupObject();
            String test = e.findElement(By.cssSelector("a")).getAttribute("href");
            int length = test.length();
            tempGroup.url = test.substring(0, length-22);
            tempGroup.name = e.getText();
            groupsList.add(tempGroup);
        }
        return groupsList;
    }

    public List getGroupPosts(WebDriver driver, String url) {

        System.out.println(url);
        System.out.println("Collecting group posts...");
        List returnList = new ArrayList();
        driver.get(url);


        List<WebElement> clickSeeMore = driver.findElements(By.cssSelector("a[class='see_more_link'"));
        Actions actions = new Actions(driver);
        for (WebElement e : clickSeeMore) {
            actions.moveToElement(e).click().build().perform();
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        }


        List<WebElement> postHolder = driver.findElements(By.cssSelector("div[class*='_4-u2 mbm _4mrt _5jmm _5pat _5v3q _7cqq _4-u8']"));
        for (WebElement e : postHolder) {
            PostObject postObject = new PostObject();
            try {
                postObject.description = e.findElement(By.cssSelector("div[class*='_5pbx']")).getText();

            } catch(NoSuchElementException ex) { }

            try {
                postObject.title = e.findElement(By.cssSelector("div[class='_l53']")).getText();
            } catch(NoSuchElementException ex) { }

            try {
                postObject.price = e.findElement(By.cssSelector("div[class='_l57']")).getText();
            } catch(NoSuchElementException ex) { }

            try {
                postObject.location = e.findElement(By.cssSelector("div[class='_l58']")).getText();
            } catch(NoSuchElementException ex) { }

            // datetime
            long unixTime = 0;
            try {
                unixTime = Integer.parseInt(e.findElement(By.cssSelector("abbr[class='_5ptz timestamp livetimestamp']")).getAttribute("data-utime"));
            } catch(NoSuchElementException ex) { }

            // sometimes its a different way? thanks facebook
            try {
                unixTime = Integer.parseInt(e.findElement(By.cssSelector("abbr[class='_5ptz']")).getAttribute("data-utime"));
            } catch(NoSuchElementException ex) { }

            // to ms
            Date date = new Date(unixTime*1000L);
            // formatting
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dt.setTimeZone(TimeZone.getTimeZone("UTC"));
            postObject.datetime = dt.format(date);


            // post url
            try {
                postObject.url = e.findElement(By.cssSelector("a[class='_5pcq']")).getAttribute("href");
            } catch(NoSuchElementException ex) { }

            // also sometimes a different way
            try {
                WebElement testurl = e.findElement(By.cssSelector("span[class='fsm fwn fcg']"));
                String test2 = testurl.findElement(By.cssSelector("a")).getAttribute("href");
                postObject.url = test2;

            } catch(NoSuchElementException ex) { }

            returnList.add(postObject);

        }
        return returnList;
    }

}

