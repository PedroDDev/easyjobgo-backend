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
import com.tcc.easyjobgo.model.WorkerDay;
import com.tcc.easyjobgo.repository.IWorkerDayRepository;

public class WorkerDayService implements IWorkerDayRepository {

    @Override
    public List<WorkerDay> findAll() {
        String query = "select ID_WORKER_DAY,ID_WORKER,ID_DAY "+
                       "from TB_WORKER_DAY " + 
                       "order by ID_WORKER_DAY";

        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<WorkerDay> workersDays = new ArrayList<WorkerDay>();

        try {
        cnn = DBConfig.startConnection();
        ps = cnn.prepareStatement(query);
        rs = ps.executeQuery();

        while(rs.next()){
            workersDays.add(new WorkerDay(rs.getInt(1), (UUID)rs.getObject(2), rs.getInt(3)));
        }

        return workersDays;

        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        } finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
    }

    @Override
    public WorkerDay findById(Integer id) {
        String query = "select ID_WORKER_DAY,ID_WORKER,ID_DAY "+
                       "from TB_WORKER_DAY " +
                       "where ID_WORKER_DAY=? " +
                       "order by ID_WORKER_DAY";

        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        WorkerDay workerDay = null;

        try {
        cnn = DBConfig.startConnection();
        ps = cnn.prepareStatement(query);

        ps.setInt(1, id);

        rs = ps.executeQuery();

        while(rs.next()){
            workerDay = new WorkerDay(rs.getInt(1), (UUID)rs.getObject(2), rs.getInt(3));
        }

        return workerDay;

        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        } finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
    }

    @Override
    public List<WorkerDay> findByWorkerId(UUID id) {
        String query = "select ID_WORKER_DAY,ID_WORKER,ID_DAY "+
                       "from TB_WORKER_DAY " +
                       "where ID_WORKER=? " +
                       "order by ID_WORKER_DAY";

        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<WorkerDay> workerDays = new ArrayList<WorkerDay>();

        try {
        cnn = DBConfig.startConnection();
        ps = cnn.prepareStatement(query);

        ps.setObject(1, id);

        rs = ps.executeQuery();

        while(rs.next()){
            workerDays.add(new WorkerDay(rs.getInt(1), (UUID)rs.getObject(2), rs.getInt(3)));
        }

        return workerDays;

        } catch (Exception e) {
            throw new DBException(e.getMessage(), e.getCause());
        } finally{
            DBConfig.closeConnection(cnn);
            DBConfig.closePreparedStatment(ps);
            DBConfig.closeResultSet(rs);
        }
    }

    @Override
    public WorkerDay saveWorkerDay(WorkerDay user) {
        String query = "insert into TB_WORKER_DAY (ID_WORKER,ID_DAY) " + 
                       "values (?,?)";
        
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cnn = DBConfig.startConnection();
            ps = cnn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            ps.setObject(1, user.getWorkerId());
            ps.setInt(2, user.getDayId());

            int affectedRows = ps.executeUpdate();

            if(affectedRows > 0){
                rs = ps.getGeneratedKeys();
                
                if(rs.next()){
                    return new WorkerDay(rs.getInt(1), (UUID)rs.getObject(2), rs.getInt(3));
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
    public String deleteWorkerDay(Integer id) {
        return null;
    }
    
}
