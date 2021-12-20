package com.erimvurucu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class loginPage extends JFrame{
    @Serial
    private static final long serialVersionUID = 1L;

    public loginPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 800, 600);
        setResizable(false);
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(mainPanel);
        mainPanel.setLayout(null);

        JLabel lbl1 = new JLabel("Veritabanı Yönetici Girisi");
        lbl1.setBounds(200,10,600,100);
        lbl1.setFont(new Font("Times New Romans",Font.BOLD,30));
        mainPanel.add(lbl1);

        JLabel lbl2 = new JLabel("Kullanıcı adı : ");
        lbl2.setBounds(200,150,200,60);
        lbl2.setFont(new Font("Times New Romans",Font.PLAIN,25));
        mainPanel.add(lbl2);

        JLabel lbl3 = new JLabel("Sifre : ");
        lbl3.setBounds(200,250,200,60);
        lbl3.setFont(new Font("Times New Romans",Font.PLAIN,25));
        mainPanel.add(lbl3);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(400, 150, 200, 60);
        usernameField.setFont(new Font("Times New Romans",Font.PLAIN,25));
        mainPanel.add(usernameField);
        usernameField.setColumns(10);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(400,250,200,60);
        passwordField.setFont(new Font("Times New Romans",Font.PLAIN,25));
        mainPanel.add(passwordField);

        JButton btn1 = new JButton("Login");
        btn1.setBounds(450,350,100,60);
        btn1.setFont(new Font("Times New Romans",Font.BOLD,20));
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String dbURL = "jdbc:postgresql://localhost:5432/ComplexDB";
                try {
                    Connection conn = DriverManager.getConnection(dbURL, username, password);

                    if (conn != null){
                        dispose();
                        JOptionPane.showMessageDialog(btn1, "Veritabanina baglanildi");
                        selectTablePage mainFrame = new selectTablePage(conn);
                        mainFrame.setVisible(true);
                    }
                } catch (SQLException sqlException) {
                    JOptionPane.showMessageDialog(btn1, "Sifre ya da kullanıcı adı hatalı!");
                    sqlException.printStackTrace();
                }
            }
        });
        mainPanel.add(btn1);
    }
}
