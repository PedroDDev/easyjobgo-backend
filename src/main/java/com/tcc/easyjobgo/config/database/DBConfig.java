package com.tcc.easyjobgo.config.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tcc.easyjobgo.exception.DBException;

public class DBConfig {
    
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
            throw new DBException(e.getMessage(), e.getCause());
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
                throw new DBException(e.getMessage(), e.getCause());
            }
        }
    }

    public static void closePreparedStatment(PreparedStatement ps){
        if(ps != null){
            try {
                ps.close();
                System.out.println("PS Closed!");
            }
            catch (Exception e) {
                throw new DBException(e.getMessage(), e.getCause());
            }
        }
    }
    public static void closeResultSet(ResultSet rs){
        if(rs != null){
            try {
                rs.close();
                System.out.println("RS Closed!");
            }
            catch (Exception e) {
                throw new DBException(e.getMessage(), e.getCause());
            }
        }
    }
}
