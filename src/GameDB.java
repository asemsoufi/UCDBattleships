import java.sql.*;

public class GameDB {

    private static String dbName, username, password;

    public static Connection dbCon() throws SQLException {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbName = "battleshipdb";
            username = "asem";
            password = "0500821362";

        } catch(Exception ex) {
            System.out.println("Something went wrong with the database!");
            ex.printStackTrace();
        }

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/" +
                dbName, username, password);
    }

}
