import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

class C3p0DataSource {
    private static ComboPooledDataSource cpds = new ComboPooledDataSource();

    static {
        try {
            cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
            cpds.setJdbcUrl("jdbc:mysql://localhost:3306/");
            cpds.setUser("root");
            cpds.setPassword("");
            cpds.setMinPoolSize(5);
            cpds.setAcquireIncrement(5);
            cpds.setMaxPoolSize(20);
            cpds.setTestConnectionOnCheckout(true);
            cpds.setMaxIdleTime(600000);
            cpds.setMaxConnectionAge(18000000);
        } catch (PropertyVetoException e) {
            System.out.println("C3P0 datasource connection error " + e);
        }
    }

    static Connection getConnection() throws SQLException {
        return  cpds.getConnection();
    }


    private C3p0DataSource(){}
}
