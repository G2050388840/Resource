package com.Communication.data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

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

        ImageIcon bg = new ImageIcon(".\\image\\1.jpg");
        Image img = bg.getImage();
        this.setLayout(new GridLayout(5,1));
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();
        JPanel jp4 = new JPanel();
        JPanel jp5 = new JPanel();
        jp1.setLayout(new GridLayout(2,1));
        jp2.setLayout(new GridLayout(2,1));
        jp3.setLayout(new GridLayout(2,1));
        jp4.setLayout(new GridLayout(2,1));
        jp5.setLayout(new GridLayout(2,1));

        lblName = new JLabel("用户名:");
        lblPassword = new JLabel("密    码:");

        textName = new JTextField(15);
        textName.setSize(new Dimension(180,10));
        textName.setOpaque(false);
        jp1.add(lblName);
        jp1.add(textName);

        textPassword = new JPasswordField(15);
        textPassword.setSize(new Dimension(180,10));
        textPassword.setOpaque(false);
        jp2.add(lblPassword);
        jp2.add(textPassword);

        btnLogin = new JButton("登录");
        btnLogin.setContentAreaFilled(false);
        btnLogin.setSize(90,20);
        jp4.add(btnLogin);

        btnRegister = new JButton("注册");
        btnRegister.setContentAreaFilled(false);
        btnRegister.setSize(90,20);
        jp5.add(btnRegister);

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
                    stm.setString(2, textPassword.getText());
                    rs = stm.executeQuery();
                    if (textName.getText().length() == 0 || textPassword.getText().length() == 0 )
                    {
                        JOptionPane.showMessageDialog(null,"Please input the username!");
                        dispose();
                        new LoginUI();
                    }
                    else if(rs.next())
                    {
                        new MainUI(textName.getText(),textPassword.getText());
                        conn.close();
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Please input the right username/userpassword! ");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnRegister.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                new Register();
            }
        });

        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);
        this.add(jp5);
        this.setVisible(true);
        this.setIconImage(img);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,200);
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
            int width = 520;
            int height = 240;
            this.setLayout(new GridLayout(4,1));
            JPanel jp1 = new JPanel();
            JPanel jp2 = new JPanel();
            JPanel jp3 = new JPanel();
            JPanel jp4 = new JPanel();
            jp1.setLayout(new GridLayout(2,2));
            jp2.setLayout(new GridLayout(2,2));
            jp3.setLayout(new GridLayout(2,2));
            lblName = new JLabel("用户名:");
            lblPassword = new JLabel("密    码:");
            lblRePassword = new JLabel("再次输入:");

            textName = new JTextField(15);
            textName.setOpaque(false);
            textName.setSize(new Dimension(300,10));
            jp1.add(lblName);
            jp1.add(textName);

            textPassword = new JPasswordField(15);
            textPassword.setOpaque(false);
            textPassword.setSize(new Dimension(300,10));
            jp2.add(lblPassword);
            jp2.add(textPassword);

            RePassword = new JPasswordField(15);
            RePassword.setSize(new Dimension(300,10));
            RePassword.setOpaque(false);
            jp3.add(lblRePassword);
            jp3.add(RePassword);

            ImageIcon bg = new ImageIcon(".\\image\\1.jpg");
            Image img = bg.getImage();
            img = img.getScaledInstance(width,height,Image.SCALE_DEFAULT);
            bg.setImage(img);
            JLabel imlable = new JLabel(bg);
            imlable.setBounds(0,0,bg.getIconWidth(),bg.getIconHeight());
            this.getLayeredPane().add(imlable,new Integer(Integer.MIN_VALUE));
            JPanel jp = (JPanel)this.getContentPane();
            jp.setOpaque(false);
            JPanel panel = new JPanel();
            panel.setOpaque(false);
            jp4.add(panel);

            btnRe = new JButton("注册");
            btnRe.setContentAreaFilled(false);
            btnRe.setSize(new Dimension(90,20));
            jp4.add(btnRe);
            this.add(jp1);
            this.add(jp2);
            this.add(jp3);
            this.add(jp4);

            btnRe.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    Connection conn = null;
                    PreparedStatement stm = null;
                    String sql = "insert into login(username,userpwd)" + "values(?,?)";
                    try
                    {
                        conn = DBManager.getConnection();
                        stm = conn.prepareStatement(sql);
                        stm.setString(1, textName.getText());
                        stm.setString(2, textPassword.getText());
                        if(textPassword.getText() != RePassword.getText())
                        {
                            JOptionPane.showMessageDialog(null,"Please input the same password!");
                            dispose();
                            new Register();
                        }
                        else
                        {
                            stm.execute();
                            dispose();
                            new LoginUI();
                        }
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
            this.setSize(width,height);
            this.setResizable(false);
            this.setVisible(true);
        }
    }
}

