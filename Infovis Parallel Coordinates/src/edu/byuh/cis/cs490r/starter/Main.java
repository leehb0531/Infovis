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

    private List<Axis> axes;

    public Main() {

        axes = new ArrayList<>();

        JMenuBar mb = setupMenu();
        setJMenuBar(mb);

        mainPanel = new Vis();
        setContentPane(mainPanel);

        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Parallel Coordinates");
        setVisible(true);
    }

    //select count(*) from derbyDB

    private void performUltimateQuery(String q) {
        //List<Point2D> results = new ArrayList<>();
        try {
            Connection c = DriverManager.getConnection("jdbc:derby:MyDbTest");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(q);
            ResultSetMetaData md = rs.getMetaData();
            int numColumns = md.getColumnCount();
            for (int i=1; i<=numColumns; i++) { // add column
                Axis axis = new Axis(md.getColumnName(i));
                axes.add(axis);
            }
            while (rs.next()) {
                for (Axis a : axes) {// add rows
                    a.extractData(rs);
//                    System.out.println("the data is : " + rs.getObject(a.columnName));
                }
            }
        } catch (SQLException e) {
            System.out.println("could not connect to Derby!");
        }
        //return results;
    }

    private void performUltimateQuery2012(String q) {
        //List<Point2D> results = new ArrayList<>();
        try {
            Connection c = DriverManager.getConnection("jdbc:derby:MyDbTest2012");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(q);
            ResultSetMetaData md = rs.getMetaData();
            int numColumns = md.getColumnCount();
            for (int i=1; i<=numColumns; i++) {
                Axis axis = new Axis(md.getColumnName(i));
                axes.add(axis);
            }
            while (rs.next()) {
                for (Axis a : axes) {
                    a.extractData(rs);
//                    System.out.println("the data is : " + rs.getObject(a.columnName));
                }
            }
        } catch (SQLException e) {
            System.out.println("could not connect to Derby!");
        }
        //return results;
    }
    private void performUltimateQueryMarathon(String q) {
        //List<Point2D> results = new ArrayList<>();
        try {
            Connection c = DriverManager.getConnection("jdbc:derby:MyDbTestMarathon");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(q);
            ResultSetMetaData md = rs.getMetaData();
            int numColumns = md.getColumnCount();
            for (int i=1; i<=numColumns; i++) {
                Axis axis = new Axis(md.getColumnName(i));
                axes.add(axis);
            }
            while (rs.next()) {
                for (Axis a : axes) {
                    a.extractData(rs);
//                    System.out.println("the data is : " + rs.getObject(a.columnName));
                }
            }
        } catch (SQLException e) {
            System.out.println("could not connect to Derby!");
        }
        //return results;
    }
    private JMenuBar setupMenu() {
        //instantiate menubar, menus, and menu options
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Data");
        JMenuItem item1 = new JMenuItem("CIS 2012");
        JMenuItem item2 = new JMenuItem("CIS 2019");
        JMenuItem item3 = new JMenuItem("Marathon");
        JMenuItem reset = new JMenuItem("Reset");

        //setup action listeners

        //2012 data
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.forMoving = true;
                mainPanel.firstRun = true;
                mainPanel.resizing = true;
                mainPanel.selectedLines.clear();
                axes.clear();
                var cis2012Query = "SELECT * FROM CIS2019";
                performUltimateQuery2012(cis2012Query);
//                System.out.println(axes.size());
                mainPanel.setAxes(axes);
                mainPanel.setFirstRun(true);
                mainPanel.repaint();
//                mainPanel.polyline.clear();

            }
        });

        //2019 data
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.forMoving = true;
                mainPanel.firstRun = true;
                mainPanel.selectedLines.clear();
                mainPanel.resizing = true;
                axes.clear();
                var cis2019Query = "SELECT * FROM CIS2019";
                performUltimateQuery(cis2019Query);
                mainPanel.setAxes(axes);
                mainPanel.setFirstRun(true);
                mainPanel.repaint();
//                System.out.println("Finish");
            }
        });
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.forMoving = true;
                mainPanel.firstRun = true;
                mainPanel.selectedLines.clear();
                mainPanel.resizing = true;
                axes.clear();
                var marathonQuery = "SELECT * FROM marathon";
                performUltimateQueryMarathon(marathonQuery);
                mainPanel.setAxes(axes);
                mainPanel.setFirstRun(true);
                mainPanel.repaint();
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.forMoving = true;
                mainPanel.firstRun = true;
                mainPanel.selectedLines.clear();
                mainPanel.resizing = true;
                mainPanel.repaint();
            }
        });

        //now hook them all together
        fileMenu.add(item1);
        fileMenu.add(item2);
        fileMenu.add(item3);
        fileMenu.add(reset);
        menuBar.add(fileMenu);

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