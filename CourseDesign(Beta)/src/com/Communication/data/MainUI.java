package com.Communication.data;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame
{
    private JButton btnChoose;
    private JButton btnEnter;
    private JButton btnDelete;
    private JLabel lbText;
    private JLabel lb1;
    private JLabel lb2;
    private JLabel lb3;
    public MainUI()
    {
        this.setUndecorated(true);
        Dimension size = new Dimension(68,70);
        this.setLayout(new FlowLayout());

        ImageIcon choose = new ImageIcon("C:\\Users\\亦灬念\\Desktop\\Resource-master\\CourseDesign(Beta)\\测试申请.png");
        ImageIcon search = new ImageIcon("C:\\Users\\亦灬念\\Desktop\\Resource-master\\CourseDesign(Beta)\\场景管理.png");
        ImageIcon delete = new ImageIcon("C:\\Users\\亦灬念\\Desktop\\Resource-master\\CourseDesign(Beta)\\编辑.png");

        Image img1 = choose.getImage();
        Image img2 = search.getImage();
        Image img3 = delete.getImage();

        img1 = img1.getScaledInstance(70,70,Image.SCALE_DEFAULT);
        img2 = img2.getScaledInstance(70,70,Image.SCALE_DEFAULT);
        img3 = img3.getScaledInstance(70,70,Image.SCALE_DEFAULT);

        choose.setImage(img1);
        search.setImage(img2);
        delete.setImage(img3);

        lbText = new JLabel("学生选课系统");
        lbText.setPreferredSize(new Dimension(80,30));
        this.add(lbText);

        lb1 = new JLabel("选课");
        lb2 = new JLabel("查询");
        lb3 = new JLabel("退选");

        btnChoose = new JButton("选课",choose);
        btnChoose.setPreferredSize(size);
        btnChoose.setContentAreaFilled(false);
        btnChoose.setFocusPainted(false);
        btnChoose.setBorderPainted(false);
        this.add(btnChoose);
        this.add(lb1);

        btnEnter = new JButton("查询",search);
        btnEnter.setPreferredSize(size);
        btnEnter.setContentAreaFilled(false);
        btnEnter.setFocusPainted(false);
        btnEnter.setBorderPainted(false);
        this.add(btnEnter);
        this.add(lb2);

        btnDelete = new JButton("退选",delete);
        btnDelete.setPreferredSize(size);
        btnDelete.setContentAreaFilled(false);
        btnDelete.setFocusPainted(false);
        btnDelete.setBorderPainted(false);
        this.add(btnDelete);
        this.add(lb3);

        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(100,340);
        this.setResizable(false);
    }

    public static void main(String[] args)
    {
        MainUI mainUI = new MainUI();
    }
}

