import org.openqa.selenium.WebDriver;


import java.util.List;


public class parser {
    public static void main (String[] args) {
        /*
        //creating methods instance
        final Methods methods = new Methods();

        WebDriver driver = methods.createDriver();

        WebDriver loggedDriver = methods.login(driver);

        List<PostObject> groupPosts = methods.getGroupPosts(loggedDriver, "https://www.facebook.com/groups/fleetpeople");

        for (PostObject post: groupPosts) {
            System.out.println(post.title);
        }

        loggedDriver.quit();

         */
        Tests tests = new Tests();
        tests.testGroupReturn();
    }
}
