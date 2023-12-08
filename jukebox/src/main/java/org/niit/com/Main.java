package org.niit.com;
import java.sql.SQLException;
import java.util.*;
public class Main
{
    public static int getUserInput(Scanner scanner) {
    while (true) {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // Clear the invalid input from scanner buffer
        }
    }
}

        public static void main(String[] args) throws SQLException {
            try {
                ConnectionManager.createConnection();

                Scanner sc = new Scanner(System.in);

                System.out.println(" ----------------------------------------------------------------------------------");
                System.out.println("|                                  JUKEBOX                                         |");
                System.out.println(" ----------------------------------------------------------------------------------");
                System.out.println("|                                  Welcome                                         |");
                System.out.println(" ----------------------------------------------------------------------------------");
                System.out.println("                             Already registered? ---->Enter 0                      ");
                System.out.println("                                 New User?       ---->Enter 1                      ");
                System.out.println("-----------------------------------------------------------------------------------");

                int choice = getUserInput(sc);

                switch (choice) {
                    case 0: {
                        int userId = UserImpl.authenticateUser();
                        if (userId != 0) {
                            Menu.MainMenu(userId);
                        } else {
                            System.out.println("Authentication failed.");
                        }
                        break;
                    }
                    case 1: {
                        UserImpl.createUser();
                        int userId = UserImpl.authenticateUser();
                        if (userId != 0) {
                            Menu.MainMenu(userId);
                        } else {
                            System.out.println("Authentication failed.");
                        }
                        break;
                    }
                    default: {
                        System.out.println("Invalid choice.");
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ConnectionManager.cleanUp(); // Close the connection when done
            }
        }
}
