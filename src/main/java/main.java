import org.openqa.selenium.WebDriver;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class main {
    public static void main (String[] args) {

        //creating methods instance
        final Methods methods = new Methods();
        //databaseSetup.main();
        //Tests tests = new Tests();
        //tests.addTestGroupsToDatabase();

        //WebDriver driver = methods.createDriver();
        //WebDriver loggedDriver = methods.login(driver);

        //List<Post> groupPosts = methods.getGroupPosts(loggedDriver, "https://www.facebook.com/groups/fleetpeople");
        //methods.addPostsToDatabase(groups);


        //List<Group> groups = methods.getGroups(loggedDriver);
        //methods.removeLeftGroups(groups);


        Group group = new Group();
        group.setUrl("1437523589841692");
        group.setName("FARNHAM RANTS");
        List<String> keywords = Arrays.asList("jazz", "trumpet");
        methods.addKeywordsToGroup(group, keywords);



        //List<Group> groups = new ArrayList<Group>();
        //Group group = new Group();
        //group.setUrl("1437523589841692");
        //group.setName("FARNHAM RANTS");
        //group.setKeywords(Arrays.asList("the", "it"));
        //groups.add(group);
        //methods.addGroupsToDatabase(groups);
//


        //loggedDriver.quit();

        //List<Group> groups = methods.getGroupsFromDatabase();
        //for (Group group : groups) {
        //    System.out.println(group.toString());
        //}

    }
}
