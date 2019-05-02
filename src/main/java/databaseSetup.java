import java.sql.*;

public class databaseSetup {

    private static final String POSTS_TABLE = "CREATE TABLE posts (" +
            " id MEDIUMINT PRIMARY KEY AUTO_INCREMENT, " +
            " description TEXT(63206) NOT NULL, " +
            " title VARCHAR(255), " +
            " price VARCHAR(7), " +
            " location VARCHAR(100), " +
            " datetime DATETIME NOT NULL, " +
            " url VARCHAR(150) NOT NULL, " +
            " group_id MEDIUMINT, " +
            " CONSTRAINT `fk_group_name` " +
            "   FOREIGN KEY(group_id) REFERENCES groups (id) " +
            "   ON DELETE CASCADE " +
            "   ON UPDATE RESTRICT" +
            " ); ";

    private static final String GROUP_TABLE = "CREATE TABLE groups (" +
            " id MEDIUMINT PRIMARY KEY AUTO_INCREMENT, " +
            " name VARCHAR(100)," +
            " url VARCHAR(132)" +
            " );" ;



    public static void main(){
        Statement stmt;
        Connection connect;
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "";

        try {
            connect = DriverManager.getConnection(url, username, password);
            stmt = connect.createStatement();
            stmt.executeUpdate("CREATE DATABASE data");
            stmt.executeUpdate("USE data");
            stmt.executeUpdate(GROUP_TABLE);
            stmt.executeUpdate(POSTS_TABLE);


        } catch (Exception e) {
            System.out.println(e);
        }

    }
}

