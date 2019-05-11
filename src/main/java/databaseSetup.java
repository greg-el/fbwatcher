import java.sql.Connection;
import java.sql.Statement;

public class databaseSetup {

    private static final String POSTS_TABLE = "CREATE TABLE posts (" +
            " id MEDIUMINT PRIMARY KEY AUTO_INCREMENT, " +
            " description TEXT(63206) NOT NULL, " +
            " title VARCHAR(255), " +
            " price VARCHAR(10), " +
            " location VARCHAR(100), " +
            " datetime DATETIME NOT NULL, " +
            " url VARCHAR(30) NOT NULL, " +
            " group_id MEDIUMINT, " +
            " CONSTRAINT `fk_group_name_posts` " +
            "   FOREIGN KEY(group_id) REFERENCES groups (id) " +
            "   ON DELETE CASCADE " +
            "   ON UPDATE RESTRICT" +
            " ); ";

    private static final String GROUPS_TABLE = "CREATE TABLE groups (" +
            " id MEDIUMINT PRIMARY KEY AUTO_INCREMENT, " +
            " name VARCHAR(100) NOT NULL, " +
            " url VARCHAR(132) NOT NULL " +
            " );" ;

    private static final String KEYWORDS_TABLE = "CREATE TABLE keywords (" +
            " id MEDIUMINT PRIMARY KEY AUTO_INCREMENT, " +
            " keyword VARCHAR(50) NOT NULL, " +
            " group_id MEDIUMINT, " +
            " CONSTRAINT `fk_group_name_keywords` " +
            "   FOREIGN KEY(group_id) REFERENCES groups (id) " +
            "   ON DELETE CASCADE " +
            "   ON UPDATE RESTRICT" +
            " ); ";

    public static void main(){
        try (Connection connect = C3p0DataSource.getConnection();
             Statement stmt = connect.createStatement())
        {
            stmt.executeUpdate("CREATE DATABASE data");
            stmt.executeUpdate("USE data");
            stmt.executeUpdate(GROUPS_TABLE);
            stmt.executeUpdate(POSTS_TABLE);
            stmt.executeUpdate(KEYWORDS_TABLE);
        } catch (Exception e) {
            System.out.println("Database creation error" + e);
        }

    }
}

