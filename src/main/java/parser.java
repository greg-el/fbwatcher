import org.openqa.selenium.WebDriver;


import java.util.List;


public class parser {
    public static void main (String[] args) {

        //creating methods instance
        final Methods methods = new Methods();
        final databaseSetup db = new databaseSetup();
        db.main();

        WebDriver driver = methods.createDriver();
        WebDriver loggedDriver = methods.login(driver);

        /*
        List<Post> groupPosts = methods.getGroupPosts(loggedDriver, "https://www.facebook.com/groups/fleetpeople");

        for (Post post: groupPosts) {
            System.out.println(post.toString());
        }
        */



        List<Group> groups = methods.getMemberGroups(loggedDriver);

        methods.addGroupsToDatabase(groups);

        loggedDriver.quit();


    }
}
