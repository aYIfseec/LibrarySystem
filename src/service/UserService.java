package service;

import java.util.List;

import dao.*;
import modle.*;
import util.ShowMessageUtil;

public class UserService {
    /*
     * 饿汉单例模式，
     * 保证系统中只有一个UserService实例
     * 可节约内存、提升效率等
     */
    private static UserService userService;
    static {
        userService = new UserService();
    }
    
    public static UserService getInstance() {
        return userService;
    }
    
    private UserDaoImpl userDao;
    
    private UserService(){
        userDao = UserDaoImpl.getInstance();
    }
    
    
	public boolean addUser(int id, String name, String password) {
		User user = new User(id, name, password, 50);
		if (userDao.insertUser(user)) {
			ShowMessageUtil.winMessage("注册成功！");
			return true;
		} else {
		    ShowMessageUtil.winMessage("注册失败，可能是该账号已存在！您可以换一个账号试试。");
		    return false;
		}
	}


    public Object[][] queryAllUser() {
        List<User> list = userDao.queryAllUser();
        Object[][] obj = new Object[list.size()][4];
        int i = 0;
        for (User u: list) {
            obj[i][0] = u.getId();
            obj[i][1] = u.getName();
            obj[i][2] = u.getPassword();
            obj[i][3] = u.getPoint();
            i++;
        }
        return obj;
    }

    
}
