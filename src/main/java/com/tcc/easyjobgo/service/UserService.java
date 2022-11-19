package com.tcc.easyjobgo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tcc.easyjobgo.config.database.DBConfig;
import com.tcc.easyjobgo.exception.DBException;
import com.tcc.easyjobgo.model.User;
import com.tcc.easyjobgo.repository.IUserRepository;

public class UserService implements IUserRepository{

    @Override
    public List<User> findAll() {
        String query = "select ID_USER,PROFILE_IMG_USER,CPF_USER,FIRST_NAME_USER,LAST_NAME_USER,EMAIL_USER,PASSWORD_USER,"+
                       "       NUMBERDDD_USER,NUMBER_USER,BIRTHDATE_USER,POSTAL_CODE_USER,ADDRESS_DISTRICT_USER,"+
                       "       ADDRESS_PUBPLACE_USER,ADDRESS_COMP_USER,CITY_USER,FU_USER,REGDATE_USER,RATING_USER,PROVSERVICE_USER,SERVICE_DESC_USER,"+
                       "       SERVICE_VALUE_USER,ID_USER_SERVICE_CAT,ID_USER_SUBSERVICE_CAT,ID_USER_STATUS,ID_USER_ROLE "+
                       "from TB_USER " + 
                       "order by FIRST_NAME_USER";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<User> users = new ArrayList<User>();
        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()){
                users.add(new User((UUID)rs.getObject(1),rs.getString(2), rs.getString(3), rs.getString(4), 
                                   rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                                   rs.getDate(10), rs.getString(11), rs.getString(12), rs.getString(13),
                                   rs.getObject(14), rs.getString(15), rs.getString(16), rs.getDate(17), rs.getObject(18),
                                   rs.getBoolean(19),rs.getString(20), rs.getObject(21), rs.getObject(22), 
                                   rs.getObject(23), rs.getInt(24), rs.getInt(25)));
            }

