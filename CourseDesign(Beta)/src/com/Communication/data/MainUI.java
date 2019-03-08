package com.Communication.data;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import com.Communication.data.User;
import com.Communication.data.DBManager;


public class MainUI extends JFrame implements ActionListener
{
    private JTextField jtfSearch = new JTextField(11);
    private String[] columnNames = new String[]{"课程ID", "课程名", "学时", "学分", "学期", "性质"};
    private DefaultTableModel dtmSearch = new DefaultTableModel(columnNames, 27);
    private JTable jtSearch = new JTable(dtmSearch);
    private JScrollPane jspSearch = new JScrollPane(jtSearch);
    private JComboBox jcbSearch = new JComboBox();
    private JTextField jtfSelectedCourse = new JTextField(10);
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private static String name;
    private static String pwd;

    //在构造方法中添加两个参数。以便在"提交"时，将学生的身份连同所选的课程，一同记录在学生选课表中。
    public MainUI(String name, String pwd)
    {
        createSearchCourse();
        this.name = name;
        this.pwd = pwd;
    }

    //根据用户的事件，做出相应的反映
    public void actionPerformed(ActionEvent e)
    {
        String str = e.getActionCommand();

        //清空结果显示区中的内容，如果有的话。
        if ("查询".trim().equals(str))
        {
            int k = 1;
            while(k < 10)
            {
                for (int i = 1; i < 7; i++)
                {
                    jtSearch.setValueAt(null, k - 1, i - 1);
                }
                k++;
            }

            //调用下面的这个方法，在数据库中进行查找，并将结果显示在表格中。
            searchAvailableCourse();
        }
        else if ("提交".equals(str))
        {
            //processBeforeCommit()对用户选课操作进行有效性检验；
            //剔除无效操作：如输入无效的课程号，或已经选择了某一课程，已经选满的6学分等各种情况
            boolean effect = processBeforeCommit();

            //如果课程存在，且该学生具有选择该课程的资格，即effect为true,进入正式提交程序(tryCommit())
            if (effect == true)
            {
                tryCommit();
            }
        }
    }

    //对用户选课操作进行有效性检验；
    public boolean processBeforeCommit()
    {
        //清空原结果显示区中的内容，如果有的话。
        int k = 1;
        while (k < 10)
        {
            for (int i = 1; i < 7; i++)
            {
                jtSearch.setValueAt(null, k - 1, i - 1);
            }
            k++;
        }

        //取得用户输入的课程号
        String userInput = jtfSelectedCourse.getText().toString().trim().toLowerCase();

        //无效操作1：在数据库中的coursexx表中查询该课程号。如果不存在该课程，给出提示。
        String sql = "select cno from coursexx where cno=? ";
        boolean flagCourseExist = false;
        try
        {
            ps = con.prepareStatement(sql);
            ps.setString(1, userInput);
            rs = ps.executeQuery();
            flagCourseExist = rs.next();
        }
        catch (Exception eC)
        {
            eC.printStackTrace();
        }
        if (!flagCourseExist)
        {
            JOptionPane.showMessageDialog(null, "该课程不存在，请重新输入");
            return false;
        }

        //判断该学生选修课已选课程的总学分是否小于6；
        //无效操作2：如果已有选课记录，并且总学分大于6学分，该学生不能再选了。
        PreparedStatement ps = null;
        sql = "select sum(grade) " + "from (select x.sname , x.cno,k.grade grade " + "from coursexx k join choicesxx x " + "on k.cno=x.cno and x.sname=?) result";
        String grade = null;
        try
        {
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next())
            {
                grade = rs.getString(1);
                if (grade == null)
                {
                    grade = "0";
                }
            }
        }
        catch (Exception rrr)
        {
            rrr.printStackTrace();
        }
        System.out.println("总学分：" + grade);

        if (Integer.parseInt(grade) > 6)
        {
            JOptionPane.showMessageDialog(null, "你已经选满6学分，系统将退出");
            this.setVisible(false);
            return false;
        }

        //无效操作3：该学生已经选择了某课程，则不能再选该课程了。
        sql = "select * from choicesxx where sname=? and cno=?";
        boolean flag = false;
        try
        {
            ps = con.prepareStatement(sql);
            ps.setString(1, this.getName());
            ps.setString(2, userInput);
            rs = ps.executeQuery();
            flag = rs.next();
        }
        catch (Exception eaa)
        {
            eaa.printStackTrace();
        }
        if (flag)
        {
            JOptionPane.showMessageDialog(null, "你已经选择了该课程。请另选课程");
            return false;
        }

