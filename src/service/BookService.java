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
    
    public static final String TABLE_HEAD = "<html><style>"
            + "table{margin-top:0px;padding-left:10px;}"
            + "td{width:100px;}.name{width:150px;}</style>"
            + "<table><tr><td>状 态</td><td class='name'>书 名</td><td>剩余数量</td><td>类 型</td>"
            + "<td>作 者</td><td>被借次数</td><td>所在位置</td></tr>";
    public static final String TABLE_TAIL = "</table></html>";
    
    public boolean lendBook(int uId, int bId){
        Book bk = bookDao.queryBook(bId);
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

    
    public boolean returnBook(int uId, int bId){
        Book bk = bookDao.queryBook(bId);
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

    public boolean updateBook(Book b) {
        return false;
    }


    public String queryAllBooks() {
        List<Book> list = bookDao.queryAllBooks();
        StringBuilder booksStr = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String str;
            if (list.get(i).getCount() == 0) {
                str = "无库存";
            } else {
                str = "可借";
            }
            booksStr.append("<tr><td>").append(str);
            booksStr.append("</td><td class='name'>");
            booksStr.append(list.get(i).getName()).append("</td><td>");
            booksStr.append(list.get(i).getCount()).append("</td><td>");
            booksStr.append(list.get(i).getType()).append("</td><td>");
            booksStr.append(list.get(i).getAuthor()).append("</td><td>");
            booksStr.append(list.get(i).getDiscount()).append("</td><td>");
            booksStr.append(list.get(i).getAddress()).append("</td></tr>");
        }
        return TABLE_HEAD + booksStr.toString() + TABLE_TAIL;
    }
}
