import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;


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



}
