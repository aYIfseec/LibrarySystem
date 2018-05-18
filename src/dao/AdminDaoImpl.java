package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import modle.*;

public class AdminDaoImpl{
	//增加管理员
	public boolean insertAdm(Admin adm) {
		String sql = "insert into Administrator(id,password)values(?,?)";
		Connection conn = BaseDaoImpl.getConn();
		PreparedStatement psts = null;
		try {
			psts = conn.prepareStatement(sql);
			psts.setObject(1, adm.getId());
			psts.setObject(2, adm.getPassword());
			int res = psts.executeUpdate();// 执行 ，返回值为int
			if(res > 0) return true;
		} catch (SQLException e) {
			// System.out.println("3");
			//e.printStackTrace();
		} finally {
			try {
				psts.close();// 关闭预编译
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean deleteAdm(int id) {
		String sql="delete from Administrator where id="+id;
		Connection conn = BaseDaoImpl.getConn();
		PreparedStatement psts = null;
		try {
			psts = conn.prepareStatement(sql);
			int res = psts.executeUpdate();
			if(res > 0) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//管理员集,,delete
	public List<Admin> queryAllAdm() {
		String sql = "select * from Administrator";
		Connection conn = BaseDaoImpl.getConn();
		// 创建一个结果’集‘
		ResultSet rs = null;
		List<Admin> list = new ArrayList<Admin>();
		PreparedStatement psts = null;
		try {
			psts = conn.prepareStatement(sql);
			// 执行
			rs = psts.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String password = rs.getString("password");
				Admin adm = new Admin(id, password);
				list.add(adm);
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
		return list;
	}

	public boolean updateAdm(int id, String password) {
		String sql = "update Administrator set password=? where id="+id;
		Connection conn = BaseDaoImpl.getConn();
		PreparedStatement psts=null;
		try {
			psts = conn.prepareStatement(sql);
			psts.setObject(1, password);
			int res = psts.executeUpdate();
			if(res > 0) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				conn.close();//关闭连接
				psts.close();//关闭预编译
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
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
