package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import service.BookService;
import util.ShowMessageUtil;

import modle.Book;
import modle.User;

import dao.BookDaoImpl;
import dao.RecordDaoImpl;
import dao.RemindDaoImpl;
import dao.UserDaoImpl;

public class UserUi extends JFrame implements ActionListener, ChangeListener {
    private static final long serialVersionUID = 1L;
    public static final int CARD0 = 0;
    public static final int CARD1 = 1;
    public static final int CARD2 = 2;
    public static final int CARD3 = 3;
    public static final int CARD4 = 4;
    public static final int CARD5 = 5;
    
    private UserDaoImpl userDao;
    private BookDaoImpl bookDao;
    private RecordDaoImpl recordDao;
    private RemindDaoImpl remindDao;
    private BookService bookService;
    
    private int uId;
    private Integer bid = null;

    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Dimension sc = toolkit.getScreenSize();
    private JLabel card0 = new JLabel();
    private JPanel card1 = new JPanel();
    private JLabel card2 = new JLabel();
    private JPanel card3 = new JPanel();
    private JLabel card4 = new JLabel();
    private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);// 点击栏位置

    private JLabel bookname = new JLabel("书  名");
    private JButton btnSchB = new JButton("查 找");
    private JButton btnBook = new JButton("设置提醒");
    private JTextField textName = new JTextField();

    String[] recordTableHead = {"序 号", "书 名","借书时间","还书时间"};
    String[] bookTableHead = {"状态", "书 名","剩余库存","类型","作者","借阅总次","所在位置"};
    
    private JLabel uName = new JLabel("用户名");
    private JTextField textUname = new JTextField();
    private JLabel oldPswd = new JLabel("旧密码");
    private JPasswordField textOldPswd = new JPasswordField();
    private JLabel newPswd = new JLabel("新密码");
    private JPasswordField textNewPswd = new JPasswordField();
    private JButton btnYes = new JButton("确 定");
    private JLabel myinfo = new JLabel("");

    private Font font = new Font("楷体", 0, 20);
    private Font font1 = new Font("楷体", 0, 16);

    private JButton reFresh = new JButton("刷新");
    private JButton btnexit = new JButton("退出");//
    private JMenuBar menuBar = new JMenuBar();

    public UserUi(int id, int defaultCard) {
        uId = id;
        init();
        initPanel();
        // card0 此card仅用于放欢迎词，后面将card0设为不可点击
        tabbedPane.addTab("欢迎用户使用", card0);
        tabbedPane.addTab("1.所有书籍", card1);
        tabbedPane.addTab("2.可借提醒", card2);
        tabbedPane.addTab("3.借还记录", card3);
        tabbedPane.addTab("4.个人信息", card4);
        
        tabbedPane.setSelectedIndex(defaultCard); // 设置默认选中的
        tabbedPane.setEnabledAt(CARD0, false);// 设置索引0的面板不可用
        
        addListener();
    }
    
    private void addListener() {
        btnBook.addActionListener(this);
        btnSchB.addActionListener(this);
        btnYes.addActionListener(this);
    }

    private void initCard4() {
        uName.setBounds(160, 230, 60, 30);
        textUname.setBounds(220, 230, 200, 30);
        oldPswd.setBounds(160, 280, 60, 30);
        textOldPswd.setBounds(220, 280, 200, 30);
        newPswd.setBounds(160, 330, 60, 30);
        textNewPswd.setBounds(220, 330, 200, 30);
        btnYes.setBounds(230, 380, 90, 30);
        uName.setFont(font1);
        textUname.setFont(font1);
        oldPswd.setFont(font1);
        textOldPswd.setFont(font1);
        textOldPswd.setEchoChar('*');
        newPswd.setFont(font1);
        textNewPswd.setFont(font1);
        textNewPswd.setEchoChar('*');
        btnYes.setFont(font);
        
        card4.add(uName);
        card4.add(textUname);
        card4.add(oldPswd);
        card4.add(textOldPswd);
        card4.add(newPswd);
        card4.add(textNewPswd);
        card4.add(btnYes);
        User me = userDao.queryUser(uId, null);
        String myInfo = new String(
                "<html><style>td{width:150px;}p{font-size:18px;}th{width:400px;text-align:center;}</style>"
                + "<p>您的基本信息如下:</p><table><tr></tr><tr><td>用户名</td><td>账 号</td><td>积 分</td></tr><tr><td>"
                + me.getName() + "</td><td>" + me.getId() + "</td><td>" + me.getPoint() + "</td></tr>"
                + "<tr></tr></table><p>您可以修改自己的用户名及密码</p></html>");
        myinfo.setBounds(150, 0, 450, 200);
        myinfo.setFont(font);
        myinfo.setText(myInfo);
        card4.add(myinfo);
        card4.setFont(font);
    }
    
    private void initCard3() {
        // new JTable 传入数据与表头
        JTable recordTable = new JTable(recordDao.getRecordsByUid(uId), recordTableHead);
        recordTable.setFont(font);
        recordTable.setPreferredSize(new Dimension(740, 460));
        recordTable.getTableHeader().setPreferredSize(new Dimension(0, 30));
        recordTable.getTableHeader().setFont(font1);
        recordTable.setRowHeight(30);
        recordTable.setEnabled(false); // 设置为不开操作
        JScrollPane scroll = new JScrollPane(recordTable);
        scroll.setPreferredSize(new Dimension(740, 430));
        card3.removeAll();
        card3.add(scroll);
    }
    private void initCard1() {
        card1.removeAll();
        JTable bookTable = new JTable(bookService.queryAllBooks(), bookTableHead);
        bookTable.setFont(font);
        bookTable.setPreferredSize(new Dimension(740, 460));
        bookTable.getTableHeader().setPreferredSize(new Dimension(0, 30));
        bookTable.getTableHeader().setFont(font1);
        bookTable.setRowHeight(30);
        bookTable.setEnabled(false);
        JScrollPane scroll = new JScrollPane(bookTable);
        scroll.setPreferredSize(new Dimension(740, 430));
        card1.add(scroll);
    }
    private void initCard2() {
        bookname.setBounds(160, 50, 80, 30);
        textName.setBounds(240, 50, 200, 30);
        btnSchB.setBounds(450, 50, 80, 30);
        btnBook.setBounds(240, 300, 100, 30);
        card2.setFont(font);
        bookname.setFont(font);
        textName.setFont(font);
        btnSchB.setFont(font1);
        btnBook.setFont(font1);
        card2.add(textName);
        card2.add(bookname);
        card2.add(btnSchB);
        card2.add(btnBook);
    }

    private void init() {
        bookDao = BookDaoImpl.getInstance();
        userDao = UserDaoImpl.getInstance();
        recordDao = RecordDaoImpl.getInstance();
        bookService = BookService.getInstance();
        remindDao = RemindDaoImpl.getInstance();
    }
    
    private void initPanel() {
        setTitle("图书馆系统");
        setResizable(false);
        setBounds((sc.width - 900) / 2, (sc.height - 500) / 2, 900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        tabbedPane.setFont(font);// 左栏字体，字号
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); // WRAP_TAB_LAYOUT
        tabbedPane.addChangeListener(this);
        reFresh.addActionListener(this);
        btnexit.addActionListener(this);
        this.setJMenuBar(menuBar);
        menuBar.add(reFresh);
        menuBar.add(btnexit);
        getContentPane().add(tabbedPane);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnSchB) {
            // 查找
            String name = textName.getText();
            if (name.equals("")) {
                ShowMessageUtil.winMessage("您还没有输入书名！");
            } else {
                Book book = bookDao.searchBook(name);
                if (book == null  || book.getId() <= 0) {
                    ShowMessageUtil.winMessage("书籍不存在!");
                } else {
                    bid = book.getId();
                    String info = new String();
                    if (book.getCount() == 0)
                        info = "无库存";
                    else
                        info = "可借";
                    String str = new String("<html>");
                    str += "状态：" + info
                            + "<br>书名：" + book.getName()
                            + "<br>作者：" + book.getAuthor()
                            + "<br>类型：" + book.getType()
                            + "<br>库存剩余：" + (book.getCount() - book.getHasLended())
                            + "<br>借阅总次：" + book.getDiscount()
                            + "<br>藏书位置：" + book.getAddress() + "</div></html>";
                    card2.setText(str);
                }
            }
        } else if (e.getSource() == this.btnBook) {
            // 设置提醒
            if (bid == null) {
                ShowMessageUtil.winMessage("请先查找您想提醒借阅的书籍！");
            } else {
                if (remindDao.insertRemind(uId, bid)) {
                    ShowMessageUtil.winMessage("设置成功！");
                    bid = null;
                } else{
                    ShowMessageUtil.winMessage("设置失败~\n可能是图书馆没有此书。");
                }
            }
        } else if (e.getSource() == this.btnYes) {
            // 改个人信息
            String name = textUname.getText();
            String oldPswd = new String(textOldPswd.getPassword());
            String newPswd = new String(textNewPswd.getPassword());
            if (name == null || oldPswd == null || newPswd == null)
                ShowMessageUtil.winMessage("您的信息没有填齐全！");
            else {
                User me = userDao.queryUser(uId, null);
                if (me.getPassword().equals(oldPswd)) {
                    if (userDao.updUserName(uId, name)
                            && userDao.updUserPassword(uId, newPswd, "User")) {
                        ShowMessageUtil.winMessage("修改成功！");
                        mainUi(uId, CARD4);
                        this.dispose();
                    } else{
                        ShowMessageUtil.winMessage("修改失败~");
                    }
                } else {
                    ShowMessageUtil.winMessage("旧密码错误！");
                }
            }
        } else if (e.getSource() == this.reFresh) {// 刷新
            mainUi(uId, CARD1);
            this.dispose();
        } else if (e.getSource() == this.btnexit) {// 退出登录
            new Index();
            this.dispose();
        }
    }

    public static void mainUi(int id, int defaultCard) {
        UserUi jTabbedPaneTest = new UserUi(id, defaultCard);
        jTabbedPaneTest.setVisible(true);
    }

    
    /**
     * 点击左边tab会触发的监听事件
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        // 获得选中的tab的index，根据index初始化不同的card
        int selectIndex = tabbedPane.getSelectedIndex();
        if (selectIndex == CARD1) {
            initCard1();
        } else if (selectIndex == CARD2) {
            initCard2();
        } else if (selectIndex == CARD3) {
            initCard3();
        } else if (selectIndex == CARD4) {
            initCard4();
        } else {
            System.out.println(selectIndex);
        }
    }
}