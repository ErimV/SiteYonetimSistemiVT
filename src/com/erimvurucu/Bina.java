package com.erimvurucu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bina extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private DefaultTableModel model;
    private JTable table;

    public Bina(Connection conn) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 190, 800, 500);
        setResizable(false);
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(mainPanel);
        mainPanel.setLayout(null);

        JLabel lbl1 = new JLabel("binano : ");
        lbl1.setBounds(20,20,100,50);
        lbl1.setFont(new Font("Times New Romans",Font.PLAIN,20));
        mainPanel.add(lbl1);

        JLabel lbl2 = new JLabel("parselno : ");
        lbl2.setBounds(20,80,100,50);
        lbl2.setFont(new Font("Times New Romans",Font.PLAIN,20));
        mainPanel.add(lbl2);

        JTextField field1 = new JTextField();
        field1.setBounds(150, 20, 150, 50);
        field1.setFont(new Font("Times New Romans",Font.PLAIN,20));
        mainPanel.add(field1);

        JTextField field2 = new JTextField();
        field2.setBounds(150, 80, 150, 50);
        field2.setFont(new Font("Times New Romans",Font.PLAIN,20));
        mainPanel.add(field2);

        JButton btn1 = new JButton("Ekle");
        btn1.setBounds(20,300,130,30);
        btn1.setFont(new Font("Times New Romans",Font.BOLD,15));
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = String.format("INSERT INTO bina.bina VALUES (%d,%d)",Integer.parseInt(field1.getText()),Integer.parseInt(field2.getText()));
                    Statement st = conn.createStatement();
                    st.executeUpdate(sql);
                    model = Bina.reloadTable(mainPanel,conn);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        mainPanel.add(btn1);

        JButton btn2 = new JButton("Sil");
        btn2.setBounds(170,300,130,30);
        btn2.setFont(new Font("Times New Romans",Font.BOLD,15));
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = String.format("DELETE FROM bina.bina WHERE binano=%d",Integer.parseInt(field1.getText()));
                    Statement st = conn.createStatement();
                    st.executeUpdate(sql);
                    model = Bina.reloadTable(mainPanel,conn);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        mainPanel.add(btn2);

        JButton btn3 = new JButton("Güncelle");
        btn3.setBounds(20,350,130,30);
        btn3.setFont(new Font("Times New Romans",Font.BOLD,15));
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = String.format("UPDATE bina.bina SET parselno=%d WHERE binano=%d",Integer.parseInt(field2.getText()),Integer.parseInt(field1.getText()));
                    Statement st = conn.createStatement();
                    st.executeUpdate(sql);
                    model = Bina.reloadTable(mainPanel,conn);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        mainPanel.add(btn3);

        JButton btn4 = new JButton("Ara");
        btn4.setBounds(170,350,130,30);
        btn4.setFont(new Font("Times New Romans",Font.BOLD,15));
        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!field1.getText().equals("")) {
                    String sql = String.format("SELECT * FROM bina.bina WHERE binano=%d",Integer.parseInt(field1.getText()));
                    model = Bina.reloadTable(sql,mainPanel,conn);
                }
                else if (!field2.getText().equals("")) {
                    String sql = String.format("SELECT * FROM bina.bina WHERE parselno=%d",Integer.parseInt(field2.getText()));
                    model = Bina.reloadTable(sql,mainPanel,conn);
                }
            }
        });
        mainPanel.add(btn4);

        JButton btn5 = new JButton("Varsayılan");
        btn5.setBounds(380,350,350,30);
        btn5.setFont(new Font("Times New Romans",Font.BOLD,15));
        btn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model = Bina.reloadTable(mainPanel,conn);
            }
        });
        mainPanel.add(btn5);

        model = Bina.reloadTable(mainPanel,conn);
    }

    static DefaultTableModel reloadTable(JPanel mainPanel, Connection conn) {
        DefaultTableModel model = new DefaultTableModel();
        Object[] columns = {"binano","parselno"};
        Object[] rows = new Object[2];
        String sql = "SELECT * FROM bina.bina";
        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBounds(330,20,450,300);
        mainPanel.add(scrollPanel);
        JTable table = new JTable();
        scrollPanel.setViewportView(table);
        model.setColumnCount(0);
        model.setRowCount(0);
        model.setColumnIdentifiers(columns);
        ResultSet rs = main.list(conn,sql);
        try {
            while (rs.next()){
                rows[0] = rs.getString("binano");
                rows[1] = rs.getString("parselno");
                model.addRow(rows);
            }
            table.setModel(model);
            return model;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    static DefaultTableModel reloadTable(String sql,JPanel mainPanel,Connection conn) {
        DefaultTableModel model = new DefaultTableModel();
        Object[] columns = {"binano","parselno"};
        Object[] rows = new Object[2];
        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBounds(330,20,450,300);
        mainPanel.add(scrollPanel);
        JTable table = new JTable();
        scrollPanel.setViewportView(table);
        model.setColumnCount(0);
        model.setRowCount(0);
        model.setColumnIdentifiers(columns);
        ResultSet rs = main.list(conn,sql);
        try {
            while (rs.next()){
                rows[0] = rs.getString("binano");
                rows[1] = rs.getString("parselno");
                model.addRow(rows);
            }
            table.setModel(model);
            return model;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
