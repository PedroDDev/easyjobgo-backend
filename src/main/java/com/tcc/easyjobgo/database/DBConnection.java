package com.tcc.easyjobgo.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    
    private static String driver = "org.postgresql.Driver";
    private static String url = "jdbc:postgresql://localhost:5432/postgres";
    private static String user = "postgres";
    private static String password = "easyjobgodb";

    public static Connection startConnection(){
        Connection cnn = null;

        try{
            Class.forName(driver);
            cnn = DriverManager.getConnection(url, user, password);
            System.out.println("DB Connected!");

        }
        catch (Exception e){
        
        }

        return cnn;
    }

    public static void closeConnection(Connection cnn){
        if(cnn != null){
            try {
                cnn.close();
                System.out.println("DB Disconnected!");
            }
            catch (Exception e) {
            
            }
        }
    }

}
