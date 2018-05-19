package service;

import java.util.List;

import modle.Book;

import dao.RemindDaoImpl;

public class RemindService {
    
    public static final String PRE = "《";
    public static final String TAIL = "》";
    public static final String SPACE = "、";
    public static final String SPLIT = ",";
    
    
    private static RemindService remindService;
    static {
        remindService = new RemindService();
    }
    
    private RemindDaoImpl remindDao;
    
    private RemindService() {
        remindDao = RemindDaoImpl.getInstance();
    }
    
    
    public static RemindService getInstance() {
        return remindService;
    }

    public String getRemindByUid(int id) {
        List<Book> books = remindDao.getRemindByUid(id);
        StringBuilder str = new StringBuilder();
        StringBuilder ids = new StringBuilder();
        
        if (books.size() > 0) {
            str.append(PRE).append(books.get(0).getName()).append(TAIL);
            ids.append(books.get(0).getId());
        }
        for(int j = 1; j < books.size(); j++) {
            str.append(SPACE).append(PRE);
            str.append(books.get(j).getName()).append(TAIL);
            
            ids.append(SPLIT);
            ids.append(books.get(j).getId());
        }
        if (ids.length() > 0) {
            remindDao.updateRemindBatch(ids.toString());
        }
        return str.toString();
    }

    
}
