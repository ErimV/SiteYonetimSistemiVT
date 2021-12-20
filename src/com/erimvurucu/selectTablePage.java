package com.erimvurucu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.sql.Connection;

public class selectTablePage extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private String[] tables =
            {"kisi","misafir","personel","sakin",
            "sirket_temsilcisi","yonetici","ziyaretci",
            "bina_bilgi","evcil_hayvan","iletisim_bilgileri",
            "parsel","sirket","sokak","bina","konut","ticari_ortak"};
    private String selectedTable;

    public selectTablePage(Connection conn) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 800, 300);
        setResizable(false);
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(mainPanel);
        mainPanel.setLayout(null);

        JLabel lbl = new JLabel();
        lbl.setBounds(350,50,350,50);
        lbl.setFont(new Font("Times New Romans",Font.PLAIN,20));
        mainPanel.add(lbl);

        JComboBox tablesList = new JComboBox(tables);
        tablesList.setBounds(100, 50, 200, 50);
        tablesList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedTable = (String)tablesList.getSelectedItem();
                switch (selectedTable) {
                    case "kisi":
                        lbl.setText(selectedTable + " " +"Tablosu Secildi");
                        break;
                    case "misafir":
                        lbl.setText(selectedTable + " " +"Tablosu Secildi");
                        break;
                    case "personel":
                        lbl.setText(selectedTable + " " +"Tablosu Secildi");
                        break;
                    case "sakin":
                        lbl.setText(selectedTable + " " +"Tablosu Secildi");
                        break;
                    case "sirket_temsilcisi":
                        lbl.setText(selectedTable + " " +"Tablosu Secildi");
                        break;
                    case "yonetici":
                        lbl.setText(selectedTable + " " +"Tablosu Secildi");
                        break;
                    case "ziyaretci":
                        lbl.setText(selectedTable + " " +"Tablosu Secildi");
                        break;
                    case "bina_bilgi":
                        lbl.setText(selectedTable + " " +"Tablosu Secildi");
                        break;
                    case "evcil_hayvan":
                        lbl.setText(selectedTable + " " +"Tablosu Secildi");
                        break;
                    case "iletisim_bilgileri":
                        lbl.setText(selectedTable + " " +"Tablosu Secildi");
                        break;
                    case "parsel":
                        lbl.setText(selectedTable + " " +"Tablosu Secildi");
                        break;
                    case "sirket":
                        lbl.setText(selectedTable + " " +"Tablosu Secildi");
                        break;
                    case "sokak":
                        lbl.setText(selectedTable + " " +"Tablosu Secildi");
                        break;
                    case "bina":
                        lbl.setText(selectedTable + " " +"Tablosu Secildi");
                        break;
                    case "konut":
                        lbl.setText(selectedTable + " " +"Tablosu Secildi");
                        break;
                    case "ticari_ortak":
                        lbl.setText(selectedTable + " " +"Tablosu Secildi");
                        break;
                    default: lbl.setText("Hata");
                }
            }
        });
        mainPanel.add(tablesList);

        JButton btn = new JButton("Git");
        btn.setBounds(450,150,100,60);
        btn.setFont(new Font("Times New Romans",Font.BOLD,20));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedTable.equals("kisi")) {
                    Kisi kisi = new Kisi(conn);
                    kisi.setVisible(true);
                }
                else if (selectedTable.equals("misafir")) {
                    Misafir misafir = new Misafir(conn);
                    misafir.setVisible(true);
                }
                else if (selectedTable.equals("personel")) {
                    Personel personel = new Personel(conn);
                    personel.setVisible(true);
                }
                else if (selectedTable.equals("sakin")) {
                    Sakin sakin = new Sakin(conn);
                    sakin.setVisible(true);
                }
                else if (selectedTable.equals("sirket_temsilcisi")) {
                    SirketTems sirketTems = new SirketTems(conn);
                    sirketTems.setVisible(true);
                }
                else if (selectedTable.equals("yonetici")) {
                    Yonetici yonetici = new Yonetici(conn);
                    yonetici.setVisible(true);
                }
                else if (selectedTable.equals("ziyaretci")) {
                    Ziyaretci ziyaretci = new Ziyaretci(conn);
                    ziyaretci.setVisible(true);
                }
                else if (selectedTable.equals("bina_bilgi")) {
                    BinaBilgi binaBilgi = new BinaBilgi(conn);
                    binaBilgi.setVisible(true);
                }
                else if (selectedTable.equals("evcil_hayvan")) {
                    EvcilHayvan evcilHayvan = new EvcilHayvan(conn);
                    evcilHayvan.setVisible(true);
                }
                else if (selectedTable.equals("iletisim_bilgileri")) {
                    IltBilg iltBilg = new IltBilg(conn);
                    iltBilg.setVisible(true);
                }
                else if (selectedTable.equals("parsel")) {
                    Parsel parsel = new Parsel(conn);
                    parsel.setVisible(true);
                }
                else if (selectedTable.equals("sirket")) {
                    Sirket sirket = new Sirket(conn);
                    sirket.setVisible(true);
                }
                else if (selectedTable.equals("sokak")) {
                    Sokak sokak = new Sokak(conn);
                    sokak.setVisible(true);
                }
                else if (selectedTable.equals("bina")) {
                    Bina bina = new Bina(conn);
                    bina.setVisible(true);
                }
                else if (selectedTable.equals("konut")) {
                    Konut konut = new Konut(conn);
                    konut.setVisible(true);
                }
                else if (selectedTable.equals("ticari_ortak")) {
                    TicariOrtak ticariOrtak = new TicariOrtak(conn);
                    ticariOrtak.setVisible(true);
                }
            }
        });
        mainPanel.add(btn);
    }
}
