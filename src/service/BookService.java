package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import util.DateUtil;

import modle.Book;
import modle.Record;

import dao.BaseDaoImpl;
import dao.BookDaoImpl;
import dao.RecordDaoImpl;

public class BookService {
    private static BookService bookService;
    static {
        bookService = new BookService();
    }
    
    private BookDaoImpl bookDao;
    private RecordDaoImpl recordDao;
    
    private BookService() {
        bookDao = BookDaoImpl.getInstance();
        recordDao  = RecordDaoImpl.getInstance();
    }
    
    public static BookService getInstance() {
        return bookService;
    }
    
    public static final String CAN = "可借";
    public static final String CANT = "无库存";
    
    public boolean lendBook(int uId, String name){
        Book bk = bookDao.searchBook(name);
        // 已借出数量等于书籍总数量，则不可借
        if(bk.getCount() == bk.getHasLended()) return false;
        Record rd = new Record(uId,bk.getId(), DateUtil.getDate(), "未还");
        bk.setHasLended(bk.getHasLended() + 1);
        bk.setDiscount(bk.getDiscount());
        Connection conn = BaseDaoImpl.getConn();
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        boolean res = bookDao.updateBook(bk, conn);
        if(!res) {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
        res = recordDao.insertRecord(rd, conn);
        if (!res) {
            try {
                conn.rollback();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return false;
        } else {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    }

    
    public boolean returnBook(int uId, String name){
        Book bk = bookDao.searchBook(name);
        if (bk.getId() <= 0) return false;
        bk.setHasLended(bk.getHasLended() - 1);
        Connection conn = BaseDaoImpl.getConn();
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        boolean res = recordDao.updataRecord(uId, bk.getId(), DateUtil.getDate(), conn);
        if(!res) {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
        res = bookDao.updateBook(bk, conn);
        if(!res) {
            try {
                conn.rollback();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return false;
        } else {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    }

    public Object[][] queryAllBooks() {
        List<Book> list = bookDao.queryAllBooks();
        Object[][] obj = new Object[list.size()][7];
        int i = 0;
        for (Book book: list) {
            obj[i][0] = book.getHasLended() < book.getCount() ? CAN : CANT;
            obj[i][1] = book.getName();
            obj[i][2] = book.getCount() - book.getHasLended();
            obj[i][3] = book.getType();
            obj[i][4] = book.getAuthor();
            obj[i][5] = book.getDiscount();
            obj[i][6] = book.getAddress();
            i++;
        }
        return obj;
    }

    public Object[][] queryBooks() {
        List<Book> list = bookDao.queryAllBooks();
        Object[][] obj = new Object[list.size()][7];
        int i = 0;
        for (Book book: list) {
            obj[i][0] = book.getId();
            obj[i][1] = book.getName();
            obj[i][2] = book.getCount() - book.getHasLended();
            obj[i][3] = book.getType();
            obj[i][4] = book.getAuthor();
            obj[i][5] = book.getDiscount();
            obj[i][6] = book.getAddress();
            i++;
        }
        return obj;
    }

    public boolean insertBook(String name, int count, String type,
            String author, String address) {
        if (bookDao.searchBook(name).getId() > 0) return false;
        return bookDao.insertBook(name, count, type, author, address);
    }

}
