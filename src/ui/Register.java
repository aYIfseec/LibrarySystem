package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import service.UserService;
import util.ShowMessageUtil;

public class Register implements ActionListener {
    private UserService userService;
    
    private JFrame jf = new JFrame("图书馆系统");
    private Container con = jf.getContentPane();// 获得面板

    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Dimension sc = toolkit.getScreenSize();// 获得屏幕尺寸
    private JLabel id = new JLabel("账    号");
    private JLabel name = new JLabel("用 户 名");
    private JLabel pass = new JLabel("密    码");
    private JLabel pass2 = new JLabel("确认密码");
    private JTextField textId = new JTextField();
    private JTextField textName = new JTextField();
    private JLabel title = new JLabel("用户注册");
    private JPasswordField textPs = new JPasswordField(); // 密码框
    private JPasswordField textPs2 = new JPasswordField();
    
    // 按钮
    private JButton registerBtn = new JButton("确定");
    private JButton backLoginBtn = new JButton("返回");
    
    // 字体，样式（粗体，斜体），大小
    private Font font = new Font("楷体", 1, 28);
    private Font font1 = new Font("楷体", 0, 20);
    private Font font2 = new Font("宋体", 0, 18);

    public Register() {
        userService = UserService.getInstance();
        
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

        id.setBounds(325, 140, 95, 30);
        id.setFont(font1);
        id.setForeground(Color.red);

        name.setBounds(325, 190, 95, 30);
        name.setFont(font1);
        name.setForeground(Color.black);

        pass.setBounds(325, 240, 95, 30);// 密码的位置大小
        pass.setForeground(Color.red);
        pass.setFont(font1);

        pass2.setBounds(325, 290, 95, 30);
        pass2.setForeground(Color.red);
        pass2.setFont(font1);

        textId.setBounds(430, 143, 170, 25);
        textId.setFont(font1);
        textId.setBorder(null);// 边框

        textName.setBounds(430, 193, 170, 25);
        textName.setFont(font1);
        textName.setBorder(null);// 边框

        textPs.setBounds(430, 243, 170, 25);
        textPs.setFont(font1);
        textPs.setBorder(null);
        // 可以将密码显示为* ；默认为· 但默认又对其设置了字体时会乱码
        textPs.setEchoChar('*');
        
        textPs2.setBounds(430, 293, 170, 25);
        textPs2.setFont(font1);
        textPs2.setBorder(null);
        textPs2.setEchoChar('*');

        registerBtn.setBounds(350, 340, 90, 25);
        registerBtn.setFont(font2);
        registerBtn.addActionListener(this);

        backLoginBtn.setBounds(490, 340, 90, 25);
        backLoginBtn.setFont(font2);
        backLoginBtn.addActionListener(this);
        ImageIcon bgim = new ImageIcon("resources/background.jpg");// 背景图案
        JLabel bg = new JLabel(bgim);
        Container laycon = jf.getLayeredPane();
        bg.setSize(jf.getSize().width, jf.getSize().height);
        bgim.setImage(bgim.getImage().getScaledInstance(bg.getSize().width,
                bg.getSize().height, Image.SCALE_DEFAULT));
        laycon.add(bg, new Integer(Integer.MIN_VALUE));

        con.add(title);
        con.add(id);
        con.add(name);
        con.add(pass);
        con.add(pass2);
        con.add(textId);
        con.add(textName);
        con.add(textPs);
        con.add(textPs2);
        con.add(registerBtn);
        con.add(backLoginBtn);
        con.add(bg);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.registerBtn) {
            String pswd = new String(textPs.getPassword());
            String pswd2 = new String(textPs2.getPassword());
            String id = textId.getText();
            if (id.equals("") || pswd.equals("") || pswd2.equals("")) {
                ShowMessageUtil.winMessage("账号、密码不能为空！");
            } else {
                if (pswd.equals(pswd2)) {
                    Integer realId = -1;
                    try {
                        // String型id转int型id，不可转（非数字字符）时会抛异常
                        realId = Integer.parseInt(id);
                        boolean res = userService.addUser(realId, this.textName.getText(), pswd);
                        if (res) {
                            // 注册成功则跳转到登录界面
                            new Login();
                            this.jf.dispose();
                        }
                    } catch (Exception ex) {
                        // 故需捕获之
                        ShowMessageUtil.winMessage("请输入有效的账号！");
                    }
                } else {
                    ShowMessageUtil.winMessage("两次输入的密码不同！");
                }
            }
        } else if (e.getSource() == this.backLoginBtn) {
            new Login();
            this.jf.dispose();
        }
    }
}