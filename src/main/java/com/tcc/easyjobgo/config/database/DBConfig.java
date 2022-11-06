package com.tcc.easyjobgo.config.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tcc.easyjobgo.exception.DBException;

public class DBConfig {
    
    private static String driver = "org.postgresql.Driver";
    private static String url = "jdbc:postgresql://ec2-35-170-21-76.compute-1.amazonaws.com:5432/d45202g1ck0jku";
    private static String user = "doniqxvwipslwh";
    private static String password = "ae3eb5ba6d90ca710beab8a0cc1c0bafa19be58b24860f3295647e6f84f2805d";

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
