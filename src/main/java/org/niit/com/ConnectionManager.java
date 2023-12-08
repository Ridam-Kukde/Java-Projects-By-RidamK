package org.niit.com;
import java.sql.*;
public class ConnectionManager {
    public static Connection con;

    public static void createConnection() {
        try {
            //load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Jukebox", "root", "ridam");
            //System.out.println("Connection With DataBase is Established");
        } catch (Exception ex) {
            System.out.println("Error in Connnection Manager" + ex);
        }
    }
    public static void cleanUp() throws SQLException {
        con.close();
    }
}
