package org.niit.com;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class UserImpl {
    static Scanner scanner = new Scanner(System.in);

    public static int authenticateUser() {
        try {
            Users users = new Users();
            ConnectionManager.createConnection();
            System.out.println("Enter your username: ");
            String user = scanner.next();

            try (Statement st = ConnectionManager.con.createStatement();
                 ResultSet rs = st.executeQuery("SELECT * FROM users WHERE username= '" + user + "'")) {
                if (rs.next()) {
                    try (ResultSet toSetUserId = st.executeQuery("SELECT userid FROM users WHERE username= '" + user + "'")) {
                        if (toSetUserId.next()) {
                            users.setUserId(toSetUserId.getInt(1));
                        }
                        return users.getUserId();
                    }
                } else {
                    System.out.println("Entered username does not exist");
                }
            }
        } catch (Exception exception) {
            System.out.println("Error in authenticating user: " + exception.getMessage());
        }
        return 0;
    }

    public static void createUser() {
        ConnectionManager.createConnection();
        boolean registered = false;
        while (!registered) {
            System.out.println("Enter your desired username: ");
            String newUser = scanner.next();
            //first check if the username entered already exists or not if not then add user to DB
            try {
                Statement st = ConnectionManager.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery("Select * from users where username= '" + newUser + "'");
                if (rs.next()) System.out.println("User: " + newUser + " already Exists!");
                else {
                    // If the username does not exist, proceed to add the new user to the database
                    ResultSet r = st.executeQuery("SELECT MAX(userid) FROM users");
                    int newuid = 1; // Default value if no user exists yet
                    if (r.next()) {
                        newuid = r.getInt(1) + 1;
                    }

                    System.out.println("Enter your number: ");
                    Double newNo = scanner.nextDouble();


                    // Insert the new user into the database
                    st.executeUpdate("INSERT INTO users (userid, username, mobileno) VALUES ('" + newuid + "','" + newUser + "', '" + newNo + "')");
                    System.out.println("User: " + newUser + " has been registered successfully!");
                    registered = true; // Set the flag to exit the loop
                }
            } catch (Exception e) {
                System.out.println("Error in creating user: " + e.getMessage());
            }
        }


    }
}
