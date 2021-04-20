package edu.byuh.cis.cs490r.starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main extends JFrame {

    private Vis mainPanel;

    public Main() {

        JMenuBar mb = setupMenu();
        setJMenuBar(mb);

        mainPanel = new Vis();
        setContentPane(mainPanel);

        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Scatter Plot");
        setVisible(true);
    }

    private List<Point2D> performTwoNumberQuery(String q) {
        List<Point2D> results = new ArrayList<>();
        try {
            Connection c = DriverManager.getConnection("jdbc:derby:MyDbTest");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(q);
            while (rs.next()) {
                double x = rs.getDouble(1);
                double y = rs.getDouble(2);
                var guillaume = new Point2D.Double(x,y);
                results.add(guillaume);
            }
        } catch (SQLException e) {
            System.out.println("could not connect to Derby!");
        }
        return results;
    }

    private JMenuBar setupMenu() {
        //instantiate menubar, menus, and menu options
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Scatter Plot");
        JMenuItem item1 = new JMenuItem("Credits attempted vs. Credits passed");
        JMenuItem item2 = new JMenuItem("Credits attempted vs. GPA");
        JMenuItem item3 = new JMenuItem("Credits passed vs. GPA");
        JMenuItem item4 = new JMenuItem("Age vs GPA");
        JMenuItem item5 = new JMenuItem("Current Credits vs. GPA");
        JMenu fileMenu2 = new JMenu("Reset");
        JMenuItem reset = new JMenuItem("Reset the size");


        //setup action listeners
        //Credits attempted vs. Credits passed
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Just clicked menu item 1");
                var gilmo = performTwoNumberQuery("SELECT credits_attempted, credits_passed FROM cis2019");
                mainPanel.zoom = false;
                mainPanel.setData(gilmo);
            }
        });
        //Credits attempted vs. GPA
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var lauren = performTwoNumberQuery("select credits_attempted,gpa from cis2019");
                mainPanel.zoom = false;
                mainPanel.setData(lauren);
            }
        });
        //Credits passed vs. GPA
        item3.addActionListener(e -> {
            var sethQuery = "SELECT CREDITS_PASSED, gpa from CIS2019";
            var gilmo = performTwoNumberQuery(sethQuery);
            mainPanel.zoom = false;
            mainPanel.setData(gilmo);
        });
        //Age vs GPA
        item4.addActionListener(e -> {
            var sethQuery = "SELECT age, gpa from CIS2019";
            var gilmo = performTwoNumberQuery(sethQuery);
            mainPanel.zoom = false;
            mainPanel.setData(gilmo);
        });
        //Current Credits vs. GPA
        item5.addActionListener(e -> {
            var sethQuery = "SELECT current_credits, gpa from CIS2019";
            var gilmo = performTwoNumberQuery(sethQuery);
            mainPanel.zoom = false;
            mainPanel.setData(gilmo);
        });
        reset.addActionListener(e -> {
            mainPanel.zoom =false;
            mainPanel.repaint();
        });

        //now hook them all together
        fileMenu.add(item1);
        fileMenu.add(item2);
        fileMenu.add(item3);
        fileMenu.add(item4);
        fileMenu.add(item5);
        menuBar.add(fileMenu);
        menuBar.add(fileMenu2);
        fileMenu2.add(reset);

        return menuBar;
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}