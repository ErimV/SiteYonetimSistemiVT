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

public class Yonetici extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private DefaultTableModel model;
    private JTable table;

    public Yonetici(Connection conn) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 190, 800, 500);
        setResizable(false);
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(mainPanel);
        mainPanel.setLayout(null);

        JLabel lbl1 = new JLabel("yoneticino : ");
        lbl1.setBounds(20,20,150,50);
        lbl1.setFont(new Font("Times New Romans",Font.PLAIN,20));
        mainPanel.add(lbl1);

        JLabel lbl2 = new JLabel("turkodu : ");
        lbl2.setBounds(20,80,150,50);
        lbl2.setFont(new Font("Times New Romans",Font.PLAIN,20));
        mainPanel.add(lbl2);

        JLabel lbl3 = new JLabel("mevcut_yonetici(t/f) : ");
        lbl3.setBounds(20,140,150,50);
        lbl3.setFont(new Font("Times New Romans",Font.PLAIN,15));
        mainPanel.add(lbl3);

        JLabel lbl4 = new JLabel("donem : ");
        lbl4.setBounds(20,200,150,50);
        lbl4.setFont(new Font("Times New Romans",Font.PLAIN,20));
        mainPanel.add(lbl4);

        JTextField field1 = new JTextField();
        field1.setBounds(200, 20, 100, 50);
        field1.setFont(new Font("Times New Romans",Font.PLAIN,20));
        mainPanel.add(field1);

        JTextField field2 = new JTextField();
        field2.setBounds(200, 80, 100, 50);
        field2.setFont(new Font("Times New Romans",Font.PLAIN,20));
        mainPanel.add(field2);

        JTextField field3 = new JTextField();
        field3.setBounds(200, 140, 100, 50);
        field3.setFont(new Font("Times New Romans",Font.PLAIN,20));
        mainPanel.add(field3);

        JTextField field4 = new JTextField();
        field4.setBounds(200, 200, 100, 50);
        field4.setFont(new Font("Times New Romans",Font.PLAIN,20));
        mainPanel.add(field4);

        JButton btn1 = new JButton("Ekle");
        btn1.setBounds(20,300,130,30);
        btn1.setFont(new Font("Times New Romans",Font.BOLD,15));
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = String.format("INSERT INTO kisiler.yonetici VALUES (%d,'%s',%b,'%s')",Integer.parseInt(field1.getText()),field2.getText(),Boolean.parseBoolean(field3.getText()),field4.getText());
                    Statement st = conn.createStatement();
                    st.executeUpdate(sql);
                    model = Yonetici.reloadTable(mainPanel,conn);
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
                    String sql = String.format("DELETE FROM kisiler.yonetici WHERE turkodu='%s'",field2.getText());
                    Statement st = conn.createStatement();
                    st.executeUpdate(sql);
                    model = Yonetici.reloadTable(mainPanel,conn);
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
                    String sql = String.format("UPDATE kisiler.yonetici SET yoneticino=%d,mevcut_yonetici=%b,donem='%s' WHERE turkodu='%s'",Integer.parseInt(field1.getText()),Boolean.parseBoolean(field3.getText()),field4.getText(),field2.getText());
                    Statement st = conn.createStatement();
                    st.executeUpdate(sql);
                    model = Yonetici.reloadTable(mainPanel,conn);
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
                    String sql = String.format("SELECT * FROM kisiler.yonetici WHERE yoneticino=%d",Integer.parseInt(field1.getText()));
                    model = Yonetici.reloadTable(sql,mainPanel,conn);
                }
                else if (!field2.getText().equals("")) {
                    String sql = String.format("SELECT * FROM kisiler.yonetici WHERE turkodu='%s'",field2.getText());
                    model = Yonetici.reloadTable(sql,mainPanel,conn);
                }
                else if (!field3.getText().equals("")) {
                    String sql = String.format("SELECT * FROM kisiler.yonetici WHERE mevcutYonetici=%b",Boolean.parseBoolean(field3.getText()));
                    model = Yonetici.reloadTable(sql,mainPanel,conn);
                }
                else if (!field4.getText().equals("")) {
                    String sql = String.format("SELECT * FROM kisiler.yonetici WHERE donem='%s'",field4.getText());
                    model = Yonetici.reloadTable(sql,mainPanel,conn);
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
                model = Yonetici.reloadTable(mainPanel,conn);
            }
        });
        mainPanel.add(btn5);

        model = Yonetici.reloadTable(mainPanel,conn);
    }

    static DefaultTableModel reloadTable(JPanel mainPanel, Connection conn) {
        DefaultTableModel model = new DefaultTableModel();
        Object[] columns = {"yoneticino","turkodu","mevcut_yonetici","donem"};
        Object[] rows = new Object[4];
        String sql = "SELECT * FROM kisiler.yonetici";
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
                rows[0] = rs.getString("yoneticino");
                rows[1] = rs.getString("turkodu");
                rows[2] = rs.getString("mevcut_yonetici");
                rows[3] = rs.getString("donem");
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
        Object[] columns = {"yoneticino","turkodu","mevcut_yonetici","donem"};
        Object[] rows = new Object[4];
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
                rows[0] = rs.getString("yoneticino");
                rows[1] = rs.getString("turkodu");
                rows[2] = rs.getString("mevcut_yonetici");
                rows[3] = rs.getString("donem");
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
