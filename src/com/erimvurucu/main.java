package com.erimvurucu;

import java.awt.*;
import java.sql.*;

public class main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    loginPage frame = new loginPage();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    static ResultSet list(Connection conn, String sql) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
