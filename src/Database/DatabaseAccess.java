package Database;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class DatabaseAccess {
    private static final String protocol = "jdbc";
        private static final String vendor = ":mysql:";
        private static final String location = "//localhost/";
        private static final String databaseName = "client_schedule";
        private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; //LOCAL
        private static final String driver = "com.mysql.cj.jdbc.Driver"; //Driver reference
        private static final String userName = "sqlUser"; // Username
        private static final String password = "Passw0rd!"; // Password
        public static Connection connection; // Connection Interface

        public static void openConnection(){
            try{
                Class.forName(driver);
                connection = DriverManager.getConnection(jdbcUrl, userName, password); //Reference Connection Object
                System.out.println("Connection Successful!");
            }
            catch (Exception e)
            {
                System.out.println("Error:" + e.getMessage());
            }
        }

        public static void closeConnection(){
            try{
                connection.close();
                System.out.println("Connection closed!");
            }
            catch(Exception e)
            {
                System.out.println("Error:" + e.getMessage());
            }
        }
}
