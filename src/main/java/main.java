import org.openqa.selenium.WebDriver;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class main {
    public static void main (String[] args) {

        //creating methods instance
        final Methods methods = new Methods();


        Tests tests = new Tests();
        tests.addWrongTypeToDatabase();



        //databaseSetup.main();

        //Tests tests = new Tests();
        //tests.addTestGroupsToDatabase();
        //WebDriver driver = methods.createDriver();
        //Login login = methods.login(driver, "gr3gl@hotmail.com", "Bobfriend1");

        //List<Post> test = methods.getGroupPosts(login.getDriver(), "667402093276096");
        //for (Post post : test){
        //    if (!methods.isPostInDatabase(post)){
        //        methods.addPostToDatabase(post);
        //    }
        //}



        //List<Post> groupPosts = methods.getGroupPosts(loggedDriver, "146278586066577");
        //for (Post post : groupPosts) {
        //    System.out.println(methods.containsKeyword(post, "PUP Morbid Ska-Posting", Arrays.asList("Morbid", "Stuff")));
        //}
        //methods.addPostsToDatabase(groups);


        //List<Group> groups = methods.getGroupsFromFacebook(loggedDriver);
        //methods.addGroupsToDatabase(groups);


        //Group group = new Group();
        //group.setUrl("1437523589841692");
        //group.setName("FARNHAM RANTS");
        //List<String> keywords = Arrays.asList("jazz", "trumpet");
        //methods.addKeywordsToGroupDatabase(group, keywords);


        //Adding group with keywords test
        //List<Group> groups = new ArrayList<Group>();
        //Group group = new Group();
        //group.setUrl("1437523589841692");
        //group.setName("FARNHAM RANTS");
        //group.setKeywords(Arrays.asList("the", "it"));
        //groups.add(group);
        //methods.addGroupsToDatabase(groups);

        //Group test = methods.getGroupDataFromName("FARNHAM RANTS");
        //System.out.println(test.getName());
        //System.out.println(test.getUrl());
        //for (String keyword : test.getKeywords()) {
        //    System.out.println(keyword);
        //}


        //loggedDriver.quit();

        //List<Group> groups = methods.getGroupsFromDatabase();
        //for (Group group : groups) {
        //    System.out.println(group.toString());
        //}

    }
}