        //如果以上无效操作都不存在，则返回true，意为这是一个准有效操作
        return true;
    }

    //对有效的提交操作的进行处理
    public void tryCommit() {
        // userInput为用户输入的课程ID.
        String userInput = jtfSelectedCourse.getText().toString().trim().toLowerCase();
        // if course still available(count<MAX_STUDENT),save result.
        // else if course not available,show Message to student.
        PreparedStatement ps;
        String sql = "select (Max-selectedCount) as RemainedCount "
                + "from Coursexx where cno=?";
        try
        {
            ps = con.prepareStatement(sql);
            // 取得学生ID或名字，将课程ID存入学生选课表choicesxx
            ps.setString(1, userInput);
            rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();
            int RemainedCount = -1;
            while (rs.next())
            {
                RemainedCount = rs.getInt(1);
                System.out.println("RemainedCount:" + RemainedCount);
            }

            //如果该课程还有选择的名额，提示单项选课操作成功。
            if (RemainedCount > 0)
            {
                //保存学生学号和课程ID到学生选课表
                sql = "insert into choicesxx values(?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, this.getName());
                ps.setString(2, userInput);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "选课成功： " + this.getName() + " 选了" + userInput + "." + "" + " 还有 " + RemainedCount + " 人可以选该课程。");

                // 更新课程中已选该课程的人数：即将可选该课程的人数减去1个人。
                sql = "update CourseXX set selectedCount=selectedCount+1 where cno=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, userInput);
                ps.executeUpdate();
                con.commit();
                //如果该课程已经没有选择名额，提示重新选课
            }
        }
        catch (Exception es)
        {
            es.printStackTrace();
            try
            {
                con.rollback();
            }
            catch (Exception ey)
            {
                ey.printStackTrace();
            }
        }
    }

    //对用户查询课程信息进行处理，并显示查询结果
    public void searchAvailableCourse()
    {
        con = DBManager.getConnection();

        //userInput取得用户输入的信息,selectedItem取得用户选择的查询方式
        String userInput = jtfSearch.getText().toString().trim().toLowerCase();
        String selectedItem = jcbSearch.getSelectedItem().toString().trim();
        System.out.println("User search:" + userInput);
        System.out.println("selectedItem:" + selectedItem);
        String sql = null;

        //按用户查询方式，如按课程名，课程ID或学时的查询进行处理，并在表格中显示结果
        try
        {
            if ("课程名".equals(selectedItem))
            {
                sql = "select cno,cname,hour,grade,term,isNeed from CourseXX where cname = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, userInput);
            }
            else if ("课程ID".equals(selectedItem))
            {
                sql = "select cno,cname,hour,grade,term,isNeed from CourseXX where cno = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, userInput);
            }
            else if ("学时".equals(selectedItem))
            {
                sql = "select cno,cname,hour,grade,term,isNeed from CourseXX where hour = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(userInput));
            }
            else if ("学分".equals(selectedItem))
            {
                sql = "select cno,cname,hour,grade,term,isNeed from CourseXX where grade = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(userInput));
            }
            else if ("学期".equals(selectedItem))
            {
                sql = "select cno,cname,hour,grade,term,isNeed from CourseXX where term = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, userInput);
            }
            System.out.println(sql);
            rs = ps.executeQuery();
            con.commit();
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();
            String result = null;
            int k = 1;
            boolean flag = false;

            //将查询结果以表格的形式显示出来
            while (rs.next())
            {
                for (int i = 1; i <= cols; i++)
                {
                    result = rs.getString(i);
                    System.out.println(result);
                    jtSearch.setValueAt(result, k - 1, i - 1);
                }
                k++;
                flag = true;
            }

            //如果查询结果集为空，提示用户没有该课程
            if (flag == false)
            {
                JOptionPane.showMessageDialog(null, "该课程不存在，请重新输入");
                return;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            try
            {
                con.rollback();
            }
            catch (Exception er)
            {
                er.printStackTrace();
            }
        }
    }

    //产生图形用户界面，以便用户操作
    public void createSearchCourse()
    {
        this.setLayout(new GridLayout(3, 1));
        JPanel jp1 = new JPanel();
        jp1.setLayout(new GridLayout(4, 1));
        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();
        JPanel jp10 = new JPanel();
        JPanel jp11 = new JPanel();
        JPanel jp12 = new JPanel();
        JPanel jp13 = new JPanel();

        JLabel jlSearch = new JLabel(" 学 生 选 课 查 询 系 统 ");
        jp11.add(jlSearch);
        jcbSearch.addItem(new String("课程名"));
        jcbSearch.addItem(new String("课程ID"));
        jcbSearch.addItem(new String("学时"));
        jcbSearch.addItem(new String("学分"));
        jcbSearch.addItem(new String("学期"));
        jp12.add(jtfSearch);
        jp12.add(jcbSearch);
        JButton jbOK = new JButton("查询");
        jbOK.setContentAreaFilled(false);    //按钮透明化
        jbOK.addActionListener(this);
        jbOK.setSize(90, 20);
        jp13.add(jbOK);
        jp1.add(jp10);
        jp1.add(jp11);
        jp1.add(jp12);
        jp1.add(jp13);
        jp2.add(jspSearch);
        JLabel jlSelectedCourse = new JLabel("请输入课程ID：");
        JButton jbSelectedCourse = new JButton("提交");
        jbSelectedCourse.setContentAreaFilled(false);       //按钮透明化
        jbSelectedCourse.addActionListener(this);
        jp3.add(jlSelectedCourse);
        jp3.add(jtfSelectedCourse);
        jp3.add(jbSelectedCourse);
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.setLocation(550,200);
        this.setVisible(true);
        this.setSize(500, 600);
    }

}