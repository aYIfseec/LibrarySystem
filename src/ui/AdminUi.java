package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dao.*;
import modle.*;
import service.*;
import util.ShowMessageUtil;

public class AdminUi extends JFrame implements ActionListener, ChangeListener{
    private static final long serialVersionUID = 1L;
    
    private UserDaoImpl userDao;
    private BookDaoImpl bookDao;
    private BookService bookService;
    private UserService userService;
    
    private int aId;
    private int uId;
    private String bName;

    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Dimension sc = toolkit.getScreenSize();
    private JLabel card0 = new JLabel();
    private JPanel card1 = new JPanel();
    private JLabel card2 = new JLabel();
    private JLabel card3 = new JLabel();
    private JPanel card4 = new JPanel();
    private JLabel card5 = new JLabel();

    private JLabel id = new JLabel("用户账号");
    private JTextField textId = new JTextField();
    private JButton btnSchU = new JButton("查 找");
    private JLabel delU = new JLabel("您可以做如下操作：");
    private JButton btnDelU = new JButton("删除该用户");
    private JPasswordField textNewPswd = new JPasswordField();
    private JButton newPswd = new JButton("修改其密码");

    private JLabel newUid = new JLabel("账  号");
    private JLabel newUname = new JLabel("用户名");
    private JLabel newUpswd = new JLabel("密  码");
    private JTextField textUid = new JTextField();
    private JTextField textUname = new JTextField();
    private JPasswordField textUpswd = new JPasswordField();
    private JButton btnNewUser = new JButton("确定");

    private JLabel bookname = new JLabel("书 名");
    private JButton btnSchB = new JButton("查 找");
    private JTextField textName = new JTextField();
    private JButton btnDelB = new JButton("删 除");

    private JLabel count5 = new JLabel("数 量");
    private JLabel type5 = new JLabel("类 型");
    private JLabel author5 = new JLabel("作 者");
    private JLabel address5 = new JLabel("藏书位置");
    private JTextField count_5 = new JTextField();
    private JTextField type_5 = new JTextField();
    private JTextField author_5 = new JTextField();
    private JTextField address_5 = new JTextField();
    private JButton button5 = new JButton("添 加");
    private JButton btnUpdate5 = new JButton("修 改");
    
    private JButton lendBtn = new JButton("借出");
    private JButton returnBtn = new JButton("还回");
    
    private String[] userTableHead = {"账号", "用户名", "密码", "积分"};
    private String tableUser = new String("<html><style>"
            + "table{margin-top:0px;padding-left:80px;}"
            + "td{width:150px;}</style>"
            + "<table><tr><td>账号</td><td>用户名</td><td>密码</td><td>积分</td></tr>");

    String[] bookTableHead = {"编号", "书 名","剩余库存","类型","作者","借阅总次","所在位置"};

    private Font font = new Font("楷体", 0, 20);
    private Font font1 = new Font("楷体", 0, 16);

    private JTabbedPane tabbedPane;
    private JButton reFresh = new JButton("刷新");
    private JButton btnexit = new JButton("退出");//
    private JMenuBar menuBar = new JMenuBar();

