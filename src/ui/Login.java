package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import modle.Admin;
import modle.User;

import service.LoginService;
import util.ShowMessageUtil;

/*
 * 实现jdk中的ActionListener接口，
 * 实现actionPerformed方法 故可以实现对按钮点击事件的监听等功能
 */
public class Login implements ActionListener {

    private LoginService loginService;

    private JFrame jf = new JFrame("图书馆系统");
    private Container con = jf.getContentPane(); // 获得面板
    private Toolkit toolkit = Toolkit.getDefaultToolkit(); // 获得系统默认工具类
    private Dimension sc = toolkit.getScreenSize(); // 获得屏幕尺寸
    private JLabel title = new JLabel("欢迎使用");
    private JLabel name1 = new JLabel("账 号");
    private JLabel pass1 = new JLabel("密 码");
    private JTextField textName = new JTextField(); // 普通文本框
    private JPasswordField textPs = new JPasswordField(); // 密码框

    // 单选
    private JRadioButton choice1 = new JRadioButton("用户");
    private JRadioButton choice2 = new JRadioButton("管理员");
    // private JRadioButton choice3 = new JRadioButton("超级用户");

    private JLabel code1 = new JLabel("验证码");
    private JTextField textCode = new JTextField();
    private JLabel code2 = new JLabel();

    private String realCode;

    // 按钮
    private JButton button1 = new JButton("注册");
    private JButton button2 = new JButton("登录");

    private Font font = new Font("楷体", 1, 28);
    private Font font1 = new Font("楷体", 0, 20);
    private Font font2 = new Font("楷体", 0, 18); // 字体，样式（粗体，斜体），大小

    private ButtonGroup buttongroup = new ButtonGroup();

    public Login() {
        loginService = LoginService.getInstance();
        
        con.setLayout(null);
        jf.setSize(900, 500);
        jf.setLocation((sc.width - 900) / 2, (sc.height - 500) / 2);

        jf.setResizable(false);// 窗口大小不可变
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        con.setVisible(true);

        title.setBounds(395, 10, 170, 30);
        title.setFont(font);
        title.setForeground(Color.black);

        name1.setBounds(350, 140, 50, 30);// 账号的位置大小
        name1.setFont(font1);// 字体
        name1.setForeground(Color.black);// name1字的颜色

        pass1.setBounds(350, 190, 50, 30);// 密码的位置大小
        pass1.setForeground(Color.black);
        pass1.setFont(font1);

        textName.setBounds(410, 143, 160, 25);
        textName.setBorder(null);// 边框
        textName.setFont(font1);

        textPs.setBounds(410, 193, 160, 25);
        textPs.setBorder(null);
        textPs.setEchoChar('*');// 可以将密码显示为* ；默认为·
        textPs.setFont(font1);

        choice1.setBounds(350, 290, 140, 25);
        choice1.setSelected(true);// 默认普通用户登录
        choice2.setBounds(490, 290, 80, 25);
        // choice3.setBounds(480, 240, 90, 25);

        code1.setBounds(350, 240, 60, 25);
        code1.setFont(font1);
        code1.setForeground(Color.black);
        textCode.setBounds(420, 240, 80, 25);
        textCode.setBorder(null);
        textCode.setFont(font1);
        code2.setBounds(510, 240, 70, 25);
        code2.setFont(font1);
        code2.setText(getCode()); // 设置验证码
        code2.setForeground(Color.black);

        button1.setBounds(350, 340, 90, 25);
        button1.setFont(font2);
        button1.addActionListener(this);

        button2.setBounds(480, 340, 90, 25);
        button2.setFont(font2);
        button2.addActionListener(this);
        // 背景图，在项目下的resources文件夹
        ImageIcon bgImg = new ImageIcon("resources/background.jpg");
        JLabel bg = new JLabel(bgImg);
        Container laycon = jf.getLayeredPane();
        bg.setSize(jf.getSize().width, jf.getSize().height);
        bgImg.setImage(bgImg.getImage().getScaledInstance(bg.getSize().width,
                bg.getSize().height, Image.SCALE_DEFAULT));
        laycon.add(bg, new Integer(Integer.MIN_VALUE));
        con.add(title);
        con.add(name1);
        con.add(pass1);
        con.add(textName);
        con.add(textPs);
        con.add(choice1);
        con.add(choice2);
        // con.add(choice3);
        // 弄成单选组
        buttongroup.add(choice1);
        buttongroup.add(choice2);
        // buttongroup.add(choice3);
        con.add(code1);
        con.add(code2);
        con.add(textCode);
        con.add(button1);
        con.add(button2);
        // con.setBackground(Color.getHSBColor(1, 0, 1));
        con.add(bg);
    }

    // 生产验证码
    private String getCode() {
        StringBuilder code = new StringBuilder();
        // 以当前系统毫秒时间为种子，new一个随机数类Random
        Random rand = new Random(System.currentTimeMillis());

        // 随机产生四个      0-9数字
        for (int i = 0; i < 4; i++) {
            int temp = rand.nextInt();
            if (temp < 0) temp = -temp;
            code.append(temp % 10);
        }
        // 保存验证码在系统中
        realCode = code.toString();
        return realCode;
    }

    public void actionPerformed(ActionEvent ac) {
        // 判断系统监听到的事件ac 的来源，来源不同，要干的事也不同
        if (ac.getSource() == this.button2) {

            // 获得输入的 账号、密码
            String id = textName.getText();
            String pswd = new String(textPs.getPassword());

            if (id.equals("") || pswd.equals("")) {
                ShowMessageUtil.winMessage("账号、密码不能为空！");
            } else {
                // 获得用户输入的验证码，进行比对
                String code = textCode.getText();
                if (realCode.equals(code)) {

                    // 获取 选择的用户类型
                    int choice = 2;
                    if (choice1.isSelected()) {
                        choice = 1;
                    }

                    Integer realId = -1;
                    try {
                        // String型id转int型id，不可转（非数字字符）时会抛异常
                        realId = Integer.parseInt(id);
                        // 调用登录方法
                        login(choice, realId, pswd);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        // 故需捕获之
                        ShowMessageUtil.winMessage("请输入有效的账号！");
                    }
                    
                } else {
                    ShowMessageUtil.winMessage("验证码不正确！");
                }
            }
        } else if (ac.getSource() == this.button1) {
            new Register();
            this.jf.dispose();// 点击注册按钮时,new一个Register的frame，原先frame销毁
        }
    }
    
    
    private void login(int choice, Integer realId, String pswd) {
        boolean res = false;
        User user = null;
        Admin admin = null;
        
        if (choice == 1) {
            user = loginService.userLogin(realId, pswd);
            if (user != null) {
                res = true;
                UserUi.mainUi(realId, UserUi.CARD1);
            }
        } else {
            admin = loginService.adminLogin(realId, pswd);
            if (admin != null) {
                res = true;
                AdminUi.mainUi(realId, UserUi.CARD1);
            }
        }
        
        // 登录成功，销毁登录窗口
        if (res) Index.getLogin().jf.dispose();
        else ShowMessageUtil.winMessage("账号或密码错误！");
    }
}
