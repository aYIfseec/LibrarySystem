package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modle.*;

public class AdminDaoImpl{
	
	private static AdminDaoImpl adminDaoImpl;
    static {
        adminDaoImpl = new AdminDaoImpl();
    }
    private AdminDaoImpl() {
        
    }
	
    public static AdminDaoImpl getInstance() {
        return adminDaoImpl;
    }
    
    
    public Admin queryAdmin(int id, String password) {
        String sql = "select * from Administrator where id = ? and password = ?";
        Connection conn = BaseDaoImpl.getConn();
        // 创建一个结果集
        ResultSet rs = null;
        Admin admin = null;
        
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql); // 对sql语句进行预处理
            // 简单防sql注入攻击，传入参数
            psts.setObject(1, id);
            psts.setObject(2, password);
            // 执行
            rs = psts.executeQuery();
            while (rs.next()) {
                int return_id = rs.getInt("id");
                String return_password = rs.getString("password");
                admin = new Admin(return_id, return_password);
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
        return admin;
    }
}
