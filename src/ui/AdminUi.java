package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import dao.*;
import modle.*;
import service.*;
import util.ShowMessageUtil;

public class AdminUi extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    
    private UserDaoImpl userDao;
    private UserService userService;
    private BookDaoImpl bookDao;
    private BookService bookService;
    private int aId;
    private int uId = -2;
    private String bName = null;

    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Dimension sc = toolkit.getScreenSize();
    private JLabel card0 = new JLabel();
    private JLabel card1 = new JLabel();
    private JLabel card2 = new JLabel();
    private JLabel card3 = new JLabel();
    private JLabel card4 = new JLabel();
    private JLabel card5 = new JLabel();
    private JLabel card6 = new JLabel();
    private JLabel card8 = new JLabel();

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
    private JLabel books = new JLabel();
    private JLabel bNum = new JLabel("操 作：输入书籍序号删除此书");
    private JButton btnDelB = new JButton("删 除");
    private JTextField textBnum = new JTextField();

    private JLabel id8 = new JLabel("编 号");
    private JLabel name8 = new JLabel("书 名");
    private JLabel count8 = new JLabel("数 量");
    private JLabel type8 = new JLabel("类 型");
    private JLabel author8 = new JLabel("作 者");
    private JLabel address8 = new JLabel("藏书位置");
    private JTextField id_8 = new JTextField();
    private JTextField name_8 = new JTextField();
    private JTextField count_8 = new JTextField();
    private JTextField type_8 = new JTextField();
    private JTextField author_8 = new JTextField();
    private JTextField address_8 = new JTextField();
    private JButton button8 = new JButton("添 加");

    private JLabel name6 = new JLabel("您想修改哪本书的信息");
    private JLabel num6 = new JLabel("该书在此系列的序号");
    // private JLabel inorout6 = new JLabel("是否在馆（是/否）");
    private JLabel state6 = new JLabel("是否损坏（是/否）");
    private JLabel lost6 = new JLabel("是否丢失（是/否）");
    private JLabel address6 = new JLabel("藏书位置");
    private JTextField num_6 = new JTextField();
    private JTextField name_6 = new JTextField();
    // private JTextField inorout_6 = new JTextField();
    private JTextField state_6 = new JTextField();
    private JTextField lost_6 = new JTextField();
    private JTextField address_6 = new JTextField();
    private JButton btnUpdateB = new JButton("修 改");

    private String tableUser = new String("<html><style>"
            + "table{margin-top:0px;padding-left:80px;}"
            + "td{width:150px;}</style>"
            + "<table><tr><td>账号</td><td>用户名</td><td>密码</td><td>积分</td></tr>");

    private JLabel allBooks = new JLabel();
    private String tableBook = new String(
            "<html><style>.title{color:red;}td{width:140px;}</style>"
                    + "<table><td>书 名</td><td>类 型</td>"
                    + "<td>作 者</td><td>被借次数</td><td>所在位置</td></tr>");

    private Font font = new Font("楷体", 0, 20);
    private Font font1 = new Font("楷体", 0, 16);

    private JTabbedPane tabbedPane;
    private JButton reFresh = new JButton("刷新");
    private JButton btnexit = new JButton("退出");//
    private JMenuBar menuBar = new JMenuBar();

    public AdminUi(int aId, int defaultCard) {
        userService = UserService.getInstance();
        userDao = UserDaoImpl.getInstance();
        bookDao = BookDaoImpl.getInstance();
        bookService  = BookService.getInstance();
        this.aId = aId;
        setTitle("图书馆系统");
        setResizable(false);
        setBounds((sc.width - 900) / 2, (sc.height - 500) / 2, 900, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);// 点击栏位置
        tabbedPane.setFont(font);// 左栏字体，字号
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); // 此处不清楚//WRAP_TAB_LAYOUT
                                                                      // 。

        reFresh.addActionListener(this);
        btnexit.addActionListener(this);
        this.setJMenuBar(menuBar);
        menuBar.add(reFresh);
        menuBar.add(btnexit);

        getContentPane().add(tabbedPane);

        // ImageIcon image2 = new ImageIcon("E:/image/c1.jpg");

        // card0
        tabbedPane.addTab("欢迎管理员", card0);// 后面将card0设为不可用

        // card1
        card1.setFont(font);
        List<User> list = userDao.queryAllUser();
        String users = new String(tableUser);
        for (int i = 0; i < list.size(); i++) {
            users += "<tr><td>" + list.get(i).getId() + "</td><td>"
                    + list.get(i).getName();
            users += "<td>" + list.get(i).getPassword() + "</td><td>"
                    + list.get(i).getPoint() + "</td></tr>";
        }
        users += "</table></html>";
        // card1.setText(users);//将集合list中信息显示
        tabbedPane.addTab("1.所有用户信息", card1);
        JLabel allUsers = new JLabel();
        allUsers.setText(users);
        allUsers.setFont(font);
        allUsers.setBounds(10, 50, 700, (list.size() + 1) * 35);
        card1.add(allUsers);
        card1.setFont(font);
        card1.setText("");

        // card2
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
        newPswd.addActionListener(this);
        btnSchU.addActionListener(this);
        btnDelU.addActionListener(this);
        tabbedPane.addTab("2.用户管理", card2);

        // card3
        tabbedPane.addTab("3.添加用户", card3);
        newUid.setBounds(150, 80, 80, 30);
        textUid.setBounds(230, 80, 200, 30);
        newUname.setBounds(150, 150, 80, 30);
        textUname.setBounds(230, 150, 200, 30);
        newUpswd.setBounds(150, 220, 80, 30);
        textUpswd.setBounds(230, 220, 200, 30);
        btnNewUser.setBounds(250, 300, 90, 30);
        btnNewUser.addActionListener(this);
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

        // card4
        tabbedPane.addTab("4.查看所有书籍", card4);
        card1.setFont(font);
        List<Book> listB = bookDao.queryAllBooks();
        String books = new String(tableBook);
        int L = listB.size(), i;
        int count = L * 3;
        for (i = 0; i < L; i++) {
            books += "<tr class='title'><td>" + listB.get(i).getName()
                    + "</td><td>" + listB.get(i).getType() + "</td><td>"
                    + listB.get(i).getAuthor() + "</td><td>"
                    + listB.get(i).getDiscount() + "</td><td>"
                    + listB.get(i).getAddress() + "</td></tr>";
        }
        books += "</table></html>";
        allBooks.setText(books);
        allBooks.setBounds(40, 5, 700, count * 28);
        // 数据库里书本count有问题，可能为负值。。导致count为负，无法显示
        allBooks.setFont(font1);
        card4.add(allBooks);
        card4.setFont(font1);
        card4.setText("");

        // card5
        // 查找书籍，删除书籍，修改书籍信息（内容太多的话考虑放在另一个界面）
        tabbedPane.addTab("5.书籍管理", card5);
        bookname.setBounds(120, 20, 80, 30);
        textName.setBounds(200, 20, 200, 30);
        btnSchB.setBounds(420, 20, 100, 30);
        bNum.setBounds(120, 400, 280, 30);
        textBnum.setBounds(400, 400, 30, 30);
        btnDelB.setBounds(440, 400, 80, 30);
        btnDelB.addActionListener(this);
        btnSchB.addActionListener(this);
        bookname.setFont(font);
        textName.setFont(font);
        btnSchB.setFont(font);
        bNum.setFont(font);
        textBnum.setFont(font);
        btnDelB.setFont(font1);
        card5.add(bookname);
        card5.add(textName);
        card5.add(btnSchB);
        card5.add(bNum);
        card5.add(textBnum);
        card5.add(btnDelB);

        // card6
        tabbedPane.addTab("6.添加书籍", card8);
        id8.setBounds(150, 70, 80, 30);
        id_8.setBounds(230, 70, 200, 30);
        name8.setBounds(150, 120, 80, 30);
        name_8.setBounds(230, 120, 200, 30);
        count8.setBounds(150, 170, 80, 30);
        count_8.setBounds(230, 170, 200, 30);
        type8.setBounds(150, 220, 80, 30);
        type_8.setBounds(230, 220, 200, 30);
        author8.setBounds(150, 270, 80, 30);
        author_8.setBounds(230, 270, 200, 30);
        address8.setBounds(150, 320, 80, 30);
        address_8.setBounds(230, 320, 200, 30);
        button8.setBounds(250, 390, 90, 30);
        button8.addActionListener(this);
        id8.setFont(font);
        id_8.setFont(font);
        name8.setFont(font);
        name_8.setFont(font);
        count8.setFont(font);
        count_8.setFont(font);
        type8.setFont(font);
        type_8.setFont(font);
        author8.setFont(font);
        author_8.setFont(font);
        address8.setFont(font);
        address_8.setFont(font);
        button8.setFont(font);
        card8.add(id8);
        card8.add(id_8);
        card8.add(name8);
        card8.add(name_8);
        card8.add(count8);
        card8.add(count_8);
        card8.add(type_8);
        card8.add(type8);
        card8.add(author8);
        card8.add(author_8);
        card8.add(address8);
        card8.add(address_8);
        card8.add(button8);

        // card7
        tabbedPane.addTab("7.修改书籍信息", card6);
        name6.setBounds(150, 80, 200, 30);
        name_6.setBounds(350, 80, 200, 30);
        num6.setBounds(150, 130, 200, 30);
        num_6.setBounds(350, 130, 200, 30);
        // inorout6.setBounds(150, 130, 200, 30);
        // inorout_6.setBounds(350, 130, 200, 30);
        state6.setBounds(150, 180, 200, 30);
        state_6.setBounds(350, 180, 200, 30);
        lost6.setBounds(150, 230, 200, 30);
        lost_6.setBounds(350, 230, 200, 30);
        address6.setBounds(150, 280, 200, 30);
        address_6.setBounds(350, 280, 200, 30);
        btnUpdateB.setBounds(290, 380, 90, 30);
        btnUpdateB.addActionListener(this);
        name6.setFont(font);
        name_6.setFont(font);
        num6.setFont(font);
        num_6.setFont(font);
        // inorout6.setFont(font);
        // inorout_6.setFont(font);
        state6.setFont(font);
        state_6.setFont(font);
        lost6.setFont(font);
        lost_6.setFont(font);
        address6.setFont(font);
        address_6.setFont(font);
        btnUpdateB.setFont(font);
        card6.add(name6);
        card6.add(name_6);
        card6.add(num6);
        card6.add(num_6);
        // card6.add(inorout6);
        // card6.add(inorout_6);
        card6.add(state6);
        card6.add(state_6);
        card6.add(lost6);
        card6.add(lost_6);
        card6.add(address6);
        card6.add(address_6);
        card6.add(btnUpdateB);

        tabbedPane.setSelectedIndex(defaultCard); // 设置默认选中的
        tabbedPane.setEnabledAt(0, false); // 设置索引0的面板不可用
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
            if (uId == -2)
                ShowMessageUtil.winMessage("请先查找一位用户！");
            else {
                if (userDao.delUser(uId)) {
                    ShowMessageUtil.winMessage("已删除该用户！");
                    AdminUi.mainUi(aId, 1);
                    this.dispose();
                } else
                    ShowMessageUtil.winMessage("删除用户失败~");
            }
        } else if (e.getSource() == this.newPswd) {
            if (uId == -2)
                ShowMessageUtil.winMessage("请先查找一位用户！");
            else {
                String pswd = new String(textNewPswd.getPassword());
                if (id.equals("") || pswd.equals("")) {
                    ShowMessageUtil.winMessage("您还未填写新密码！");
                } else {
                    if (userDao.updUserPassword(uId, pswd, "User")) {
                        ShowMessageUtil.winMessage("密码修改成功！");
                        AdminUi.mainUi(aId, 1);
                        this.dispose();
                    } else
                        ShowMessageUtil.winMessage("密码修改失败~");
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
                    userService.addUser(realId, this.textName.getText(), pswd);
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
                if (book == null)
                    ShowMessageUtil.winMessage("书籍不存在!");
                else {
                    this.bName = book.getName();
                    String str = new String(
                            "<html><style>td{width:100px;}</style>"
                                    + "<table><tr><td>书 名</td><td>类 型</td><td>作 者</td><td>借出次数</td><td>位 置</td></tr><tr>"
                                    + "<td>"
                                    + book.getName()
                                    + "</td><td>"
                                    + book.getType()
                                    + "</td><td>"
                                    + book.getAuthor()
                                    + "</td><td>"
                                    + book.getDiscount()
                                    + "</td><td>"
                                    + book.getAddress()
                                    + "</td></tr></table></html>");
                    // System.out.println(str);乖，摸摸头
                    books.setBounds(120, 50, 510, (3 + 0) * 31);
                    books.setText(str);
                    books.setFont(font1);
                    card5.add(books);
                    card5.setText("");// 发现必须要这两个
                    card5.setFont(font1);
                }
            }
        } else if (e.getSource() == this.btnDelB) {
            if (this.bName == null) {
                ShowMessageUtil.winMessage("请先查找一本书！");
            } else if (this.textBnum.getText().equals(""))
                ShowMessageUtil.winMessage("请输入书籍序号！");
            else {
                int num;
                try {
                    num = Integer.parseInt(this.textBnum.getText());
                } catch (NumberFormatException ee) {
                    ShowMessageUtil.winMessage("请输入正确的序号！");
                    return;
                }
                Book bk = bookDao.searchBook(bName);
                // TODO
                if (bookService.updateBook(bk)) {
                    ShowMessageUtil.winMessage("成功删除该书！");
                    AdminUi.mainUi(aId, 4);
                    this.dispose();
                } else
                    ShowMessageUtil.winMessage("馆中没有此书！\n删除失败~");
            }
        } else if (e.getSource() == this.button8) {
            int count, id;
            try {
                count = Integer.parseInt(count_8.getText());
                id = Integer.parseInt(id_8.getText());
            } catch (NumberFormatException ee) {
                ShowMessageUtil.winMessage("数量或编号格式填写不正确!");
                return;
            }
            String name = name_8.getText();
            String type = type_8.getText();
            String author = author_8.getText();
            String address = address_8.getText();
            if (name.equals("") || type.equals("") || author.equals("")
                    || count_8.getText().equals("") || address.equals("")
                    || id_8.getText().equals("")) {
                ShowMessageUtil.winMessage("书名、类型、数量、作者均不能为空！");
            } else {
                bookService.addBooks(id, name, count, type, author, address);
                AdminUi.mainUi(aId, 6);
                this.dispose();
            }
        } else if (e.getSource() == this.btnUpdateB) {
            int count;
            try {
                count = Integer.parseInt(num_6.getText());
            } catch (NumberFormatException ee) {
                ShowMessageUtil.winMessage("书籍序号格式填写不正确!");
                return;
            }
            String name = name_6.getText();
            String type = state_6.getText();
            String author = lost_6.getText();
            String address = address_6.getText();
            if (name.equals("") || type.equals("") || author.equals("")
                    || address.equals("")) {
                ShowMessageUtil.winMessage("信息未填齐全！");
            } else {
                Book bk = bookDao.searchBook(name);
                // TODO
                if (bookService.updateBook(bk)) {
                    ShowMessageUtil.winMessage("书籍信息修改成功！");
                    AdminUi.mainUi(aId, 5);
                    this.dispose();
                } else
                    ShowMessageUtil.winMessage("书籍信息修改失败！");
            }
        } else if (e.getSource() == this.reFresh) {// 刷新
            AdminUi.mainUi(aId, 1);
            this.dispose();
        } else if (e.getSource() == this.btnexit) {// 退出登录
            new Index();
            this.dispose();
        }
    }
}