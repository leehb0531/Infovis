package edu.byuh.cis.cs490r.starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.*;

public class Main extends JFrame {

    private Vis mainPanel;

    public Main() {

        JMenuBar mb = setupMenu();
        setJMenuBar(mb);

        mainPanel = new Vis();
        setContentPane(mainPanel);

        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Chart");
        setVisible(true);
    }

    //select count(*) from derbyDB
    private int runSimpleCountQuery(String q) {
        try {
            Connection c = DriverManager.getConnection("jdbc:derby:MyDbTest");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(q);
            rs.next();
            int count = rs.getInt(1);
            return count;
        } catch (SQLException e) {
            System.out.println("could not connect to Derby!");
            return 0;
        }
    }

    private LinkedHashMap<String, Double> performTwoColumnQuery(String q) {
        LinkedHashMap<String, Double> results = new LinkedHashMap<>();
        try {
            Connection c = DriverManager.getConnection("jdbc:derby:MyDbTest");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(q);
            while (rs.next()) {
                double num = rs.getDouble(1);
                String label = rs.getString(2);
                results.put(label, num);
            }

        } catch (SQLException e) {
            System.out.println("could not connect to Derby!");
        }
        return results;
    }

    private JMenuBar setupMenu() {
        //instantiate menubar, menus, and menu options
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu1 = new JMenu("Chart Type");
        JMenuItem barType = new JMenuItem("Bar Type");
        JMenuItem lineType = new JMenuItem("Line Type");

        JMenu fileMenu2 = new JMenu("Query");
        //c means chart
        JMenuItem item1c = new JMenuItem("Item 1");
        JMenuItem item2c = new JMenuItem("Item 2");
        JMenuItem item3c = new JMenuItem("Item 3");
        JMenuItem item4c = new JMenuItem("Item 4");
        JMenuItem item5c = new JMenuItem("Item 5");
        JMenuItem item6c = new JMenuItem("Item 6");


        //setup action listeners
        //set the type to bar chart
        barType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setChartType("Bar");
                mainPanel.setText("Bar chart");
            }
        });
        //set the type to line chart
        lineType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setChartType("Line");
                mainPanel.setText("Line chart");
            }
        });
        //The number of students in each of the majors
        item1c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Just clicked menu item 1");
                var query1c = performTwoColumnQuery("select count(*), major from cis2019 group by major");
                for (var q : query1c.keySet()){
                    double num = query1c.get(q);
                    System.out.println(q + " : " + (int)num);
                }
                mainPanel.setData(query1c);
            }
        });
        //The number of students from each of the "home areas"
        item2c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Just clicked menu item 2");
                var query2c = performTwoColumnQuery("select count(*), home from cis2019 group by home");
                for (var q : query2c.keySet()){
                    double num = query2c.get(q);
                    System.out.println(q + " : " + (int)num);
                }
                mainPanel.setData(query2c);
            }
        });
        //The average GPA of students in each of the major
        item3c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Just clicked menu item 3");
                var query3c = performTwoColumnQuery("select AVG(gpa), major from cis2019 group by major");
                for (var q : query3c.keySet()){
                    double num = query3c.get(q);
                    System.out.println(q + " : " + num);
                }
                mainPanel.setData(query3c);
            }
        });
        //The average number of credits attempted, per year
        item4c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Just clicked menu item 3");
                var query4c = performTwoColumnQuery("select AVG(credits_attempted), gradyear from cis2019 group by gradyear");
                for (var q : query4c.keySet()){
                    double num = query4c.get(q);
                    System.out.println(q + " : " + num);
                }
                mainPanel.setData(query4c);
            }
        });
        //The average GPA of students in each of the age group
        item5c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Just clicked menu item 5");
                var query5c = performTwoColumnQuery("select AVG(gpa), agegroup from cis2019 group by agegroup");
                for (var q : query5c.keySet()){
                    double num = query5c.get(q);
                    System.out.println(q + " : " + num);
                }
                mainPanel.setData(query5c);
            }
        });
        //Number of students per GPA
        item6c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Just clicked menu item 6");
                var query6c = performTwoColumnQuery("select count(*), gpa_bins from cis2019 group by gpa_bins");
                for (var q : query6c.keySet()){
                    double num = query6c.get(q);
                    System.out.println(q + " : " + num);
                }
                mainPanel.setData(query6c);
            }
        });

        //now hook them all together
        fileMenu2.add(item1c);
        fileMenu2.add(item2c);
        fileMenu2.add(item3c);
        fileMenu2.add(item4c);
        fileMenu2.add(item5c);
        fileMenu2.add(item6c);
        fileMenu1.add(barType);
        fileMenu1.add(lineType);
        menuBar.add(fileMenu1);
        menuBar.add(fileMenu2);


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