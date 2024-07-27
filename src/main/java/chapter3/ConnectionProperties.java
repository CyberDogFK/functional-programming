package chapter3;

import java.sql.Connection;
import java.util.Properties;

public class ConnectionProperties {
    private String user;
    private String password;
    Properties properties = new Properties();

    public ConnectionProperties user(String user) {
        this.user = user;
        properties.setProperty("user", this.user);
        return this;
    }

    public ConnectionProperties password(String password) {
        this.password = password;
        properties.setProperty("password", this.password);
        return this;
    }

    public Properties properties() {
        return properties;
    }

    public static void main(String[] args) {
        Connection conn = null;
        ConnectionProperties cp = new ConnectionProperties();
        cp
                .user("user")
                .password("password");
//        conn = DriverManager.getConnection(
//                "jdbc:" + this.dbms + "://" + this.servername +
//                        "" this.portNumber + "/", cp.properties
//        )
    }
}
