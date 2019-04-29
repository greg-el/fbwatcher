import java.sql.*;

public class databaseSetup {

    private static final String POSTS_TABLE = "CREATE TABLE posts " +
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

    private static final String GROUP_TABLE = "CREATE TABLE groups(" +
            " id BINARY(16) DEFAULT UUID() PRIMARY KEY," +
            " name VARCHAR(100)," +
            " url VARCHAR(132)";

    private static final String UUID_TO_BIN = "CREATE FUNCTION UuidToBin(_uuid BINARY(36))" +
            " RETURNS BINARY(16)" +
            " LANGUAGE SQL  DETERMINISTIC  CONTAINS SQL  SQL SECURITY INVOKER " +
            " RETURN " +
            "   UNHEX(CONCAT( " +
            "       SUBSTR(__uuid, 15, 4) " +
            "       SUBSTR(__uuid, 10, 4) " +
            "       SUBSTR(__uuid, 1, 8) " +
            "       SUBSTR(__uuid, 20, 4) " +
            "       SUBSTR(__uuid, 25) )) ";

    private static final String BIN_TO_UUID = "CREATE FUNCTION BinToUuid(_uuid BINARY(16))" +
            " RETURNS BINARY(36)" +
            " LANGUAGE SQL  DETERMINISTIC  CONTAINS SQL  SQL SECURITY INVOKER " +
            " RETURN " +
            "   LCASE(CONACT_WS('-', " +
            "       HEX(SUBSTR(__uuid, 5, 4)) " +
            "       HEX(SUBSTR(__uuid, 3, 2)) " +
            "       HEX(SUBSTR(__uuid, 1, 2)) " +
            "       HEX(SUBSTR(__uuid, 9, 2)) " +
            "       HEX(SUBSTR(__uuid, 11)) )) ";

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
            stmt.executeUpdate("USE data");
            stmt.executeUpdate(POSTS_TABLE);
            stmt.executeUpdate(GROUP_TABLE);

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}