    public AdminUi(int aId, int defaultCard) {
        this.aId = aId;
        init();
        initPane();
        tabbedPane.addTab("欢迎管理员", card0);// 后面将card0设为不可用
        tabbedPane.addTab("1.所有用户信息", card1);
        tabbedPane.addTab("2.用户管理", card2);
        tabbedPane.addTab("3.添加用户", card3);
        tabbedPane.addTab("4.查看所有书籍", card4);
        tabbedPane.addTab("5.书籍管理", card5);
        tabbedPane.setSelectedIndex(defaultCard);
        tabbedPane.setEnabledAt(0, false);
        
        addListenrt();
    }
    private void addListenrt() {
        newPswd.addActionListener(this);
        btnSchU.addActionListener(this);
        btnDelU.addActionListener(this);
        btnNewUser.addActionListener(this);
        
        btnDelB.addActionListener(this);
        btnSchB.addActionListener(this);
        
        button5.addActionListener(this);
        btnUpdate5.addActionListener(this);
        
        returnBtn.addActionListener(this);
        lendBtn.addActionListener(this);
    }
    private void initCard1() {
        card1.removeAll();
        JTable userTable = new JTable(userService.queryAllUser(), userTableHead);
        userTable.setFont(font);
        userTable.setPreferredSize(new Dimension(720, 450));
        userTable.getTableHeader().setPreferredSize(new Dimension(0, 30));
        userTable.getTableHeader().setFont(font1);
        userTable.setRowHeight(30);
        userTable.setEnabled(false); // 设置为不开操作
        JScrollPane scroll = new JScrollPane(userTable);
        scroll.setPreferredSize(new Dimension(720, 430));
        card1.add(scroll);
        card1.setFont(font);
    }
    private void initCard2() {
        id.setBounds(150, 100, 90, 30);
        textId.setBounds(240, 100, 200, 30);
        btnSchU.setBounds(450, 100, 80, 30);
        delU.setBounds(170, 300, 200, 30);
        btnDelU.setBounds(370, 300, 120, 30);
        newPswd.setBounds(370, 350, 120, 30);
        textNewPswd.setBounds(170, 350, 170, 30);
        card2.setFont(font);
        id.setFont(font);
        textId.setFont(font);
        btnSchU.setFont(font1);
        delU.setFont(font);
        btnDelU.setFont(font1);
        newPswd.setFont(font1);
        textNewPswd.setFont(font);
        textNewPswd.setEchoChar('*');
        card2.add(textId);
        card2.add(id);
        card2.add(delU);
        card2.add(btnDelU);
        card2.add(btnSchU);
        card2.add(newPswd);
        card2.add(textNewPswd);
    }
    private void initCard3() {
        newUid.setBounds(150, 80, 80, 30);
        textUid.setBounds(230, 80, 200, 30);
        newUname.setBounds(150, 150, 80, 30);
        textUname.setBounds(230, 150, 200, 30);
        newUpswd.setBounds(150, 220, 80, 30);
        textUpswd.setBounds(230, 220, 200, 30);
        btnNewUser.setBounds(250, 300, 90, 30);
        newUid.setFont(font);
        textUid.setFont(font);
        newUname.setFont(font);
        textUname.setFont(font);
        newUpswd.setFont(font);
        textUpswd.setEchoChar('*');// 可以将密码显示为* ；默认为· 但默认又对其设置了字体时会乱码
        textUpswd.setFont(font);
        btnNewUser.setFont(font);
        card3.add(newUid);
        card3.add(textUid);
        card3.add(newUname);
        card3.add(textUname);
        card3.add(newUpswd);
        card3.add(textUpswd);
        card3.add(btnNewUser);
    }
    private void initCard4() {
        JTable bookTable = new JTable(bookService.queryBooks(), bookTableHead);
        bookTable.setFont(font);
        bookTable.setPreferredSize(new Dimension(720, 440));
        bookTable.getTableHeader().setPreferredSize(new Dimension(0, 30));
        bookTable.getTableHeader().setFont(font1);
        bookTable.setRowHeight(30);
        bookTable.setEnabled(false); // 设置为不开操作
        JScrollPane scroll = new JScrollPane(bookTable);
        scroll.setPreferredSize(new Dimension(720, 430));
        card4.removeAll();
        card4.add(scroll);
    }
    private void initCard5() {
        // 查找书籍，删除书籍，修改书籍信息（内容太多的话考虑放在另一个界面）
        bookname.setBounds(150, 40, 80, 30);
        textName.setBounds(230, 40, 200, 30);
        btnSchB.setBounds(450, 40, 100, 30);
        
        bookname.setFont(font);
        textName.setFont(font);
        btnSchB.setFont(font);
        
        card5.add(bookname);
        card5.add(textName);
        card5.add(btnSchB);
        
        
        btnDelB.setBounds(350, 390, 90, 30);
        lendBtn.setBounds(450, 390, 90, 30);
        returnBtn.setBounds(550, 390, 90, 30);
        btnDelB.setFont(font1);
        lendBtn.setFont(font1);
        returnBtn.setFont(font1);
        card5.add(btnDelB);
        card5.add(lendBtn);
        card5.add(returnBtn);
        
        count5.setBounds(150, 100, 80, 30);
        count_5.setBounds(230, 100, 200, 30);
        type5.setBounds(150, 160, 80, 30);
        type_5.setBounds(230, 160, 200, 30);
        author5.setBounds(150, 220, 80, 30);
        author_5.setBounds(230, 220, 200, 30);
        address5.setBounds(150, 280, 80, 30);
        address_5.setBounds(230, 280, 200, 30);
        button5.setBounds(250, 390, 90, 30);
        
        count5.setFont(font);
        count_5.setFont(font);
        type5.setFont(font);
        type_5.setFont(font);
        author5.setFont(font);
        author_5.setFont(font);
        address5.setFont(font);
        address_5.setFont(font);
        button5.setFont(font);
        card5.add(count5);
        card5.add(count_5);
        card5.add(type_5);
        card5.add(type5);
        card5.add(author5);
        card5.add(author_5);
        card5.add(address5);
        card5.add(address_5);
        card5.add(button5);
        
        btnUpdate5.setBounds(150, 390, 90, 30);
        btnUpdate5.setFont(font);
        card5.add(btnUpdate5);
    }
    