            return users;

        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        } finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }

    }
    @Override
    public User findByUsername(String username) {
        String query = "select ID_USER,PROFILE_IMG_USER,CPF_USER,FIRST_NAME_USER,LAST_NAME_USER,EMAIL_USER,PASSWORD_USER,"+
                       "       NUMBERDDD_USER,NUMBER_USER,BIRTHDATE_USER,POSTAL_CODE_USER,ADDRESS_DISTRICT_USER,"+
                       "       ADDRESS_PUBPLACE_USER,ADDRESS_COMP_USER,CITY_USER,FU_USER,REGDATE_USER,RATING_USER,PROVSERVICE_USER,SERVICE_DESC_USER,"+
                       "       SERVICE_VALUE_USER,ID_USER_SERVICE_CAT,ID_USER_SUBSERVICE_CAT,ID_USER_STATUS,ID_USER_ROLE "+
                       "from TB_USER " +
                       "WHERE EMAIL_USER=?";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        User user = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);

            ps.setString(1, username);

            rs = ps.executeQuery();

            while(rs.next()){
                user = new User((UUID)rs.getObject(1),rs.getString(2), rs.getString(3), rs.getString(4), 
                                rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                                rs.getDate(10), rs.getString(11), rs.getString(12), rs.getString(13),
                                rs.getObject(14), rs.getString(15), rs.getString(16), rs.getDate(17), rs.getObject(18),
                                rs.getBoolean(19),rs.getString(20), rs.getObject(21), rs.getObject(22), 
                                rs.getObject(23), rs.getInt(24), rs.getInt(25));
            }

            return user;

        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        } finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
    }
    @Override
    public User findByCpf(String cpf) {
        String query = "select ID_USER,PROFILE_IMG_USER,CPF_USER,FIRST_NAME_USER,LAST_NAME_USER,EMAIL_USER,PASSWORD_USER,"+
                       "       NUMBERDDD_USER,NUMBER_USER,BIRTHDATE_USER,POSTAL_CODE_USER,ADDRESS_DISTRICT_USER,"+
                       "       ADDRESS_PUBPLACE_USER,ADDRESS_COMP_USER,CITY_USER,FU_USER,REGDATE_USER,RATING_USER,PROVSERVICE_USER,SERVICE_DESC_USER,"+
                       "       SERVICE_VALUE_USER,ID_USER_SERVICE_CAT,ID_USER_SUBSERVICE_CAT,ID_USER_STATUS,ID_USER_ROLE "+
                       "from TB_USER " +
                       "WHERE CPF_USER=?";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        User user = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);

            ps.setString(1, cpf);

            rs = ps.executeQuery();

            while(rs.next()){
                user = new User((UUID)rs.getObject(1),rs.getString(2), rs.getString(3), rs.getString(4), 
                                rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                                rs.getDate(10), rs.getString(11), rs.getString(12), rs.getString(13),
                                rs.getObject(14), rs.getString(15), rs.getString(16), rs.getDate(17), rs.getObject(18),
                                rs.getBoolean(19),rs.getString(20), rs.getObject(21), rs.getObject(22), 
                                rs.getObject(23), rs.getInt(24), rs.getInt(25));
            }

            return user;

        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        } finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
    }
    @Override
    public User findById(UUID id) {
        String query = "select ID_USER,PROFILE_IMG_USER,CPF_USER,FIRST_NAME_USER,LAST_NAME_USER,EMAIL_USER,PASSWORD_USER,"+
                       "       NUMBERDDD_USER,NUMBER_USER,BIRTHDATE_USER,POSTAL_CODE_USER,ADDRESS_DISTRICT_USER,"+
                       "       ADDRESS_PUBPLACE_USER,ADDRESS_COMP_USER,CITY_USER,FU_USER,REGDATE_USER,RATING_USER,PROVSERVICE_USER,SERVICE_DESC_USER,"+
                       "       SERVICE_VALUE_USER,ID_USER_SERVICE_CAT,ID_USER_SUBSERVICE_CAT,ID_USER_STATUS,ID_USER_ROLE "+
                       "from TB_USER WHERE ID_USER=?";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        User user = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);

            ps.setObject(1, id);

            rs = ps.executeQuery();

            while(rs.next()){
                user = new User((UUID)rs.getObject(1),rs.getString(2), rs.getString(3), rs.getString(4), 
                                rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                                rs.getDate(10), rs.getString(11), rs.getString(12), rs.getString(13),
                                rs.getObject(14), rs.getString(15), rs.getString(16), rs.getDate(17), rs.getObject(18),
                                rs.getBoolean(19),rs.getString(20), rs.getObject(21), rs.getObject(22), 
                                rs.getObject(23), rs.getInt(24), rs.getInt(25));
            }

            return user;

        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        } finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
    }

    @Override
    public User saveUser(User user) {
        
        String query = "insert into TB_USER (PROFILE_IMG_USER,CPF_USER,FIRST_NAME_USER,LAST_NAME_USER,EMAIL_USER,PASSWORD_USER,NUMBERDDD_USER,NUMBER_USER,BIRTHDATE_USER," +
                       "                     POSTAL_CODE_USER,ADDRESS_DISTRICT_USER,ADDRESS_PUBPLACE_USER,ADDRESS_COMP_USER,CITY_USER,FU_USER,PROVSERVICE_USER,SERVICE_DESC_USER," +
                       "                     SERVICE_VALUE_USER,ID_USER_SERVICE_CAT,ID_USER_SUBSERVICE_CAT,ID_USER_STATUS,ID_USER_ROLE) " + 
                       "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            String passwordEncode = new BCryptPasswordEncoder().encode(user.getPassword());

            ps.setString(1, user.getProfileImg());
            ps.setString(2, user.getCpf());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getUsername());
            ps.setString(6, passwordEncode);
            ps.setString(7, user.getNumberDDD());
            ps.setString(8, user.getNumber());
            ps.setDate(9, user.getBirthdate());
            ps.setString(10, user.getCep());
            ps.setString(11, user.getAddressDistrict());
            ps.setString(12, user.getAddressPubPlace());
            ps.setObject(13, user.getAddressComp());
            ps.setString(14, user.getCity());
            ps.setString(15, user.getFedUnit());
            ps.setBoolean(16, user.isProvideService());
            ps.setString(17, user.getServiceDesc());
            ps.setObject(18, user.getServiceValue());
            ps.setObject(19, user.getServiceCategoryId());
            ps.setObject(20, user.getSubserviceCategoryId());
            ps.setInt(21, user.getStatusId());
            ps.setInt(22, user.getRoleId());

            int affectedRows = ps.executeUpdate();

            if(affectedRows > 0){
                rs = ps.getGeneratedKeys();
                
                if(rs.next()){
                    return new User((UUID)rs.getObject(1),rs.getString(2), rs.getString(3), rs.getString(4), 
                                    rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                                    rs.getDate(10), rs.getString(11), rs.getString(12), rs.getString(13),
                                    rs.getObject(14), rs.getString(15), rs.getString(16), rs.getDate(17), rs.getObject(18),
                                    rs.getBoolean(19),rs.getString(20), rs.getObject(21), rs.getObject(22), 
                                    rs.getObject(23), rs.getInt(24), rs.getInt(25));
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
    public User updateUser(User user) {
        String query = "update TB_USER set PROFILE_IMG_USER=?,PASSWORD_USER=?,NUMBERDDD_USER=?,NUMBER_USER=?," +
                       "                   POSTAL_CODE_USER=?,ADDRESS_DISTRICT_USER=?,ADDRESS_PUBPLACE_USER=?,ADDRESS_COMP_USER=?,CITY_USER=?,FU_USER=?," +
                       "                   PROVSERVICE_USER=?,SERVICE_DESC_USER=?,SERVICE_VALUE_USER=?,ID_USER_SERVICE_CAT=?,ID_USER_SUBSERVICE_CAT=? " +
                       "where ID_USER=?";

        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            String passwordEncode = new BCryptPasswordEncoder().encode(user.getPassword());
            
            ps.setString(1, user.getProfileImg());
            ps.setString(2, passwordEncode);
            ps.setString(3, user.getNumberDDD());
            ps.setString(4, user.getNumber());
            ps.setString(5, user.getCep());
            ps.setString(6, user.getAddressDistrict());
            ps.setString(7, user.getAddressPubPlace());
            ps.setObject(8, user.getAddressComp());
            ps.setString(9, user.getCity());
            ps.setString(10, user.getFedUnit());
            ps.setBoolean(11, user.isProvideService());
            ps.setString(12, user.getServiceDesc());
            ps.setObject(13, user.getServiceValue());
            ps.setObject(14, user.getServiceCategoryId());
            ps.setObject(15, user.getSubserviceCategoryId());

            ps.setObject(16, user.getId());

            int affectedRows = ps.executeUpdate();

            if(affectedRows > 0){
                rs = ps.getGeneratedKeys();
                
                if(rs.next()){
                    return new User((UUID)rs.getObject(1),rs.getString(2), rs.getString(3), rs.getString(4), 
                                    rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                                    rs.getDate(10), rs.getString(11), rs.getString(12), rs.getString(13),
                                    rs.getObject(14), rs.getString(15), rs.getString(16), rs.getDate(17), rs.getObject(18),
                                    rs.getBoolean(19),rs.getString(20), rs.getObject(21), rs.getObject(22), 
                                    rs.getObject(23), rs.getInt(24), rs.getInt(25));
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
    public void updateUserStatus(UUID id, Integer status) {
        String query = "update TB_USER set ID_USER_STATUS=? " +
                       "where ID_USER=?";

        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, status);
            ps.setObject(2, id);
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        }finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
    }
    
    @Override
    public String deleteUser(UUID id) {
        String query = "delete from TB_USER where ID_USER=?";

        Connection cnn = null;
        PreparedStatement ps = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);

            ps.setObject(1, id);

            int affectedRows = ps.executeUpdate();

            if(affectedRows > 0) return "USER SUCCESSFULLY DELETED FROM DATABASE.";
            
        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        }finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
        }
        
        return null;
    }
}
