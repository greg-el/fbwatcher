import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.junit.Assert.*;

public class Tests {

    public void testGroupReturn() {
        Methods methods = new Methods();
        WebDriver driver = methods.createDriver();
        WebDriver loggedDriver = methods.login(driver);
        List<Post> output = methods.getGroupPosts(loggedDriver, "https://www.facebook.com/groups/1792153421069245");


        for (Post post: output) {
            assertEquals(post.getClass().getName(), "".getClass().getName());
        }

    }
}
