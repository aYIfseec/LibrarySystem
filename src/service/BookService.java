package service;

import java.util.List;

import util.DateUtil;

import modle.Book;
import modle.Record;

import dao.BookDaoImpl;
import dao.RecordDaoImpl;
import dao.UserDaoImpl;

public class BookService {
    private static BookService bookService;
    static {
        bookService = new BookService();
    }
    
    private BookDaoImpl bookDao;
    private UserDaoImpl userDao;
    private RecordDaoImpl recordDao;
    
    private BookService() {
        bookDao = BookDaoImpl.getInstance();
        userDao = UserDaoImpl.getInstance();
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
        if(bk.getCount() == 0) return false;
        Record rd = new Record(uId,bk.getId(), DateUtil.getDate(), "未还");
        if(bookDao.updateBook(bk.getId(), bk.getCount()-1,bk.getDiscount()+1)
                && recordDao.insertRecord(rd))
            return true;
        else return false;
    }

    
    public boolean returnBook(int uId, int bId){
        Book bk = bookDao.queryBook(bId);
        if(bookDao.updateBook(bk.getId(), bk.getCount()+1, bk.getDiscount())
                && recordDao.updataRecord(uId, bk.getId(), DateUtil.getDate()))
            return true;
        else return false;
    }

    public boolean updateBook(Book b) {
        return false;
    }

    public void addBooks(int id, String name, int count, String type,
            String author, String address) {
        // TODO Auto-generated method stub
        
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
