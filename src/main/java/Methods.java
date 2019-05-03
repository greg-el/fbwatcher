import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.sql.*;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Methods {


    public WebDriver login(WebDriver driver) {
        Scanner login = new Scanner(System.in);
        System.out.println("Username: ");
        String email = login.nextLine();
        System.out.println("Password: ");
        String password = login.nextLine();
        driver.get("https://www.facebook.com/");
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@id='pass']")).sendKeys(password);
        driver.findElement(By.xpath("//input[starts-with(@id, 'u_0_')][@value='Log In']")).click();
        return driver;
    }

    public List<Group> getGroups(WebDriver driver) {
        List<Group> groupsList = new ArrayList<Group>();
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

    public List<Post> getGroupPosts(WebDriver driver, String url) {
        System.out.println("Collecting group posts...");
        List returnList = new ArrayList();
        driver.get(url);


        List<WebElement> clickSeeMore = driver.findElements(By.cssSelector("a[class='see_more_link'"));
        Actions actions = new Actions(driver);
        for (WebElement e : clickSeeMore) {
            actions.moveToElement(e).click().build().perform();
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        }

        Pattern urlNumber = Pattern.compile("(\\/)(\\d*)(?=\\/$)");

        List<WebElement> postHolder = driver.findElements(By.cssSelector("div[class*='_4-u2 mbm _4mrt _5jmm _5pat _5v3q _7cqq _4-u8']"));
        for (WebElement e : postHolder) {
            Post post = new Post();
            try {
                post.setDescription(e.findElement(By.cssSelector("div[class*='_5pbx']")).getText());

            } catch(NoSuchElementException ex) { }

            try {
                post.setTitle(e.findElement(By.cssSelector("div[class='_l53']")).getText());
            } catch(NoSuchElementException ex) { }

            try {
                post.setPrice(e.findElement(By.cssSelector("div[class='_l57']")).getText());
            } catch(NoSuchElementException ex) { }

            try {
                post.setLocation(e.findElement(By.cssSelector("div[class='_l58']")).getText());
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
            post.setDatetime(dt.format(date));


            // post url
            try {
                post.setUrl(e.findElement(By.cssSelector("a[class='_5pcq']")).getAttribute("href"));
            } catch(NoSuchElementException ex) { }

            // also sometimes a different way
            try {
                WebElement testurl = e.findElement(By.cssSelector("span[class='fsm fwn fcg']"));
                String postUrl = testurl.findElement(By.cssSelector("a")).getAttribute("href");
                post.setUrl(postUrl);

            } catch(NoSuchElementException ex) { }

            returnList.add(post);

        }
        return returnList;
    }

    public WebDriver createDriver() {
        //chrome webdriver executable location
        System.setProperty("webdriver.chrome.driver", "/home/greg/IdeaProjects/orgfbtestwatcher/chromedriver");

        //webdriver options
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--disable-notifications");
        WebDriver driver = new ChromeDriver(options);
        return driver;
    }

    public void addPostsToDatabase(List<Post> posts) {
        PreparedStatement pstmt;
        Connection connect;
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "";


            try {
                connect = DriverManager.getConnection(url, username, password);
                pstmt = connect.prepareStatement("INSERT INTO posts " +
                        "(description, title, price, location, datetime, url) VALUE" +
                        "(?, ?, ?, ?, ?, ?");
                for (Post post : posts) {
                    pstmt.setString(1, post.getDescription());
                    pstmt.setString(2, post.getTitle());
                    pstmt.setString(3, post.getPrice());
                    pstmt.setString(4, post.getLocation());
                    pstmt.setString(5, post.getDatetime());
                    pstmt.setString(6, post.getUrl());
                    pstmt.executeUpdate();
                }
                pstmt.close();
                connect.close();
            } catch (Exception e) {
                System.out.println(e);
            }

    }

    public void addGroupsToDatabase(List<Group> groups) {
        PreparedStatement pstmt;
        Connection connect;
        Statement stmt;
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "";
            try {
                connect = DriverManager.getConnection(url, username, password);
                stmt = connect.createStatement();
                stmt.executeUpdate("USE data;");
                pstmt = connect.prepareStatement("INSERT INTO groups " +
                        "(name, url) VALUE" +
                        "(?, ?)");
                for (Group group : groups) {
                    pstmt.setString(1, group.getName());
                    pstmt.setString(2, group.getUrl());
                    pstmt.executeUpdate();
                }
                connect.close();
                pstmt.close();
            } catch (Exception e) {
                System.out.println(e);
            }
    }

    public void addJoinedGroups(List<Group> groups) {
        PreparedStatement pstmt;
        Connection connect;
        Statement stmt;
        ResultSet rs;
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "";
        try {
            connect = DriverManager.getConnection(url, username, password);
            stmt = connect.createStatement();
            stmt.executeUpdate("USE data;");
            pstmt = connect.prepareStatement("SELECT * FROM groups WHERE url = ?");
            for (Group group : groups) {
                pstmt.setString(1, group.getUrl());
                rs = pstmt.executeQuery();
                while(rs.next()) {
                    if (rs.getString("url") == null) {
                        pstmt = connect.prepareStatement("INSERT INTO groups " +
                                "(name, url) VALUE" +
                                "(?, ?)");
                        pstmt.setString(1, group.getName());
                        pstmt.setString(2, group.getUrl());
                        pstmt.executeUpdate();
                    }
                }
            }
            connect.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void removeLeftGroups(List<Group> groups) {
        PreparedStatement pstmt;
        Connection connect;
        Statement stmt;
        ResultSet rs;
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "";
        try {
            connect = DriverManager.getConnection(url, username, password);
            stmt = connect.createStatement();
            stmt.executeUpdate("USE data;");
            pstmt = connect.prepareStatement("SELECT * FROM groups");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                boolean match = false;
                for (Group group : groups) {
                    System.out.println(group.getUrl());
                    System.out.println(rs.getString("url"));
                    if (group.getUrl().equals(rs.getString("url"))) {
                        match = true;
                    }
                }
                if (!match) {
                    System.out.println("Removing group: " + rs.getString("name"));
                    pstmt = connect.prepareStatement("DELETE FROM groups WHERE url=?");
                    pstmt.setString(1, rs.getString("url"));
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e ) {}
    }

}

