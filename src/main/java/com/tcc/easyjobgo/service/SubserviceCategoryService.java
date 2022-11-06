package com.tcc.easyjobgo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tcc.easyjobgo.config.database.DBConfig;
import com.tcc.easyjobgo.exception.DBException;
import com.tcc.easyjobgo.model.SubserviceCategory;
import com.tcc.easyjobgo.repository.ISubserviceCategoryRepository;

public class SubserviceCategoryService implements ISubserviceCategoryRepository {
    
    @Override
    public List<SubserviceCategory> findAll() {
        String query = "select ID_SUBSERVICE_CAT,DESC_SUBSERVICE_CAT,ID_SUBSERVICE_SERVICE_CAT "+
                       "from TB_SUBSERVICE_CAT";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<SubserviceCategory> categories = new ArrayList<SubserviceCategory>();

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);

            rs = ps.executeQuery();

            while(rs.next()){
                categories.add(new SubserviceCategory(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }

            return categories;

        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        } finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
    
    }
    @Override
    public SubserviceCategory findById(int id) {
        String query = "select ID_SUBSERVICE_CAT,DESC_SUBSERVICE_CAT,ID_SUBSERVICE_SERVICE_CAT "+
                       "from TB_SUBSERVICE_CAT WHERE ID_SUBSERVICE_CAT=?";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        SubserviceCategory category = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);

            ps.setInt(1, id);

            rs = ps.executeQuery();

            while(rs.next()){
                category = new SubserviceCategory(rs.getInt(1), rs.getString(2), rs.getInt(3));
            }

            return category;

        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        } finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
    }
    @Override
    public List<SubserviceCategory> findByDesc(String desc) {
        String query = "select ID_SUBSERVICE_CAT,DESC_SUBSERVICE_CAT, ID_SUBSERVICE_SERVICE_CAT "+
                       "from TB_SUBSERVICE_CAT where DESC_SUBSERVICE_CAT like '%"+desc+"%'";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<SubserviceCategory> categories = new ArrayList<SubserviceCategory>();

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);

            rs = ps.executeQuery();

            while(rs.next()){
                categories.add(new SubserviceCategory(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }

            return categories;

        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        } finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
    }

    @Override
    public SubserviceCategory saveSubserviceCategory(SubserviceCategory sc) {
        String query = "insert into TB_SUBSERVICE_CAT (DESC_SUBSERVICE_CAT, ID_SUBSERVICE_SERVICE_CAT) "+
                       "values (?,?)";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, sc.getDescription());
            ps.setInt(2, sc.getServiceCategoryId());

            int affectedRows = ps.executeUpdate();

            if(affectedRows > 0){
                rs = ps.getGeneratedKeys();
                
                if(rs.next()){
                    return new SubserviceCategory(rs.getInt(1), rs.getString(2), rs.getInt(3));
                }
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

    @Override
    public SubserviceCategory updateSubserviceCategory(SubserviceCategory sc) {
        String query = "update TB_SUBSERVICE_CAT set DESC_SUBSERVICE_CAT=? " +
                       "where ID_SUBSERVICE_CAT=?";

        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, sc.getDescription());
            ps.setObject(2, sc.getId());

            int affectedRows = ps.executeUpdate();

            if(affectedRows > 0){
                rs = ps.getGeneratedKeys();
                
                if(rs.next()){
                    return new SubserviceCategory(rs.getInt(1), rs.getString(2), rs.getInt(3));
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
    public String deleteSubserviceCategory(int id) {
        String query = "delete from TB_SUBSERVICE_CAT where ID_SUBSERVICE_CAT=?";

        Connection cnn = null;
        PreparedStatement ps = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);

            ps.setObject(1, id);

            int affectedRows = ps.executeUpdate();

            if(affectedRows > 0) return "SERVICE CATEGORY SUCCESSFULLY DELETED FROM DATABASE.";
            
        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        }finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
        }
        
        return null;
    }

}
