import java.util.List;


public class main {
    public static void main (String[] args) {

        //creating facebook instance
        final Facebook facebook = new Facebook();
        final Database database = new Database();





        //databaseSetup.main();

        //Tests tests = new Tests();
        //tests.addTestGroupsToDatabase();
        //WebDriver driver = facebook.createDriver();
        //Login login = facebook.login(driver, "gr3gl@hotmail.com", "Bobfriend1");

        //List<Post> test = facebook.getGroupPosts(login.getDriver(), "667402093276096");
        //for (Post post : test){
        //    if (!facebook.isPostInDatabase(post)){
        //        facebook.addPostToDatabase(post);
        //    }
        //}



        //List<Post> groupPosts = facebook.getGroupPosts(loggedDriver, "146278586066577");
        //for (Post post : groupPosts) {
        //    System.out.println(facebook.containsKeyword(post, "PUP Morbid Ska-Posting", Arrays.asList("Morbid", "Stuff")));
        //}
        //facebook.addPostsToDatabase(groups);


        //List<Group> groups = facebook.getGroupsFromFacebook(loggedDriver);
        //facebook.addGroupsToDatabase(groups);


        //Group group = new Group();
        //group.setUrl("1437523589841692");
        //group.setName("FARNHAM RANTS");
        //List<String> keywords = Arrays.asList("jazz", "trumpet");
        //facebook.addKeywordsToGroupDatabase(group, keywords);


        //Adding group with keywords test
        //List<Group> groups = new ArrayList<Group>();
        //Group group = new Group();
        //group.setUrl("1437523589841692");
        //group.setName("FARNHAM RANTS");
        //group.setKeywords(Arrays.asList("the", "it"));
        //groups.add(group);
        //facebook.addGroupsToDatabase(groups);

        //Group test = facebook.getGroupDataFromName("FARNHAM RANTS");
        //System.out.println(test.getName());
        //System.out.println(test.getUrl());
        //for (String keyword : test.getKeywords()) {
        //    System.out.println(keyword);
        //}


        //loggedDriver.quit();

        List<String> groups = database.getGroupListFromDatabase();
        for (String group : groups) {
            System.out.println(group);
        }

    }
}
