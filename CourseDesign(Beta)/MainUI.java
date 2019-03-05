package com.Communication.data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainUI extends JFrame
{
    private Socket socket;
    private DataOutputStream dout;
    private DataInputStream din;
    private String loginName;
    private JTextArea chatArea;
    private JList<String> friendList;
    private JButton btnSend;
    private JPanel pContacts , pMain;
    private JTextField txtMsg;
    private JCheckBox sel;
    private DefaultListModel<String> model = new DefaultListModel<String>();
    public MainUI(String name, String message)
    {
        super("欢迎你: " + name);
        loginName = name;
        pContacts = new JPanel();
        if(message != null)
        {
            String friendName[] = message.split(",");
            for(String n : friendName)
            {
                model.addElement(n);
            }
        }
        friendList = new JList<String>(model);
        friendList.setPreferredSize(new Dimension(200,360));

        pContacts.add(friendList);
        this.add(pContacts, BorderLayout.WEST);

        pMain = new JPanel();
        chatArea = new JTextArea(20,30);
        chatArea.setPreferredSize(new Dimension(500,240));

        sel = new JCheckBox();
        txtMsg = new JTextField();
        txtMsg.setPreferredSize(new Dimension(400,30));
        btnSend = new JButton("发送");
        btnSend.setPreferredSize(new Dimension(68,30));
        pMain.add(chatArea);
        pMain.add(sel);
        pMain.add(txtMsg);
        pMain.add(btnSend);
        this.add(pMain, BorderLayout.CENTER);

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socket=new Socket("10.6.49.224",9999);
                    OutputStream output=socket.getOutputStream();
                    InputStream input=socket.getInputStream();
                    dout=new DataOutputStream(output);
                    din=new DataInputStream(input);
                    dout.writeUTF(txtMsg.getText());
                    System.out.println(txtMsg.getText());
                    String message = txtMsg.getText();
                    chatArea.append(String.format("%s%n", message));
                    txtMsg.setText(" ");
                    output.close();
                    input.close();
                    socket.close();
                } catch (UnknownHostException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

            }
        });
        this.setLocationRelativeTo(null);
        this.setSize(740,450);
        this.setResizable(false);
        setVisible(true);
    }
}
