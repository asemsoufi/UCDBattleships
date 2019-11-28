import java.sql.*;

public class GameDB {

    private static String location, dbName, username, password;

    public static Connection dbCon() throws SQLException {

        try{

            /*location = "jdbc:mysql://folding03.ucd.ie:3306/";
            dbName = "db17210556";
            username = "u17210556";
            password = "COMP20300";*/

            location = "jdbc:mysql://localhost:3306/";
            dbName = "battleshipdb";
            username = "asem";
            password = "0500821362";

        } catch(Exception ex) {
            System.out.println("Something went wrong with the database!");
            ex.printStackTrace();
        }

        return DriverManager.getConnection( location+dbName, username, password);
    }
}
