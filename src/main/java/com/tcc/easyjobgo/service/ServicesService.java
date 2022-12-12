package com.tcc.easyjobgo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tcc.easyjobgo.config.database.DBConfig;
import com.tcc.easyjobgo.exception.DBException;
import com.tcc.easyjobgo.model.Services;
import com.tcc.easyjobgo.repository.IServicesRepository;

public class ServicesService implements IServicesRepository{

    @Override
    public List<Services> findAll() {
        return null;
    }

    @Override
    public Integer count(UUID serviceWorker) {
        String query = "SELECT count(id_service_worker) FROM tb_services ts2 WHERE id_service_worker = ? ";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Integer count = 0;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);

            ps.setObject(1, serviceWorker);

            rs = ps.executeQuery();

            while(rs.next()){
                count = rs.getInt(1);
            }

            return count;

        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        } finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
    }

    @Override
    public List<Services> findAllByClient(UUID serviceClient) {
        String query = "SELECT ID_SERVICE, INIT_HOUR_SERVICE,FINAL_HOUR_SERVICE,DESC_SERVICE,VALUE_SERVICE,CREATE_DATE_SERVICE," +
                               "EXPIRES_DATE_SERVICE,CONFIRMATION_CLIENT_SERVICE,CONFIRMATION_WORKER_SERVICE, START_DATE_SERVICE, END_DATE_SERVICE," +
                               "END_TOKEN_SERVICE,END_CONF_CLIENT_SERVICE,END_CONF_WORKER_SERVICE,ID_DAY_SERVICE_WORKER,ID_SERVICE_CLIENT,ID_SERVICE_WORKER" +
                        " FROM tb_services ts2 WHERE id_service_client = ? ";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Services> service = new ArrayList<Services>();

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);

            ps.setObject(1, serviceClient);

            rs = ps.executeQuery();

            while(rs.next()){
                service.add(new Services((UUID)rs.getObject(1), rs.getTimestamp(1), rs.getTimestamp(1), rs.getString(1), rs.getObject(1),
                                         rs.getTimestamp(1), rs.getTimestamp(1), rs.getBoolean(1), rs.getBoolean(1),
                                         rs.getTimestamp(1), rs.getTimestamp(1), rs.getString(1), rs.getBoolean(1),
                                         rs.getBoolean(1), rs.getInt(1), (UUID)rs.getObject(1), (UUID)rs.getObject(1)));
            }

            return service;

        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        } finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
    }

    @Override
    public List<Services> findAllByWorker(UUID serviceWorker) {
        String query = "SELECT ID_SERVICE, INIT_HOUR_SERVICE,FINAL_HOUR_SERVICE,DESC_SERVICE,VALUE_SERVICE,CREATE_DATE_SERVICE," +
                        "EXPIRES_DATE_SERVICE,CONFIRMATION_CLIENT_SERVICE,CONFIRMATION_WORKER_SERVICE, START_DATE_SERVICE, END_DATE_SERVICE," +
                        "END_TOKEN_SERVICE,END_CONF_CLIENT_SERVICE,END_CONF_WORKER_SERVICE,ID_DAY_SERVICE_WORKER,ID_SERVICE_CLIENT,ID_SERVICE_WORKER" +
                        " FROM tb_services ts2 WHERE ID_SERVICE_WORKER = ? ";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Services> service = new ArrayList<Services>();

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);

            ps.setObject(1, serviceWorker);

            rs = ps.executeQuery();

            while(rs.next()){
                service.add(new Services((UUID)rs.getObject(1), rs.getTimestamp(1), rs.getTimestamp(1), rs.getString(1), rs.getObject(1),
                                         rs.getTimestamp(1), rs.getTimestamp(1), rs.getBoolean(1), rs.getBoolean(1),
                                         rs.getTimestamp(1), rs.getTimestamp(1), rs.getString(1), rs.getBoolean(1),
                                         rs.getBoolean(1), rs.getInt(1), (UUID)rs.getObject(1), (UUID)rs.getObject(1)));
            }

            return service;

        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        } finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
    }

    @Override
    public Services findById(UUID id) {
        return null;
    }

    @Override
    public Services findByToken(String token) {
        String query = "select ID_SERVICE,INIT_HOUR_SERVICE,FINAL_HOUR_SERVICE,DESC_SERVICE,VALUE_SERVICE,CREATE_DATE_SERVICE," +
                       "EXPIRES_DATE_SERVICE,CONFIRMATION_CLIENT_SERVICE,CONFIRMATION_WORKER_SERVICE,START_DATE_SERVICE, END_DATE_SERVICE," +
                       "END_TOKEN_SERVICE,END_CONF_CLIENT_SERVICE,END_CONF_WORKER_SERVICE,ID_DAY_SERVICE_WORKER,ID_SERVICE_CLIENT,ID_SERVICE_WORKER"+
                       "from TB_SERVICES WHERE END_TOKEN_SERVICE=?";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Services service = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query);

            ps.setObject(1, token);

            rs = ps.executeQuery();

            while(rs.next()){
                service = new Services((UUID)rs.getObject(1), rs.getTimestamp(1), rs.getTimestamp(1), rs.getString(1), rs.getObject(1),
                                        rs.getTimestamp(1), rs.getTimestamp(1), rs.getBoolean(1), rs.getBoolean(1),
                                        rs.getTimestamp(1), rs.getTimestamp(1), rs.getString(1), rs.getBoolean(1),
                                        rs.getBoolean(1), rs.getInt(1), (UUID)rs.getObject(1), (UUID)rs.getObject(1));
            }

            return service;

        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        } finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
    }

    @Override
    public Services update(Services service) {
        return null;
    }

    @Override
    public Services save(Services service) {
        String query = "insert into TB_SERVICES (INIT_HOUR_SERVICE,FINAL_HOUR_SERVICE,DESC_SERVICE,VALUE_SERVICE,CREATE_DATE_SERVICE,EXPIRES_DATE_SERVICE," +
                       "ID_SERVICE_CLIENT,ID_SERVICE_WORKER) " + 
                       "values (?,?,?,?,?,?,?,?)";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            ps.setTimestamp(1, service.getInitalHour());
            ps.setTimestamp(2, service.getFinalHour());
            ps.setString(3, service.getDescription());
            ps.setObject(4, service.getValue());
            ps.setTimestamp(5, service.getCreateDate());
            ps.setTimestamp(6, service.getExpiresDate());
            ps.setObject(7, service.getServiceClient());
            ps.setObject(8, service.getServiceWorker());

            int affectedRows = ps.executeUpdate();

            if(affectedRows > 0){
                rs = ps.getGeneratedKeys();
                
                if(rs.next()){
                    return new Services((UUID)rs.getObject(1), rs.getTimestamp(1), rs.getTimestamp(1), rs.getString(1), rs.getObject(1),
                                       rs.getTimestamp(1), rs.getTimestamp(1), rs.getBoolean(1), rs.getBoolean(1),
                                       rs.getTimestamp(1), rs.getTimestamp(1), rs.getString(1), rs.getBoolean(1),
                                       rs.getBoolean(1), rs.getInt(1), (UUID)rs.getObject(1), (UUID)rs.getObject(1));
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
    public String delete(UUID id) {
        return null;
    }

    @Override
    public List<Services> findAllByUser() {
        return null;
    }

    @Override
    public Services updateWorkerStatus(Services service) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Services updateStartDate(Services service) {
        // TODO Auto-generated method stub
        return null;
    }
}
