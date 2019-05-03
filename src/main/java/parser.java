import org.openqa.selenium.WebDriver;


import java.util.ArrayList;
import java.util.List;


public class parser {
    public static void main (String[] args) {

        //creating methods instance
        final Methods methods = new Methods();


        //WebDriver driver = methods.createDriver();
        //WebDriver loggedDriver = methods.login(driver);

/*
        List<Post> groupPosts = methods.getGroupPosts(loggedDriver, "https://www.facebook.com/groups/fleetpeople");

        for (Post post: groupPosts) {
            System.out.println(post.toString());
        }
*/



        List<Group> groups = new ArrayList<Group>();
        Group group = new Group();
        group.setUrl("1906268259697392");
        group.setName("Fleet Rants");

        methods.removeLeftGroups(groups);

        //loggedDriver.quit();


    }
}
