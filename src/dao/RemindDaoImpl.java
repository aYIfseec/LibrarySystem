package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modle.Book;

public class RemindDaoImpl {

    private static RemindDaoImpl remindDao;
    static {
        remindDao = new RemindDaoImpl();
    }
    private RemindDaoImpl(){}
    public static RemindDaoImpl getInstance() {
        return remindDao;
    }
    /**
     * 添加书籍可借提醒
     * @param uId
     * @param bid
     * @return
     */
    public boolean insertRemind(int uId, Integer bid) {
        String sql = "insert into Remind(uid, bid)values(?,?)";
        Connection conn = BaseDaoImpl.getConn();
        PreparedStatement psts=null;
        try {
            psts = conn.prepareStatement(sql);
            psts.setObject(1, uId);
            psts.setObject(2, bid);
            int res = psts.executeUpdate();//执行 ，返回值为int
            if(res>0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
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
    
    /**
     * 书籍可借提醒
     * @param uid
     * @return
     */
    public List<Book> getRemindByUid(int uid) {
        List<Book> books = new ArrayList<Book>();
        String sql = "select Remind.id as id, name from Remind, Book " +
        		"where Remind.bid = Book.id and isRead = 0 and Book.count > Book.hasLended and uid="+uid;
        Connection conn = BaseDaoImpl.getConn();
        ResultSet rs = null;
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            rs = psts.executeQuery();// 执行
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                psts.close();
                rs.close();
            } catch (final SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    
    /**
     * 批量更新为已读提醒
     * @param str
     */
    public void updateRemindBatch(String str) {
        String sql = "update Remind set isRead = 1 where id in (" + str + ")";
        System.out.println(sql);
        Connection conn = BaseDaoImpl.getConn();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            psts.executeUpdate();// 执行
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                psts.close();
            } catch (final SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
