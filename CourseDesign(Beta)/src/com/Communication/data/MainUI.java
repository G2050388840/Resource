package com.Communication.data;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame
{
    private JButton btnChoose;
    private JButton btnEnter;
    private JButton btnDelete;
    private JLabel lbText;
    public MainUI()
    {
        this.setUndecorated(true);
        Dimension size = new Dimension(70,60);
        this.setLayout(new FlowLayout());

        ImageIcon choose = new ImageIcon("D:\\BaiduNetdiskDownload\\CourseDesign(Beta)\\测试申请.png");
        ImageIcon search = new ImageIcon("D:\\BaiduNetdiskDownload\\CourseDesign(Beta)\\场景管理.png");
        ImageIcon delete = new ImageIcon("D:\\BaiduNetdiskDownload\\CourseDesign(Beta)\\编辑.png");

        Image img1 = choose.getImage();
        Image img2 = search.getImage();
        Image img3 = delete.getImage();

        img1 = img1.getScaledInstance(75,60,Image.SCALE_DEFAULT);
        img2 = img2.getScaledInstance(75,60,Image.SCALE_DEFAULT);
        img3 = img3.getScaledInstance(75,60,Image.SCALE_DEFAULT);

        choose.setImage(img1);
        search.setImage(img2);
        delete.setImage(img3);

        lbText = new JLabel("学生选课系统");
        this.add(lbText);

        btnChoose = new JButton("选课",choose);
        btnChoose.setPreferredSize(size);
        btnChoose.setContentAreaFilled(false);
        btnChoose.setFocusPainted(false);
        btnChoose.setBorderPainted(false);
        this.add(btnChoose);

        btnEnter = new JButton("查询",search);
        btnEnter.setPreferredSize(size);
        btnEnter.setContentAreaFilled(false);
        btnEnter.setFocusPainted(false);
        btnEnter.setBorderPainted(false);
        this.add(btnEnter);

        btnDelete = new JButton("退选",delete);
        btnDelete.setPreferredSize(size);
        btnDelete.setContentAreaFilled(false);
        btnDelete.setFocusPainted(false);
        btnDelete.setBorderPainted(false);
        this.add(btnDelete);

        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(100,300);
        this.setResizable(false);
    }

    public static void main(String[] args)
    {
        MainUI mainUI = new MainUI();
    }
}

