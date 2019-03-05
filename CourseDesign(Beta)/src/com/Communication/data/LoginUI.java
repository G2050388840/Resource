package com.Communication.data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Communication.data.DBManager;
import com.Communication.data.User;

public class LoginUI extends JFrame
{
    private JLabel lblName;
    private JLabel lblPassword;
    private JTextField textName;
    private JPasswordField textPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    public LoginUI()
    {
        this.setUndecorated(true);
        this.setVisible(true);
        this.setLayout(new FlowLayout());
        lblName = new JLabel("用户名:");
        lblPassword = new JLabel("密    码:");
        textName = new JTextField(15);
        textName.setOpaque(false);
        this.add(lblName);
        this.add(textName);

        textPassword = new JPasswordField(15);
        textPassword.setOpaque(false);
        this.add(lblPassword);
        this.add(textPassword);

        btnLogin = new JButton("登录");
        btnLogin.setContentAreaFilled(false);
        this.add(btnLogin);

        ImageIcon bg = new ImageIcon("C:\\Users\\Administrator\\Desktop\\CourseDesign(Beta)\\123.jpg");

        Image im = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Administrator\\Desktop\\CourseDesign(Beta)\\123.jpg");
        JLabel imlable = new JLabel(bg);
        imlable.setBounds(0,0,bg.getIconWidth(),bg.getIconHeight());
        this.getLayeredPane().add(imlable,new Integer(Integer.MIN_VALUE));
        JPanel jp = (JPanel)this.getContentPane();
        jp.setOpaque(false);
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        this.add(panel);

        btnRegister = new JButton("注册");
        btnRegister.setContentAreaFilled(false);
        this.add(btnRegister);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn = null;
                PreparedStatement stm = null;
                ResultSet rs = null;
                String sql = "select * from login where username=? and userpwd=?";
                try {
                    conn = DBManager.getConnection();
                    stm = conn.prepareStatement(sql);
                    stm.setString(1, textName.getText());
                    stm.setString(2,textPassword.getText());
                    rs = stm.executeQuery();
                    while (rs.next()) {
                        new MainUI();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Register();
            }
        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(260,90);
        this.setResizable(false);

    }

    public static void main(String[] args)
    {

        LoginUI login = new LoginUI();
    }

    public class Register extends JFrame
    {
        private JLabel lblName;
        private JLabel lblPassword;
        private JLabel lblRePassword;
        private JTextField textName;
        private JPasswordField textPassword;
        private JPasswordField RePassword;
        private JButton btnRe;
        Register()
        {
            this.setUndecorated(true);
            this.setLayout(new FlowLayout());
            lblName = new JLabel("用户名:");
            lblPassword = new JLabel("密    码:");
            lblRePassword = new JLabel("再次输入:");

            textName = new JTextField(15);
            textName.setOpaque(false);
            this.add(lblName);
            this.add(textName);

            textPassword = new JPasswordField(15);
            textPassword.setOpaque(false);
            this.add(lblPassword);
            this.add(textPassword);

            RePassword = new JPasswordField(15);
            RePassword.setOpaque(false);
            this.add(lblRePassword);
            this.add(RePassword);

            ImageIcon bg = new ImageIcon("C:\\Users\\Administrator\\Desktop\\CourseDesign(Beta)\\123.jpg");
            Image im = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Administrator\\Desktop\\CourseDesign(Beta)\\123.jpg");
            JLabel imlable = new JLabel(bg);
            imlable.setBounds(0,0,bg.getIconWidth(),bg.getIconHeight());
            this.getLayeredPane().add(imlable,new Integer(Integer.MIN_VALUE));
            JPanel jp = (JPanel)this.getContentPane();
            jp.setOpaque(false);
            JPanel panel = new JPanel();
            panel.setOpaque(false);
            this.add(panel);

            btnRe = new JButton("注册");
            btnRe.setContentAreaFilled(false);
            this.add(btnRe);

            btnRe.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Connection conn = null;
                    PreparedStatement stm = null;
                    String sql = "insert into login(username,userpwd)" + "values(?,?)";
                    try
                    {
                        conn = DBManager.getConnection();
                        stm = conn.prepareStatement(sql);
                        stm.setString(1, textName.getText());
                        stm.setString(2, textPassword.getText());
                        stm.execute();
                    }
                    catch(SQLException e1)
                    {
                        e1.printStackTrace();
                    }
                    finally
                    {
                        try
                        {
                            stm.close();
                            conn.close();
                        }
                        catch (SQLException e2)
                        {
                            e2.printStackTrace();
                        }
                    }
                    System.out.println(textName.getText());
                    System.out.println(textPassword.getText());
                }
            });

            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(260,120);
            this.setResizable(false);
            this.setVisible(true);
        }
    }
}