    private void initPane() {
        setTitle("图书馆系统");
        setResizable(false);
        setBounds((sc.width - 900) / 2, (sc.height - 500) / 2, 900, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);// 点击栏位置
        tabbedPane.setFont(font);// 左栏字体，字号
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.addChangeListener(this);
        
        reFresh.addActionListener(this);
        btnexit.addActionListener(this);
        this.setJMenuBar(menuBar);
        menuBar.add(reFresh);
        menuBar.add(btnexit);

        getContentPane().add(tabbedPane);
    }
    
    private void init() {
        uId = -1;
        bName = null;
        userService = UserService.getInstance();
        userDao = UserDaoImpl.getInstance();
        bookDao = BookDaoImpl.getInstance();
        bookService = BookService.getInstance();
    }

    public static void mainUi(int id, int defaultCard) {
        AdminUi jTabbedPaneTest = new AdminUi(id, defaultCard);
        jTabbedPaneTest.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnSchU) {
            String id = textId.getText();
            if (id.equals("")) {
                ShowMessageUtil.winMessage("您还没有输入账号！");
            } else {
                int uid = -1;
                try {
                    uid = Integer.parseInt(id);
                } catch (NumberFormatException ee) {
                    ShowMessageUtil.winMessage("请输入正确的账号！");
                    return;
                }
                User user = userDao.queryUser(uid, null);
                if (user == null)
                    ShowMessageUtil.winMessage("账号不存在!");
                else {
                    uId = uid;
                    String str = new String(tableUser);
                    str += "<tr><td>" + user.getId() + "</td><td>"
                            + user.getName() + "</td><td>" + user.getPassword()
                            + "</td><td>" + user.getPoint()
                            + "</td></tr></table></html>";
                    card2.setText(str);
                }
            }
        } else if (e.getSource() == this.btnDelU) {
            if (uId <= 0)
                ShowMessageUtil.winMessage("请先查找一位用户！");
            else {
                if (userDao.delUser(uId)) {
                    ShowMessageUtil.winMessage("已删除该用户！");
                    uId = 0;
                } else {
                    ShowMessageUtil.winMessage("删除用户失败~");
                }
            }
        } else if (e.getSource() == this.newPswd) {
            if (uId <= 0) {
                ShowMessageUtil.winMessage("请先查找一位用户！");
            } else {
                String pswd = new String(textNewPswd.getPassword());
                if (id.equals("") || pswd.equals("")) {
                    ShowMessageUtil.winMessage("您还未填写新密码！");
                } else {
                    if (userDao.updUserPassword(uId, pswd, "User")) {
                        ShowMessageUtil.winMessage("密码修改成功！");
                    } else{
                        ShowMessageUtil.winMessage("密码修改失败~");
                    }
                }
            }
        } else if (e.getSource() == this.btnNewUser) {
            String pswd = new String(textUpswd.getPassword());
            String id = textUid.getText();
            if (id.equals("") || pswd.equals("")) {
                ShowMessageUtil.winMessage("账号、密码不能为空！");
            } else {
                Integer realId = -1;
                try {
                    // String型id转int型id，不可转（非数字字符）时会抛异常
                    realId = Integer.parseInt(id);
                    userService.addUser(realId, this.textUname.getText(), pswd);
                } catch (Exception ex) {
                    // 故需捕获之
                    ShowMessageUtil.winMessage("请输入有效的账号！");
                }
            }
        } else if (e.getSource() == this.btnSchB) {
            String name = textName.getText();
            if (name.equals("")) {
                ShowMessageUtil.winMessage("您还没有输入书名！");
            } else {
                Book book = bookDao.searchBook(name);
                if (book == null || book.getId() <= 0){
                    ShowMessageUtil.winMessage("书籍不存在!");
                } else {
                    bName = book.getName();
                    count_5.setText("" + book.getCount());
                    type_5.setText(book.getType());
                    author_5.setText(book.getAuthor());
                    address_5.setText(book.getAddress());
                }
            }
        } else if (e.getSource() == this.btnDelB) {
            if (this.bName == null) {
                ShowMessageUtil.winMessage("请先查找一本书！");
            } else {
                if (bookDao.delBook(bName)) {
                    ShowMessageUtil.winMessage("成功删除该书！");
                    bName = null;
                } else{
                    ShowMessageUtil.winMessage("馆中没有此书！\n删除失败~");
                }
            }
        } else if (e.getSource() == this.button5) {
            int count;
            try {
                count = Integer.parseInt(count_5.getText());
            } catch (NumberFormatException ee) {
                ShowMessageUtil.winMessage("数量格式填写不正确!");
                return;
            }
            String name = textName.getText();
            String type = type_5.getText();
            String author = author_5.getText();
            String address = address_5.getText();
            if (name.equals("") || type.equals("") || author.equals("")
                    || count_5.getText().equals("") || address.equals("")) {
                ShowMessageUtil.winMessage("书名、类型、数量、作者均不能为空！");
            } else {
                if (bookService.insertBook(name, count, type, author, address)) {
                    ShowMessageUtil.winMessage("添加成功！");
                } else {
                    ShowMessageUtil.winMessage("添加失败！请注意书名须唯一。");
                }
            }
        } else if (e.getSource() == this.btnUpdate5) {
            if (bName == null) {
                ShowMessageUtil.winMessage("请先查找一本书！");
            } else {
                int count;
                try {
                    count = Integer.parseInt(count_5.getText());
                } catch (NumberFormatException ee) {
                    ShowMessageUtil.winMessage("书籍序号格式填写不正确!");
                    return;
                }
                String type = type_5.getText();
                String author = author_5.getText();
                String address = address_5.getText();
                
                if (type.equals("") || author.equals("")
                        || address.equals("")) {
                    ShowMessageUtil.winMessage("信息未填齐全！");
                } else {
                    Book book = new Book(bName, count, type, author, address);
                    if (bookDao.updateBook(book, null)) {
                        ShowMessageUtil.winMessage("书籍信息修改成功！");
                        bName = null;
                    } else{
                        ShowMessageUtil.winMessage("书籍信息修改失败！");
                    }
                }
            }
        } else if (e.getSource() == this.returnBtn) {
            if (bName == null) {
                ShowMessageUtil.winMessage("请先查找一本书！");
            } else {
                String uIdStr = JOptionPane.showInputDialog(new JTextField(),"请输入还书者账号");
                if (uIdStr == null) return;
                Integer realId = -1;
                try {
                    realId = Integer.parseInt(uIdStr);
                } catch (NumberFormatException ex) {
                    ShowMessageUtil.winMessage("账号格式错误！");
                    return;
                }
                if (bookService.returnBook(realId, bName)) {
                    ShowMessageUtil.winMessage("操作成功！");
                } else {
                    ShowMessageUtil.winMessage("操作失败，此书不不存在，或用户不存在！");
                }
            }
        } else if (e.getSource() == this.lendBtn) {
            if (bName == null) {
                ShowMessageUtil.winMessage("请先查找一本书！");
            } else {
                String uIdStr = JOptionPane.showInputDialog(new JTextField(), "请输入借书者账号");
                if (uIdStr == null) return;
                Integer realId = -1;
                try {
                    realId = Integer.parseInt(uIdStr);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    ShowMessageUtil.winMessage("账号格式错误！");
                    return;
                }
                if (bookService.lendBook(realId, bName)) {
                    ShowMessageUtil.winMessage("操作成功！");
                } else {
                    ShowMessageUtil.winMessage("操作失败，此书不可借，或者用户不存在！");
                }
            }
        } else if (e.getSource() == this.reFresh) {// 刷新
            AdminUi.mainUi(aId, UserUi.CARD1);
            this.dispose();
        } else if (e.getSource() == this.btnexit) {// 退出登录
            new Index();
            this.dispose();
        }
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        int selectIndex = tabbedPane.getSelectedIndex();
        if (selectIndex == UserUi.CARD1) {
            initCard1();
        } else if (selectIndex == UserUi.CARD2) {
            initCard2();
        } else if (selectIndex == UserUi.CARD3) {
            initCard3();
        } else if (selectIndex == UserUi.CARD4) {
            initCard4();
        } else if (selectIndex == UserUi.CARD5) {
            initCard5();
        } else {
            System.out.println(selectIndex);
        }
    }
}