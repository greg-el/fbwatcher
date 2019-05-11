import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.net.URL;

public class main {
    public static void test (String[] args) {

        //creating fbScraper instance
        final FbScraper fbScraper = new FbScraper();
        final Database database = new Database();

        //ClassLoader classLoader = getClass().getClassLoader();
        //java.net.URL url = classLoader.getResource("test_html.html");
        //File file = new File(url);
        //System.out.println(url);

        //Tests tests = new Tests();
        //tests.addTestGroupsToDatabase();
        //WebDriver driver = fbScraper.createDriver();
        //Login login = fbScraper.login(driver, "gr3gl@hotmail.com", "Bobfriend1");

        //List<Post> test = fbScraper.getGroupPosts(login.getDriver(), "667402093276096");
        //for (Post post : test){
        //    if (!fbScraper.isPostInDatabase(post)){
        //        fbScraper.addPostToDatabase(post);
        //    }
        //}



        //List<Post> groupPosts = fbScraper.getGroupPosts(loggedDriver, "146278586066577");
        //for (Post post : groupPosts) {
        //    System.out.println(fbScraper.containsKeyword(post, "PUP Morbid Ska-Posting", Arrays.asList("Morbid", "Stuff")));
        //}
        //fbScraper.addPostsToDatabase(groups);


        //List<Group> groups = fbScraper.getGroupsFromFacebook(loggedDriver);
        //fbScraper.addGroupsToDatabase(groups);


        //Group group = new Group();
        //group.setUrl("1437523589841692");
        //group.setName("FARNHAM RANTS");
        //List<String> keywords = Arrays.asList("jazz", "trumpet");
        //fbScraper.addKeywordsToGroupDatabase(group, keywords);


        //Adding group with keywords test
        //List<Group> groups = new ArrayList<Group>();
        //Group group = new Group();
        //group.setUrl("1437523589841692");
        //group.setName("FARNHAM RANTS");
        //group.setKeywords(Arrays.asList("the", "it"));
        //groups.add(group);
        //fbScraper.addGroupsToDatabase(groups);

        //Group test = fbScraper.getGroupDataFromName("FARNHAM RANTS");
        //System.out.println(test.getName());
        //System.out.println(test.getUrl());
        //for (String keyword : test.getKeywords()) {
        //    System.out.println(keyword);
        //}


        //loggedDriver.quit();

        //List<String> groups = database.getGroupListFromDatabase();
        //for (String group : groups) {
        //    System.out.println(group);
        //}

    }
}
