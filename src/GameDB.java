import java.sql.*;

public class GameDB {

    private static String location, dbName, username, password;

    /**
     * Connection to the game's MySQL database
     * @return The connection to the game's MySQL database
     * @throws SQLException in case connection with database failed
     */
    public static Connection dbCon() throws SQLException {

        try{

            location = "jdbc:mysql://folding03.ucd.ie:3306/";
            dbName = "db17210556";
            username = "u17210556";
            password = "COMP20300";

            //only for testing locally
            /*location = "jdbc:mysql://localhost:3306/";
            dbName = "battleshipdb";
            username = "asem";
            password = "0500821362";*/

        } catch(Exception ex) {
            System.out.println("Something went wrong with the database!");
            ex.printStackTrace();
        }

        return DriverManager.getConnection( location+dbName, username, password);
    }
}
