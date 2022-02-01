package Database;

import java.sql.Connection;
import java.sql.DriverManager;

/**DatabaseConnection Class opens, closes, and retrieves a connection to the database.*/
public abstract class DatabaseConnection {
    private static final String protocol = "jdbc";
        private static final String vendor = ":mysql:";
        private static final String location = "//localhost/";
        private static final String databaseName = "client_schedule";
        private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; //LOCAL
        private static final String driver = "com.mysql.cj.jdbc.Driver"; //Driver reference
        private static final String userName = "sqlUser"; // Username
        private static final String password = "Passw0rd!"; // Password
        public static Connection connection; // Connection Interface

        /**Opens a connection to the database. Called in Main*/
        public static void openConnection(){
            try{
                Class.forName(driver);
                connection = DriverManager.getConnection(jdbcUrl, userName, password); //Reference Connection Object
            }
            catch (Exception e)
            {
                System.out.println("Error:" + e.getMessage());
            }
        }

        /**Retrieves a connection to the database.*/
        public static Connection getConnection(){
            return connection;
        }

        /**Ends connection to database. Closes at ending of application.*/
        public static void closeConnection(){
            try{
                connection.close();
            }
            catch(Exception e)
            {
                System.out.println("Error:" + e.getMessage());
            }
        }
}
