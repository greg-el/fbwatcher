import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class FacebookTest {

    @Test
    public void getGroupPostsReturnsValues() {
        Facebook facebook = new Facebook();
        WebDriver driver = facebook.createDriver();
        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine();
        String password = scanner.nextLine();
        Login login = facebook.login(driver, "fbgroupwatcher@gmail.com", "Testing123");
        WebDriver loggedDriver = login.getDriver();
        List<Post> returnList = facebook.getGroupPosts(loggedDriver, "146278586066577"); //TODO: this doesnt work


        for (Post post : returnList) {
            assertTrue(post.getTitle() instanceof String || post.getTitle() == null);
            assertTrue(post.getDescription() instanceof String || post.getDescription() == null);
            assertTrue(post.getLocation() instanceof String || post.getLocation() == null);
            assertTrue(post.getPrice() instanceof String || post.getPrice() == null);
            assertTrue(post.getDatetime() instanceof Date);
            assertTrue(post.getUrl() instanceof String);

        }
    }
}