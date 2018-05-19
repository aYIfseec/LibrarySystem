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
    public boolean insertBook(String name, int count, String type,
            String author, String address) {
        String sql = "insert into " +
        		"Book(name, count, type, author, discount, hasLended, address)" +
        		"values(?,?,?,?,?,?,?)";
        Connection conn = BaseDaoImpl.getConn();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            psts.setObject(1, name);
            psts.setObject(2, count);
            psts.setObject(3, type);
            psts.setObject(4, author);
            psts.setObject(5, 0);
            psts.setObject(6, 0);
            psts.setObject(7, address);
            return psts.executeUpdate() > 0;// 执行 ，返回值为int
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
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
            rs = psts.executeQuery(); // 执行
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
    
    
    public boolean delBook(String name) {
        String sql="delete from Book where name = '" + name + "'";
        Connection conn = BaseDaoImpl.getConn();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            int res = psts.executeUpdate();
            if(res > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                psts.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    
    public boolean updateBook(Book book, Connection conn) {
        boolean isRelease = false;
        String sql = "update Book set ";
        if (book.getCount() != 0) {
            sql += "count="+book.getCount() + ",";
        }
        if (book.getDiscount() != 0) {
            sql += "discount="+book.getDiscount() + ",";
        }
        if (book.getHasLended() != 0) {
            sql += "hasLended="+book.getHasLended() + ",";
        }
        if (book.getType() != null) {
            sql += "type='"+book.getType() + "',";
        }
        if (book.getAddress() != null) {
            sql += "address='"+book.getAddress() + "' ,";
        }
        if (book.getAuthor() != null) {
            sql += "author='"+book.getAuthor() + "' ,";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += " where name='" + book.getName() + "'";
        
        System.out.println(sql);
        
        if(conn == null) {
            conn = BaseDaoImpl.getConn();
            isRelease = true;
        }
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            int res = psts.executeUpdate();
            if(res > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try {
                if (isRelease && conn != null) conn.close();
                if (psts != null) psts.close();//关闭预编译
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public Book searchBook(String name) {
        String sql = "select * from Book where name = '" + name + "'";
        Connection conn = BaseDaoImpl.getConn();
        ResultSet rs = null;
        Book book = new Book();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            rs = psts.executeQuery(); // 执行
            if (rs.next()) {
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setCount(rs.getInt("count"));
                book.setType(rs.getString("type"));
                book.setAuthor(rs.getString("author"));
                book.setDiscount(rs.getInt("discount"));
                book.setHasLended(rs.getInt("hasLended"));
                book.setAddress(rs.getString("address"));
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
        return book;
    }

    public Book queryBook(int bId) {
        String sql = "select * from Book where id = " + bId;
        Connection conn = BaseDaoImpl.getConn();
        ResultSet rs = null;
        Book book = new Book();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            rs = psts.executeQuery(); // 执行
            book.setId(rs.getInt("id"));
            book.setName(rs.getString("name"));
            book.setCount(rs.getInt("count"));
            book.setType(rs.getString("type"));
            book.setAuthor(rs.getString("author"));
            book.setDiscount(rs.getInt("discount"));
            book.setHasLended(rs.getInt("hasLended"));
            book.setAddress(rs.getString("address"));
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
        return book;
    }

}
