package service;

import util.ShowMessageUtil;

import dao.AdminDaoImpl;
import dao.UserDaoImpl;

import modle.Admin;
import modle.User;

public class LoginService {
    private static LoginService loginService;
    
    private UserDaoImpl userDao;
    private AdminDaoImpl admDao;
    private RemindService remindService;
    
    static {
        loginService = new LoginService();
    }
    
    public static LoginService getInstance() {
        return loginService;
    }
    
    private LoginService() {
        userDao = UserDaoImpl.getInstance();
        admDao = AdminDaoImpl.getInstance();
        remindService = RemindService.getInstance();
    }
    
    public User userLogin(int id, String password) {
        User u = userDao.queryUser(id, password);
        if (u != null) {
            String remind = remindService.getRemindByUid(id);
            if(remind != null && !remind.equals("")) {
                ShowMessageUtil.winMessage("您预约的书" + remind + "可借了！\n赶紧去看看吧!");
            }
        }
        return u;
    }

    public Admin adminLogin(int id, String password) {
        return admDao.queryAdmin(id, password);
    }
    
}
