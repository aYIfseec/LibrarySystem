package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modle.Book;

public class BookDaoImpl {
    
    private static BookDaoImpl bookDao;
    static {
        bookDao = new BookDaoImpl();
    }
    
    private BookDaoImpl() {}

    public static BookDaoImpl getInstance() {
        return bookDao;
    }
    
    
    //增加图书
    public boolean insertBook(Book book) {
        String sql = "insert into " +
        		"Book(id, name, count, type, author, discount, hasLended, address)" +
        		"values(?,?,?,?,?,?,?,?)";
        Connection conn = BaseDaoImpl.getConn();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            psts.setObject(1, book.getId());
            psts.setObject(2, book.getName());
            psts.setObject(3, book.getCount());
            psts.setObject(4, book.getType());
            psts.setObject(5, book.getAuthor());
            psts.setObject(6, book.getDiscount());
            psts.setObject(7, book.getHasLended());
            psts.setObject(8, book.getAddress());
            psts.executeUpdate();// 执行 ，返回值为int
        } catch (SQLException e) {
            // System.out.println("3");
            e.printStackTrace();
        } finally {
            try {
                psts.close();// 关闭预编译
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    // 所有书籍
    public List<Book> queryAllBooks() {
        String sql = "select * from Book";
        Connection conn = BaseDaoImpl.getConn();
        ResultSet rs = null;
        List<Book> list = new ArrayList<Book>();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            rs = psts.executeQuery();// 执行
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int count = rs.getInt("count");
                String type = rs.getString("type");
                String author = rs.getString("author");
                int discount = rs.getInt("discount");
                int hasLended = rs.getInt("hasLended");
                String address = rs.getString("address");
                Book book = new Book(id, name, count,type,author,discount,hasLended,address);
                list.add(book);
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
    
    
    public boolean delBooks(int id) {
        String sql="delete from Book where id = "+id;
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
    
    
    public boolean updateBook(int id , int count, int discount) {
        String sql = "update Book set count=?,discount=? where id="+id;
        Connection conn = BaseDaoImpl.getConn();
        PreparedStatement psts=null;
        try {
            psts = conn.prepareStatement(sql);
            psts.setObject(1, count);
            psts.setObject(2, discount);
            int res = psts.executeUpdate();
            if(res > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                psts.close();//关闭预编译
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public Book searchBook(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public Book queryBook(int bId) {
        // TODO Auto-generated method stub
        return null;
    }

}
