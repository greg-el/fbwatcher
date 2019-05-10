import com.mchange.v2.c3p0.ComboPooledDataSource;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Methods {
    WebDriver createDriver() {
        //chrome webdriver executable location
        System.setProperty("webdriver.chrome.driver", "/home/greg/IdeaProjects/orgfbtestwatcher/chromedriver");

        //webdriver options
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--disable-notifications");
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

    List<Post> getGroupPosts(WebDriver driver, String urlNumber) {
        String url = "http://www.facebook.com/groups/" + urlNumber;
        System.out.println("Collecting group posts...");
        List<Post> returnList = new ArrayList<Post>();
        driver.get(url);

        String urlPattern = "(\\/)(\\d+)(\\/)";
        Pattern p = Pattern.compile(urlPattern);


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
                post.setTitle("");
            }

            try {
                post.setTitle(e.findElement(By.cssSelector("div[class='_l53']")).getText());
            } catch(NoSuchElementException ex) {
                post.setTitle("");
            }


            try {
                post.setPrice(e.findElement(By.cssSelector("div[class='_l57']")).getText());
            } catch(NoSuchElementException ex) {
                post.setPrice("");
            }

            try {
                post.setLocation(e.findElement(By.cssSelector("div[class='_l58']")).getText());
            } catch(NoSuchElementException ex) {
                post.setLocation("");
            }

            // datetime
            long unixTime = 0;
            try {
                unixTime = Integer.parseInt(e.findElement(By.cssSelector("abbr[class='_5ptz timestamp livetimestamp']")).getAttribute("data-utime"));
            } catch(NoSuchElementException ex) {
                System.out.println("No time");
                post.setDatetime("");
            }

            // sometimes its a different way? thanks facebook
            try {
                unixTime = Integer.parseInt(e.findElement(By.cssSelector("abbr[class='_5ptz']")).getAttribute("data-utime"));
            } catch(NoSuchElementException ex) {
                System.out.println("No time");
                post.setDatetime("");
            }

            // to ms
            Date date = new Date(unixTime*1000L);
            // formatting
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dt.setTimeZone(TimeZone.getTimeZone("UTC"));
            post.setDatetime(dt.format(date));


            // post url
            try {
                String urlBeforeRegex = e.findElement(By.cssSelector("a[class='_5pcq']")).getAttribute("href");
                Matcher m = p.matcher(urlBeforeRegex);
                while (m.find()) {
                    post.setUrl(m.group(2));
                }
            } catch(NoSuchElementException ex) {
                System.out.println("No url");
            }

            // also sometimes a different way
            try {
                WebElement urlElement = e.findElement(By.cssSelector("span[class='fsm fwn fcg']"));
                String urlBeforeRegex = urlElement.findElement(By.cssSelector("a")).getAttribute("href");
                Matcher m = p.matcher(urlBeforeRegex);
                while (m.find()) {
                    post.setUrl(m.group(2));
                }

            } catch(NoSuchElementException ex) {
                System.out.println("No url");
            }

            returnList.add(post);

        }
        return returnList;
    }

    void addPostsToDatabase(List<Post> posts) {
        try (Connection connect = C3p0DataSource.getConnection();
             Statement stmt = connect.createStatement();
             PreparedStatement pstmt = connect.prepareStatement("INSERT INTO posts " +
                     "(description, title, price, location, datetime, url) VALUE" +
                     "(?, ?, ?, ?, ?, ?"))
        {
            stmt.executeUpdate("USE data;");
            for (Post post : posts) {
                pstmt.setString(1, post.getDescription());
                pstmt.setString(2, post.getTitle());
                pstmt.setString(3, post.getPrice());
                pstmt.setString(4, post.getLocation());
                pstmt.setString(5, post.getDatetime());
                pstmt.setString(6, post.getUrl());
                pstmt.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    void addPostToDatabase(Post post) {
        try (Connection connect = C3p0DataSource.getConnection();
             Statement stmt = connect.createStatement();
             PreparedStatement pstmt = connect.prepareStatement("INSERT INTO posts " +
                     "(description, title, price, location, datetime, url) VALUE" +
                     "(?, ?, ?, ?, ?, ?)"))
        {
            stmt.executeUpdate("USE data;");
            pstmt.setString(1, post.getDescription());
            pstmt.setString(2, post.getTitle());
            pstmt.setString(3, post.getPrice());
            pstmt.setString(4, post.getLocation());
            pstmt.setString(5, post.getDatetime());
            pstmt.setString(6, post.getUrl());
            pstmt.executeUpdate();
            System.out.println("Adding post to database");
        } catch (Exception e) {
            System.out.println("addPostToDatabase" + e);
        }

    }

    void addGroupsToDatabase(List<Group> groups) {
        try (Connection connect = C3p0DataSource.getConnection();
             Statement stmt = connect.createStatement();
             PreparedStatement pstmt = connect.prepareStatement("INSERT INTO groups " +
                     "(name, url) VALUE" +
                     "(?, ?)"))
            {
            stmt.executeUpdate("USE data;");
                for (Group group : groups) {
                    pstmt.setString(1, group.getName());
                    pstmt.setString(2, group.getUrl());
                    pstmt.executeUpdate();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
    }

    void addJoinedGroups(List<Group> groups) {
        try (Connection connect = C3p0DataSource.getConnection();
             Statement stmt = connect.createStatement();
             PreparedStatement pstmtSelect = connect.prepareStatement("SELECT * FROM groups WHERE url = ?");
             PreparedStatement pstmtDelete = connect.prepareStatement("INSERT INTO groups " +
                     "(name, url) VALUE" +
                     "(?, ?)"))
        {
            stmt.executeUpdate("USE data;");
            for (Group group : groups) {
                pstmtSelect.setString(1, group.getUrl());
                try (ResultSet rs = pstmtSelect.executeQuery()) {
                    while(rs.next()) {
                        if (rs.getString("url") == null) {
                            pstmtDelete.setString(1, group.getName());
                            pstmtDelete.setString(2, group.getUrl());
                            pstmtDelete.executeUpdate();
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void removeLeftGroups(List<Group> groups) {
        try (Connection connect = C3p0DataSource.getConnection();
             Statement stmt = connect.createStatement();
             PreparedStatement pstmtSelect = connect.prepareStatement("SELECT url FROM groups");
             PreparedStatement pstmtDelete = connect.prepareStatement("DELETE FROM groups WHERE url=?"))
        {
            stmt.executeUpdate("USE data;");
            try (ResultSet rs = pstmtSelect.executeQuery()) {
                while (rs.next()) {
                    boolean match = false;
                    for (Group group : groups) {
                        if (group.getUrl().equals(rs.getString("url"))) {
                            match = true;
                        }
                    }
                    if (!match) {
                        pstmtDelete.setString(1, rs.getString("url"));
                        pstmtDelete.executeUpdate();
                    }
                }
            }
        } catch (Exception e ) {}
    }

    void addKeywordsToGroupDatabase(Group group, List<String> keywords) {
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmtSelect = connect.prepareStatement("SELECT id FROM groups WHERE name=?");
             PreparedStatement pstmtInsert = connect.prepareStatement("INSERT INTO keywords (keyword, group_id) VALUES (?, ?)");
             Statement stmt = connect.createStatement())
        {
            int id = 0;
            stmt.executeUpdate("USE data;");

            pstmtSelect.setString(1, group.getName());
            try (ResultSet rs = pstmtSelect.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt("id");
                }
            }
            for (String keyword : keywords) {
                pstmtInsert.setString(1, keyword);
                pstmtInsert.setInt(2, id);
                pstmtInsert.executeUpdate();
            }
        } catch (Exception e) {System.out.println("addKeywordsToGroupDatabase: " + e); }
    }

    void addKeywordToGroupDatabase(String groupName, String keyword) {
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmtSelect = connect.prepareStatement("SELECT id FROM groups WHERE name=?");
             PreparedStatement pstmtInsert = connect.prepareStatement("INSERT INTO keywords (keyword, group_id) VALUES (?, ?)");
             Statement stmt = connect.createStatement())
        {
            int id = 0;
            stmt.executeUpdate("USE data;");
            pstmtSelect.setString(1, groupName);
            try (ResultSet rs = pstmtSelect.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt("id");
                }
            }

            pstmtInsert.setString(1, keyword);
            pstmtInsert.setInt(2, id);
            pstmtInsert.executeUpdate();
        } catch (Exception e) {
            System.out.println("addKeyWordToGroupDatabase" + e);
        }
    }

    void removeKeywordFromGroupDatabase(String groupName, String keyword) {
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmtSelect = connect.prepareStatement("SELECT id FROM groups WHERE name=?");
             PreparedStatement pstmtDelete = connect.prepareStatement("DELETE FROM keywords WHERE keyword=? AND group_id=?");
             Statement stmt = connect.createStatement())
        {
            int id = 0;
            stmt.executeUpdate("USE data;");
            pstmtSelect.setString(1, groupName);
            try (ResultSet rs = pstmtSelect.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt("id");
                }
            }            pstmtDelete.setString(1, keyword);
            pstmtDelete.setInt(2, id);
            pstmtDelete.executeUpdate();
        } catch (Exception e) { }
    }

    List<Group> getGroupsFromFacebook(WebDriver driver) {
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

    List<Group> getGroupsFromDatabase() {
        List<Group> groups = new ArrayList<Group>();
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmt = connect.prepareStatement("SELECT * FROM groups;");
             Statement stmt = connect.createStatement())
        {
            stmt.executeUpdate("USE data;");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Group group = new Group(rs.getString("name"), rs.getString("url"), getGroupKeywordsFromName(rs.getString("name")));
                    groups.add(group);
                }
            }
        } catch (Exception e) {}
        return groups;
    }

    List<String> getGroupKeywordsFromName(String name) {
        List<String> groups = new ArrayList<String>();
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmt = connect.prepareStatement("SELECT * FROM groups INNER JOIN keywords ON groups.id = keywords.group_id WHERE keywords.group_id=?;");
             Statement stmt = connect.createStatement())
        {

            stmt.executeUpdate("USE data;");
            pstmt.setString(1, getGroupIdFromName(name));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    groups.add(rs.getString("keyword"));
                }
            }
        } catch (Exception e) {}
        return groups;
    }

    List<String> getGroupListFromDatabase() {
        List<String> groupNames = new ArrayList<>();
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmt = connect.prepareStatement("SELECT name FROM groups;");
             Statement stmt = connect.createStatement())

        {
            stmt.executeUpdate("USE data;");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("test0");
                    groupNames.add(rs.getString("name"));
                }
            }
        } catch (Exception e) {}

        return groupNames;
    }

    String getGroupUrlFromName(String name) {
        String url = null;
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmt = connect.prepareStatement("SELECT url FROM groups WHERE name=?;");
             Statement stmt = connect.createStatement())
        {
            stmt.executeUpdate("USE data;");
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    url = rs.getString("url");
                }
            }
        } catch (Exception e) {System.out.println("getGroupUrlFromName: " + e);}
        return url;
    }

    Group getGroupDataFromName(String name) {
        Group group = new Group();
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmt = connect.prepareStatement("SELECT url FROM groups WHERE name=?;");
             Statement stmt = connect.createStatement())
        {
            stmt.executeUpdate("USE data;");
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    group.setName(name);
                    group.setUrl(rs.getString("url"));
                    group.setKeywords(getGroupKeywordsFromName(name));
                }
            }
        } catch (Exception e) {
            System.out.println("getGroupDataFromName: " + e);
        }
        return group;
    }

    boolean containsKeyword(Post post, List<String> keywords) {
        for (String keyword : keywords) {
            //System.out.println("Current post description: " + post.getDescription());
            //System.out.println("Current post title: " + post.getTitle());
            //System.out.println("Current keyword: " + keyword);
            if (post.getDescription() != null && post.getDescription().contains(keyword) || post.getTitle() != null && post.getTitle().contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    boolean isPostInDatabase(Post post) {
        String postExist = null;
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmt = connect.prepareStatement("SELECT * FROM posts WHERE url=? LIMIT 1");
             Statement stmt = connect.createStatement())
        {
            stmt.executeUpdate("USE data;");
            pstmt.setString(1, post.getUrl());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    postExist = rs.getString("url");
                }
            }
            if (postExist != null) {
                System.out.println("Post already in database");
                return true;

            }

        } catch (Exception e) {System.out.println("isPostInDatabase " + e); }
        return false;
    }

    boolean getLoggedInStatus(WebDriver driver) {
        driver.get("https://www.facebook.com");
        try {
            driver.findElement(By.xpath("//input[starts-with(@id, 'u_0_')][@value='Log In']"));
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    private String getGroupIdFromName(String name) {
        String id = null;
        try (Connection connect = C3p0DataSource.getConnection();
             PreparedStatement pstmt = connect.prepareStatement("SELECT * FROM groups WHERE name=?");
             Statement stmt = connect.createStatement())

        {
            stmt.executeUpdate("USE data;");
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    id = rs.getString("id");
                }
            }

        } catch (Exception e) {System.out.println("getGroupIdFromName: " + e);}
        return id;
    }

}

