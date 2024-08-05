package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {
    final String URL = "jdbc:mysql://localhost:3306/freelancy";
    final String USER = "root";
    final String PASS = "";
    Connection connection;
    private static MyDatabase instance;

    private MyDatabase() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //solution pour faire la connexion  une seule fois au chaque req
    public static MyDatabase getInstance(
    ) {
        if (instance == null)
            instance = new MyDatabase();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}



