package com.tcc.easyjobgo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tcc.easyjobgo.config.database.DBConfig;
import com.tcc.easyjobgo.exception.DBException;
import com.tcc.easyjobgo.model.Role;
import com.tcc.easyjobgo.repository.IRoleRepository;

public class RoleService implements IRoleRepository{

    
    @Override
    public Role findRoleById(int id) {
        String query = "select ID_ROLE, DESC_ROLE FROM TB_ROLE WHERE ID_ROLE=?";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        cnn = DBConfig.startConnection();
        ps = cnn.prepareStatement(query);

        ps.setInt(1, id);

        rs = ps.executeQuery();

        while(rs.next()){
            return new Role(rs.getInt(1), rs.getString(2));
        }

        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        } finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }

        return null;
    }
}
