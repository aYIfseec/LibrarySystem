package ui;

public class Index {
    public static Login login;

    /**
     * 整个程序开始的地方main方法
     * @param args
     */
    public static void main(String args[]) {
        new Index();
    }

    // Index类的构造方法
    public Index() {
        // new一个Login类
        login = new Login();
    }

    public static Login getLogin() {
        return login;
    }
}
