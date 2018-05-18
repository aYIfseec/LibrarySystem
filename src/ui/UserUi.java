package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import service.BookService;
import util.ShowMessageUtil;

import modle.Book;
import modle.Record;
import modle.User;

import dao.BookDaoImpl;
import dao.RecordDaoImpl;
import dao.RemindDaoImpl;
import dao.UserDaoImpl;

public class UserUi extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    
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
    private JLabel card1 = new JLabel();
    private JLabel card2 = new JLabel();
    private JLabel card3 = new JLabel();
    private JLabel card4 = new JLabel();

    private JLabel bookname = new JLabel("书  名");
    private JButton btnSchB = new JButton("查 找");
    private JButton btnBook = new JButton("预约此书");
    private JTextField textName = new JTextField();

    private JLabel bNum = new JLabel("书 名");
    private JTextField textBnum = new JTextField();
    private String lendstr = new String(
            "<html><style>"
                    + "table{padding-left:40px;}td{width:150px;}.num{width:80px;}</style>"
                    + "<table><tr><td class='num'>序 号</td><td>书 名</td><td>借书时间</td><td>还书时间</td></tr>");

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
        bookDao = BookDaoImpl.getInstance();
        userDao = UserDaoImpl.getInstance();
        recordDao = RecordDaoImpl.getInstance();
        bookService = BookService.getInstance();
        
        uId = id;
        
        setTitle("图书馆系统");
        setResizable(false);
        setBounds((sc.width - 900) / 2, (sc.height - 500) / 2, 900, 500);// 设置位置
                                                                         // 及
                                                                         // 窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);// 点击栏位置
        tabbedPane.setFont(font);// 左栏字体，字号
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); // WRAP_TAB_LAYOUT
        
        reFresh.addActionListener(this);
        btnexit.addActionListener(this);
        this.setJMenuBar(menuBar);
        menuBar.add(reFresh);
        menuBar.add(btnexit);

        getContentPane().add(tabbedPane);
        // card0
        tabbedPane.addTab("欢迎用户使用", card0);// 后面将card0设为不可用

        // card1
        tabbedPane.addTab("1.所有书籍", card1);
        card1.setFont(font);
        
        // 获取所有书籍
        String bookStr = bookService.queryAllBooks();
        
        JLabel allbooks = new JLabel();
        //allbooks.add(comp)
        allbooks.setText(bookStr);
        allbooks.setFont(font);
        allbooks.setBounds(0, 20, 750, 460);
        card1.add(allbooks);
        card1.setText("");


        // card2
        tabbedPane.addTab("2.借阅图书", card2);
        bookname.setBounds(160, 50, 80, 30);
        textName.setBounds(240, 50, 200, 30);
        btnSchB.setBounds(450, 50, 80, 30);
        btnSchB.addActionListener(this);
        btnBook.setBounds(240, 300, 100, 30);
        btnBook.addActionListener(this);
        card2.setFont(font);
        bookname.setFont(font);
        textName.setFont(font);
        btnSchB.setFont(font1);
        btnBook.setFont(font1);
        card2.add(textName);
        card2.add(bookname);
        card2.add(btnSchB);
        card2.add(btnBook);
        btnSchB.addActionListener(this);

        // card3
        tabbedPane.addTab("3.借还记录", card3);
        List<Record> list3 = recordDao.getRecordsByUid(uId);
        String record = new String(lendstr);
        int len3 = list3.size(), k;
        for (k = 0; k < len3; k++) {
            List<Book> listb = bookDao.queryAllBooks();
            int x;
            for (x = 0; x < listb.size(); x++)
                if (listb.get(x).getId() == list3.get(k).getBid())
                    break;
            record += "<tr><td class='num'>" + (k + 1) + "</td><td>"
                    + listb.get(x).getName() + "</td><td>"
                    + list3.get(k).getLendTime() + "</td><td>"
                    + list3.get(k).getReturnTime() + "</td></tr>";
        }
        record += "</table></html>";
        bNum.setBounds(160, 50, 50, 30);
        textBnum.setBounds(210, 50, 200, 30);
        bNum.setFont(font1);
        textBnum.setFont(font1);
        card3.add(bNum);
        card3.add(textBnum);
        // card3.setText(record);
        JLabel allRecords = new JLabel();
        allRecords.setText(record);
        allRecords.setFont(font);
        allRecords.setBounds(50, 100, 700, (len3 + 1) * 30);
        card3.add(allRecords);
        card3.setFont(font);
        card3.setText("");

        // card4
        tabbedPane.addTab("4.个人信息", card4);
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
        btnYes.addActionListener(this);
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
                        + me.getName()
                        + "</td><td>"
                        + me.getId()
                        + "</td><td>"
                        + me.getPoint()
                        + "</td></tr>"
                        + "<tr></tr></table><p>您可以修改自己的用户名及密码</p></html>");
        myinfo.setBounds(150, 0, 450, 200);
        myinfo.setFont(font);
        myinfo.setText(myInfo);
        card4.add(myinfo);
        // card4.setText(myInfo);
        card4.setFont(font);

        tabbedPane.setSelectedIndex(defaultCard); // 设置默认选中的
        tabbedPane.setEnabledAt(0, false);// 设置索引0的面板不可用
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == this.btnSchB) {// 查找
            String name = textName.getText();
            if (name.equals("")) {
                ShowMessageUtil.winMessage("您还没有输入书名！");
            } else {
                Book book = bookDao.searchBook(name);
                
                if (book == null) {
                    ShowMessageUtil.winMessage("书籍不存在!");
                    bid = null;
                } else {
                    bid = book.getId();
                    String info = new String();
                    if (book.getCount() == 0)
                        info = "可预约";
                    else
                        info = "可借";
                    String str = new String("");
                    str += "<tr><td>" + info + "</td><td class='name'>"
                            + book.getName() + "</td><td>" + book.getCount()
                            + "</td><td>" + book.getType() + "</td><td>"
                            + book.getAuthor() + "</td><td>"
                            + book.getDiscount() + "</td><td>"
                            + book.getAddress() + "</td></tr></table></html>";
                    card2.setText(str);
                }
            }
        } else if (e.getSource() == this.btnBook) {// 预约
            if (bid == null) {
                ShowMessageUtil.winMessage("请先查找您想借的书籍！");
            } else {
                if (remindDao.insertRemind(uId, bid)) {
                    ShowMessageUtil.winMessage("预约书籍成功！");
                    mainUi(uId, 2);
                    this.dispose();
                } else
                    ShowMessageUtil.winMessage("预约书籍失败~\n可能是图书馆没有此书。");
            }
        } else if (e.getSource() == this.btnYes) {// 改个人信息
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
                        mainUi(uId, 4);
                        this.dispose();
                    } else
                        ShowMessageUtil.winMessage("修改失败~");
                } else
                    ShowMessageUtil.winMessage("旧密码错误！");
            }
        } else if (e.getSource() == this.reFresh) {// 刷新
            mainUi(uId, 1);
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
}