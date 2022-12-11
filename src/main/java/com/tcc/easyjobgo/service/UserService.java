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
        String query = " SELECT id_user, profile_img_user, cpf_user, first_name_user, last_name_user, email_user, password_user, " +
                       " numberddd_user, number_user, birthdate_user, postal_code_user, address_district_user, " +
                       " address_pubplace_user, address_comp_user, city_user, fu_user, regdate_user, " +
                       " rating_user, provservice_user, service_desc_user, service_value_user, id_user_service_cat, " +
                       " id_user_subservice_cat, id_user_status, id_user_role, desc_subservice_cat FROM tb_user tu " +
                       " LEFT JOIN tb_subservice_cat tsc2 on id_subservice_cat = id_user_subservice_cat";
        
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
                                   rs.getObject(23), rs.getInt(24), rs.getInt(25), rs.getString(26)));
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
    public List<User> findAllWorkers(String username) {
        String query = " SELECT id_user, profile_img_user, cpf_user, first_name_user, last_name_user, email_user, password_user, " +
                       " numberddd_user, number_user, birthdate_user, postal_code_user, address_district_user, " +
                       " address_pubplace_user, address_comp_user, city_user, fu_user, regdate_user, " +
                       " rating_user, provservice_user, service_desc_user, service_value_user, id_user_service_cat, " +
                       " id_user_subservice_cat, id_user_status, id_user_role, desc_subservice_cat FROM tb_user tu " +
                       " LEFT JOIN tb_subservice_cat tsc2 on id_subservice_cat = id_user_subservice_cat " +
                       " WHERE id_user_role = '2' AND id_user_status = '1' AND email_user <> ? AND provservice_user = true";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<User> users = new ArrayList<User>();
        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);

            ps.setString(1, username);

            rs = ps.executeQuery();

            while(rs.next()){
                users.add(new User((UUID)rs.getObject(1),rs.getString(2), rs.getString(3), rs.getString(4), 
                                   rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                                   rs.getDate(10), rs.getString(11), rs.getString(12), rs.getString(13),
                                   rs.getObject(14), rs.getString(15), rs.getString(16), rs.getDate(17), rs.getObject(18),
                                   rs.getBoolean(19),rs.getString(20), rs.getObject(21), rs.getObject(22), 
                                   rs.getObject(23), rs.getInt(24), rs.getInt(25), rs.getString(26)));
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
    public List<User> findByDesc(String username, String desc) {
        String query = " SELECT id_user, profile_img_user, cpf_user, first_name_user, last_name_user, email_user, password_user, " +
                        "numberddd_user, number_user, birthdate_user, postal_code_user, address_district_user, " +
                        " address_pubplace_user, address_comp_user, city_user, fu_user, regdate_user, " +
                        "rating_user, provservice_user, service_desc_user, service_value_user, id_user_service_cat, " +
                        "id_user_subservice_cat, id_user_status, id_user_role, desc_subservice_cat FROM tb_user tu " +
                        "INNER JOIN tb_service_cat tsc ON tsc.id_service_cat = id_user_service_cat" +
                        "INNER JOIN  tb_subservice_cat tsc2 ON id_subservice_service_cat = tsc.id_service_cat "+
                        "WHERE id_user_role = '2' AND id_user_status = '1' AND  email_user <> ? AND provservice_user = true AND"+
                        "(desc_service_cat ILIKE '%"+desc+"%' OR desc_subservice_cat ILIKE '%"+desc+"%' OR service_desc_user ILIKE '%"+desc+"%')";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<User> users = new ArrayList<User>();
        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);

            ps.setString(1, username);

            rs = ps.executeQuery();

            while(rs.next()){
                users.add(new User((UUID)rs.getObject(1),rs.getString(2), rs.getString(3), rs.getString(4), 
                                   rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                                   rs.getDate(10), rs.getString(11), rs.getString(12), rs.getString(13),
                                   rs.getObject(14), rs.getString(15), rs.getString(16), rs.getDate(17), rs.getObject(18),
                                   rs.getBoolean(19),rs.getString(20), rs.getObject(21), rs.getObject(22), 
                                   rs.getObject(23), rs.getInt(24), rs.getInt(25), rs.getString(26)));
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
        String query = " SELECT id_user, profile_img_user, cpf_user, first_name_user, last_name_user, email_user, password_user, " +
                        " numberddd_user, number_user, birthdate_user, postal_code_user, address_district_user, " +
                        " address_pubplace_user, address_comp_user, city_user, fu_user, regdate_user, " +
                        " rating_user, provservice_user, service_desc_user, service_value_user, id_user_service_cat, " +
                        " id_user_subservice_cat, id_user_status, id_user_role, desc_subservice_cat FROM tb_user tu " +
                        " LEFT JOIN tb_subservice_cat tsc2 on id_subservice_cat = id_user_subservice_cat " +
                        " WHERE email_user = ?";
        
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
                                rs.getObject(23), rs.getInt(24), rs.getInt(25), rs.getString(26));
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
        String query = " SELECT id_user, profile_img_user, cpf_user, first_name_user, last_name_user, email_user, password_user, " +
                        " numberddd_user, number_user, birthdate_user, postal_code_user, address_district_user, " +
                        " address_pubplace_user, address_comp_user, city_user, fu_user, regdate_user, " +
                        " rating_user, provservice_user, service_desc_user, service_value_user, id_user_service_cat, " +
                        " id_user_subservice_cat, id_user_status, id_user_role, desc_subservice_cat FROM tb_user tu " +
                        " LEFT JOIN tb_subservice_cat tsc2 on id_subservice_cat = id_user_subservice_cat " +
                        " WHERE cpf_user = ?";
        
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
                                rs.getObject(23), rs.getInt(24), rs.getInt(25), rs.getString(26));
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
        String query = " SELECT id_user, profile_img_user, cpf_user, first_name_user, last_name_user, email_user, password_user, " +
                        " numberddd_user, number_user, birthdate_user, postal_code_user, address_district_user, " +
                        " address_pubplace_user, address_comp_user, city_user, fu_user, regdate_user, " +
                        " rating_user, provservice_user, service_desc_user, service_value_user, id_user_service_cat, " +
                        " id_user_subservice_cat, id_user_status, id_user_role, desc_subservice_cat FROM tb_user tu " +
                        " LEFT JOIN tb_subservice_cat tsc2 on id_subservice_cat = id_user_subservice_cat " +
                        " WHERE id_user = ?";
        
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
                                rs.getObject(23), rs.getInt(24), rs.getInt(25), rs.getString(26));
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
        
        String query = "insert into TB_USER (CPF_USER,FIRST_NAME_USER,LAST_NAME_USER,EMAIL_USER,PASSWORD_USER,NUMBERDDD_USER,NUMBER_USER,BIRTHDATE_USER," +
                       "                     POSTAL_CODE_USER,ADDRESS_DISTRICT_USER,ADDRESS_PUBPLACE_USER,ADDRESS_COMP_USER,CITY_USER,FU_USER,PROVSERVICE_USER,SERVICE_DESC_USER," +
                       "                     SERVICE_VALUE_USER,ID_USER_SERVICE_CAT,ID_USER_SUBSERVICE_CAT,ID_USER_STATUS,ID_USER_ROLE) " + 
                       "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            String passwordEncode = new BCryptPasswordEncoder().encode(user.getPassword());

            ps.setString(1, user.getCpf());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getUsername());
            ps.setString(5, passwordEncode);
            ps.setString(6, user.getNumberDDD());
            ps.setString(7, user.getNumber());
            ps.setDate(8, user.getBirthdate());
            ps.setString(9, user.getCep());
            ps.setString(10, user.getAddressDistrict());
            ps.setString(11, user.getAddressPubPlace());
            ps.setObject(12, user.getAddressComp());
            ps.setString(13, user.getCity());
            ps.setString(14, user.getFedUnit());
            ps.setBoolean(15, user.isProvideService());
            ps.setString(16, user.getServiceDesc());
            ps.setObject(17, user.getServiceValue());
            ps.setObject(18, user.getServiceCategoryId());
            ps.setObject(19, user.getSubserviceCategoryId());
            ps.setInt(20, user.getStatusId());
            ps.setInt(21, user.getRoleId());

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
        String query = "update TB_USER set NUMBERDDD_USER=?,NUMBER_USER=?," +
                       "                   POSTAL_CODE_USER=?,ADDRESS_DISTRICT_USER=?,ADDRESS_PUBPLACE_USER=?,ADDRESS_COMP_USER=?,CITY_USER=?,FU_USER=?," +
                       "                   PROVSERVICE_USER=?,SERVICE_DESC_USER=?,SERVICE_VALUE_USER=?,ID_USER_SERVICE_CAT=?,ID_USER_SUBSERVICE_CAT=? " +
                       "where ID_USER=?";

        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, user.getNumberDDD());
            ps.setString(2, user.getNumber());
            ps.setString(3, user.getCep());
            ps.setString(4, user.getAddressDistrict());
            ps.setString(5, user.getAddressPubPlace());
            ps.setObject(6, user.getAddressComp());
            ps.setString(7, user.getCity());
            ps.setString(8, user.getFedUnit());
            ps.setBoolean(9, user.isProvideService());
            ps.setString(10, user.getServiceDesc());
            ps.setObject(11, user.getServiceValue());
            ps.setObject(12, user.getServiceCategoryId());
            ps.setObject(13, user.getSubserviceCategoryId());

            ps.setObject(14, user.getId());

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

    @Override
    public void updateUserProfileImage(String newProfile, UUID id) {
        String query = "update TB_USER set profile_img_user=? " +
                       "where ID_USER=?";

        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, newProfile);
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
}
