package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static DatabaseConnection db = new DatabaseConnection();
    public Connection databaseLink;

    public Connection getConnection(){
        String mysqlConnUrl = "jdbc:mysql://localhost/dingenarul";
        String mysqlUserName = "root";
        String mysqlPassword = "";
        try{
            databaseLink = DriverManager.getConnection(mysqlConnUrl, mysqlUserName, mysqlPassword);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return databaseLink;
    }

}
