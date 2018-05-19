package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modle.Record;

public class RecordDaoImpl {
    private static RecordDaoImpl recordDao;
    static {
        recordDao = new RecordDaoImpl();
    }
    private RecordDaoImpl() {}
    
    public static RecordDaoImpl getInstance() {
        return recordDao;
    }
    
    //借还信息表中加入信息
    public boolean insertRecord(Record record, Connection conn) {
        boolean isRelease = false;
        String sql="insert into Record(uid,bid,lendTime,returnTime)values(?,?,?,?)";
        if(conn == null) {
            conn = BaseDaoImpl.getConn();
            isRelease = true;
        }
        PreparedStatement psts=null;
        try {
            psts = conn.prepareStatement(sql);
            psts.setObject(1, record.getUid());
            psts.setObject(2, record.getBid());
            psts.setObject(3, record.getLendTime());
            psts.setObject(4, record.getReturnTime());
            int res = psts.executeUpdate();//执行 ，返回值为int
            if(res>0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(isRelease && conn != null) conn.close();//关闭连接
                if(psts != null) psts.close();//关闭预编译
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    
    //修改还借记录表，主要是还书时调用，修改returnTime
    public boolean updataRecord(int uid, int bid, String returnTime, Connection conn) {
        boolean isRelease = false;
        String sql = "update Record set returnTime=? where uid=? and bid="+bid;
        if(conn == null) {
            conn = BaseDaoImpl.getConn();
            isRelease = true;
        }
        PreparedStatement psts=null;
        try {
            psts = conn.prepareStatement(sql);
            psts.setObject(1, returnTime);
            psts.setObject(2, uid);
            int res = psts.executeUpdate();
            if(res > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(isRelease && conn != null) conn.close();//关闭连接
                if(psts != null) psts.close();//关闭预编译
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Object[][] getRecordsByUid(int uid) {
        String sql = "select Record.* , name  from Record, Book " +
        		"where Record.bid=Book.id and uid="+uid;
        Connection conn = BaseDaoImpl.getConn();
        ResultSet rs = null;
        List<Record> list = new ArrayList<Record>();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            rs = psts.executeQuery();// 执行
            while (rs.next()) {
                String name = rs.getString("name");
                String lendTime = rs.getString("lendTime");
                String returnTime = rs.getString("returnTime");
                Record rd = new Record(lendTime,returnTime);
                rd.setName(name);
                list.add(rd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                psts.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Object[][] obj = new Object[list.size()][4];
        int i = 0;
        for (Record re: list) {
            obj[i][0] = i + 1;
            obj[i][1] = re.getName();
            obj[i][2] = re.getLendTime();
            obj[i][3] = re.getReturnTime();
            i++;
        }
        return obj;
    }
    
}
