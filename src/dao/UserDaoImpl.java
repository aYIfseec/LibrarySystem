package dao;

import java.util.*;
import java.sql.*;

import modle.*;

public class UserDaoImpl{
	
    private static UserDaoImpl userDaoImpl;
    static {
        userDaoImpl = new UserDaoImpl();
    }
    
    private UserDaoImpl() {
        
    }
    
    public static UserDaoImpl getInstance() {
        return userDaoImpl;
    }
    
    
  //增加用户
    public boolean insertUser(User user) {
        String sql="insert into User(id,name,password,point)values(?,?,?,?)";
        Connection conn = BaseDaoImpl.getConn();
        PreparedStatement psts=null;
        try {
            psts = conn.prepareStatement(sql);
            psts.setObject(1, user.getId());
            psts.setObject(2, user.getName());
            psts.setObject(3, user.getPassword());
            psts.setObject(4, user.getPoint());
            int res = psts.executeUpdate();//执行 ，返回值为int
            if(res>0) return true;
        } catch (SQLException e) {
            //System.out.println("3");
            //e.printStackTrace();
        } finally {
            try {
                conn.close();//关闭连接
                psts.close();//关闭预编译
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public User queryUser(int id, String password) {
        String sql = "select * from User where id = ?";
        if (password != null) sql += " and password = ?";
        Connection conn = BaseDaoImpl.getConn();
        //创建一个结果集
        ResultSet rs = null;
        User user = null;
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            psts.setObject(1, id);
            if (password != null) psts.setObject(2, password);
            //执行
            rs=psts.executeQuery();
            while(rs.next()){
                int return_id = rs.getInt("id");
                String name = rs.getString("name");
                String return_password = rs.getString("password");
                int point = rs.getInt("point");
                user = new User(return_id, name, return_password, point);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                conn.close();
                psts.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
    
 // 查看所有用户
    public List<User> queryAllUser() {
        String sql = "select * from User";
        Connection conn = BaseDaoImpl.getConn();
        //创建一个结果集
        ResultSet rs = null;
        List<User> list = new ArrayList<User>();
        PreparedStatement psts = null;
        try {
            psts=conn.prepareStatement(sql);
            //执行
            rs=psts.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                String password=rs.getString("password");
                int point=rs.getInt("point");
                User user=new User(id,name,password,point);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
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
    
    //删除用户
    public boolean delUser(int id) {
        String sql="delete from User where id="+id;
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
    
    //修改用户名
    public boolean updUserName(int id, String name) {
        String sql = "update User set name=? where id="+id;
        Connection conn = BaseDaoImpl.getConn();
        PreparedStatement psts=null;
        try {
            psts = conn.prepareStatement(sql);
            psts.setObject(1, name);
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
    
    //修改用户密码
    public boolean updUserPassword(int id, String password,String str) {
        String sql = "update "+str+" set password=? where id="+id;
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
}