package com.tcc.easyjobgo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import com.tcc.easyjobgo.config.database.DBConfig;
import com.tcc.easyjobgo.exception.DBException;
import com.tcc.easyjobgo.model.Token;
import com.tcc.easyjobgo.repository.ITokenRepository;

public class TokenService implements ITokenRepository {

    @Override
    public Token findByToken(String token) {
        
        String getTokenQuery = "select ID_TOKEN, TEXT_TOKEN, CREATE_DATE_TOKEN, EXPIRES_DATE_TOKEN, CONFIRMED_DATE_TOKEN, ID_TOKEN_USER FROM TB_TOKEN " + 
                                "WHERE TEXT_TOKEN=?";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(getTokenQuery);

            ps.setString(1, token);

            rs = ps.executeQuery();

            while(rs.next()){
                return new Token(rs.getInt(1), rs.getString(2), rs.getTimestamp(3), rs.getTimestamp(4), rs.getTimestamp(5), (UUID)rs.getObject(6));
            }

        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        }finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
        
        return null;
    }

    @Override
    public Token saveToken(Token token) {
        
        String saveTokenQuery = "insert into TB_TOKEN (TEXT_TOKEN, CREATE_DATE_TOKEN, EXPIRES_DATE_TOKEN, ID_TOKEN_USER) " + 
                                "values (?,?,?,?)";
                                
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(saveTokenQuery, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, token.getToken());
            ps.setTimestamp(2, token.getCreateDate());
            ps.setTimestamp(3, token.getExpiresDate());
            ps.setObject(4, token.getUserId());

            int affectedRows = ps.executeUpdate();

            if(affectedRows > 0){
                rs = ps.getGeneratedKeys();
                
                if(rs.next()){
                    return new Token(rs.getInt(1), rs.getString(2), rs.getTimestamp(3), rs.getTimestamp(4), rs.getTimestamp(5), (UUID)rs.getObject(6));
                }
            }

        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        }finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
        
        return null;
    }

    @Override
    public Token updateToken(Token token)
    {
        String updateTokenQuery = "update TB_TOKEN set CONFIRMED_DATE_TOKEN=?" +
                                  "where ID_TOKEN=?";

        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(updateTokenQuery, Statement.RETURN_GENERATED_KEYS);
            
            ps.setTimestamp(1, token.getConfirmationDate());
            ps.setInt(2, token.getId());

            int affectedRows = ps.executeUpdate();

            if(affectedRows > 0){
                rs = ps.getGeneratedKeys();
                
                if(rs.next()){
                    return new Token(rs.getInt(1), rs.getString(2), rs.getTimestamp(3), rs.getTimestamp(4), rs.getTimestamp(5), (UUID)rs.getObject(6));
                }
            }
            
        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        }finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
        
        return null;   
    }

    public void deleteToken(int id)
    {
        String updateTokenQuery = "delete from TB_TOKEN where ID_TOKEN=?";

        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(updateTokenQuery, Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, id);
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        }finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
    }
}
