import java.sql.*;

public class databaseSetup {

    private static final String POSTS_TABLE = "create table data " +
            "(id INTEGER not NULL, " +
            " price VARCHAR(255)";


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

