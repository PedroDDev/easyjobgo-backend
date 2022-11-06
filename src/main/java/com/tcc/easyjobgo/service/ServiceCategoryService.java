package com.tcc.easyjobgo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tcc.easyjobgo.config.database.DBConfig;
import com.tcc.easyjobgo.exception.DBException;
import com.tcc.easyjobgo.model.ServiceCategory;
import com.tcc.easyjobgo.repository.IServiceCategoryRepository;

public class ServiceCategoryService implements IServiceCategoryRepository{

    @Override
    public List<ServiceCategory> findAll() {
        String query = "select ID_SERVICE_CAT,DESC_SERVICE_CAT "+
                       "from TB_SERVICE_CAT";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<ServiceCategory> categories = new ArrayList<ServiceCategory>();

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);

            rs = ps.executeQuery();

            while(rs.next()){
                categories.add(new ServiceCategory(rs.getInt(1), rs.getString(2)));
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
    public ServiceCategory findById(int id) {
        String query = "select ID_SERVICE_CAT,DESC_SERVICE_CAT "+
                       "from TB_SERVICE_CAT WHERE ID_SERVICE_CAT=?";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ServiceCategory category = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);

            ps.setInt(1, id);

            rs = ps.executeQuery();

            while(rs.next()){
                category = new ServiceCategory(rs.getInt(1), rs.getString(2));
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
    public List<ServiceCategory> findByDesc(String desc) {
        String query = "select ID_SERVICE_CAT,DESC_SERVICE_CAT "+
                       "from TB_SERVICE_CAT where DESC_SERVICE_CAT like '%"+desc+"%'";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<ServiceCategory> categories = new ArrayList<ServiceCategory>();

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);

            // ps.setString(1, desc);

            rs = ps.executeQuery();

            while(rs.next()){
                categories.add(new ServiceCategory(rs.getInt(1), rs.getString(2)));
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
    public ServiceCategory saveServiceCategory(ServiceCategory sc) {
        String query = "insert into TB_SERVICE_CAT (DESC_SERVICE_CAT) "+
                       "values (?)";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, sc.getDescription());

            int affectedRows = ps.executeUpdate();

            if(affectedRows > 0){
                rs = ps.getGeneratedKeys();
                
                if(rs.next()){
                    return new ServiceCategory(rs.getInt(1), rs.getString(2));
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
    public ServiceCategory updateServiceCategory(ServiceCategory sc) {
        String query = "update TB_SERVICE_CAT set DESC_SERVICE_CAT=? " +
                       "where ID_SERVICE_CAT=?";

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
                    return new ServiceCategory(rs.getInt(1), rs.getString(2));
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
    public String deleteServiceCategory(int id) {
        String query = "delete from TB_SERVICE_CAT where ID_SERVICE_CAT=?";

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
