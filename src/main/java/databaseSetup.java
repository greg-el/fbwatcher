import java.sql.*;

public class databaseSetup {

    private static final String POSTS_TABLE = "create table posts " +
            " id BINARY(16) DEFAULT UUID() PRIMARY KEY, " +
            " description TEXT(63206) NOT NULL, " +
            " title VARCHAR(255), " +
            " price VARCHAR(7), " +
            " location VARCHAR(100), " +
            " datetime DATETIME NOT NULL, " +
            " url VARCHAR(150) NOT NULL, " +
            " group_id BINARY(16), " +
            " CONSTRAINT `fk_group_name` " +
            "   FOREIGN KEY(group_id) REFERENCES group (id) " +
            "   ON DELETE CASCADE " +
            "   ON UPDATE RESTRICT)";

    private static final String GROUP_TABLE = "create table groups(" +
            "id BINARY(16) DEFAULT UUID() PRIMARY KEY," +
            "name VARCHAR(100)," +
            "url VARCHAR(132)";



    public static void main(String[] args){
        Statement stmt = null;
        Connection connect = null;
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "";

        try {
            connect = DriverManager.getConnection(url, username, password);
            stmt = connect.createStatement();
            stmt.executeUpdate("CREATE DATABASE data");
            stmt.executeUpdate("USE Posts");
            stmt.executeUpdate(POSTS_TABLE);


        } catch (Exception e) {
            System.out.println(e);
        }

    }
}

