import java.sql.*;

public class databaseSetup {

    private static final String POSTS_TABLE = "create table data " +
            "(description VARCHAR(63206 ), " +
            " title VARCHAR(255), " +
            " price VARCHAR(7), " +
            " location VARCHAR(100), " +
            " datetime DATETIME, " +
            " url VARCHAR(150)";


    public static void main(String[] args){
        Statement stmt = null;
        Connection connect = null;
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "";

        try {
            connect = DriverManager.getConnection(url, username, password);
            stmt = connect.createStatement();
            stmt.executeUpdate("CREATE DATABASE Posts");
            stmt.executeUpdate("USE Posts");
            stmt.executeUpdate(POSTS_TABLE);


        } catch (Exception e) {
            System.out.println(e);
        }

    }
}

