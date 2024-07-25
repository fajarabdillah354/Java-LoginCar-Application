package com.example.logincar;
import java.sql.*;


public class LoginDbConnection {

    public Connection databaselink;

    public Connection getConnection(){
        String databaseName = "loginCar";
        String username = "root";
        String password = "fahmifadilah25";
        String url = "jdbc:mysql://localhost:3306/"+databaseName;

        try {
            databaselink = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return databaselink;
    }


}
