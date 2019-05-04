import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Tests {
    public void addTestGroupsToDatabase() {
        PreparedStatement pstmt;
        Connection connect;
        Statement stmt;
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "";
        try {
            connect = DriverManager.getConnection(url, username, password);
            stmt = connect.createStatement();
            stmt.executeUpdate("USE data;");
            pstmt = connect.prepareStatement("INSERT INTO groups " +
                    "(name, url) VALUE" +
                    "(?, ?)");
            pstmt.setString(1, "UK musicians for gigs ");
            pstmt.setString(2, "450716634941881");
            pstmt.executeUpdate();

            pstmt.setString(1, "UK GUITAR / AMP / GEAR EXCHANGE");
            pstmt.setString(2, "365219143557726");
            pstmt.executeUpdate();

            pstmt.setString(1, "FARNHAM RANTS");
            pstmt.setString(2, "1437523589841692");
            pstmt.executeUpdate();

            pstmt.close();
            connect.close();
        } catch (Exception e) {}
    }

    public List<Post> returnTestPosts() {
        List<Post> posts = new ArrayList<Post>();
        Post post = new Post();
        post.setDescription("I have scientifically determined that Morbid Stuff through these headphones creates a new atmospheric experience that really brings out the scrappiness of the album. This is the TRUE way Pup was meant to be experienced.");
        post.setTitle("I love PUP");
        post.setLocation("The sticks");
        post.setPrice("£2.50");
        post.setDatetime("2019-05-04 20:02:41");
        post.setUrl("https://www.facebook.com/groups/146278586066577/permalink/314843802543387/");
        posts.add(post);

        Post post1 = new Post();
        post1.setDescription("Have they played see you at your funeral at any of these shows? No set list I’ve seen so far has it and I wanna sing my heart out to the chorus with everyone \uD83E\uDD18\uD83D\uDE14");
        post1.setTitle("null");
        post1.setLocation("null");
        post1.setPrice("null");
        post1.setDatetime("2019-05-04 20:06:53");
        post1.setUrl("https://www.facebook.com/groups/146278586066577/permalink/314844955876605/");
        posts.add(post1);

        return posts;
        }

}
