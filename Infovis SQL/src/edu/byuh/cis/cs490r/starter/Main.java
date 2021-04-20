package edu.byuh.cis.cs490r.starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

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
        setTitle("Put the title of your program here");
        setVisible(true);
    }

    //select count(*) from derbyDB
    private int runSimpleCountQuery(String q) {
        try {
            //connect to the database
            Connection c = DriverManager.getConnection("jdbc:derby:MyDbTest");
            //statement is a object executing the sql statment.
            Statement s = c.createStatement();
            //Get table according to the statement
            ResultSet rs = s.executeQuery(q);
            //read each rows
            rs.next();
            int count = rs.getInt(1);
            return count;
        } catch (SQLException e) {
            System.out.println("could not connect to Derby!");
            return 0;
        }
    }

    private JMenuBar setupMenu() {
        //instantiate menubar, menus, and menu options
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem item1 = new JMenuItem("Item 1");
        JMenu subMenu = new JMenu("Submenu");
        JMenuItem item2 = new JMenuItem("Item 2");
        JMenuItem item3 = new JMenuItem("Item 3");
        JMenuItem item4 = new JMenuItem("Item 4");
        JMenuItem item5 = new JMenuItem("Item 5");
        JMenuItem item6 = new JMenuItem("Item 6");

        //setup action listeners
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int query1 = runSimpleCountQuery("SELECT COUNT(*) FROM cis2019");
                System.out.println("There are " + query1 + " students in the table cis2019.");
                mainPanel.setText("There are " + query1 + " students in the table cis2019.");
            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int query2 = runSimpleCountQuery("SELECT COUNT(*) FROM cis2019 " +
                        "WHERE HOME = 'US Mainland' " +
                        "and MAJOR = 'Computer Science'");
                System.out.println("There are " + query2 + " student.");
                mainPanel.setText("How many students are from US Mainland majoring in Computer Science?" +
                        " There are " + query2 + " student.");
            }
        });
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int query3 = runSimpleCountQuery("SELECT COUNT(*) FROM cis2019 " +
                        "WHERE HOME = 'US Mainland' " +
                        "and MAJOR = 'Computer Science' " +
                        "");
                System.out.println("Among the student of item2 " +
                        "there are " +
                        query3 + " - GAP 3.75~3.85 or 3.95~4");
                mainPanel.setText("Among the student of item2 " +
                        "there are " +
                        query3 + " - GAP 3.75~3.85 or 3.95~4");
            }
        });
        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int query4 = runSimpleCountQuery("SELECT COUNT(*) FROM cis2019 " +
                        "WHERE GRADYEAR >= 2019 " +
                        "and (HOME = 'Asia' or HOME = 'Other International')");
                System.out.println("There are " + query4 + " students who are from Asia or other international graduating from 2019.");
                mainPanel.setText("There are " + query4 + " students who are from Asia or other international graduating from 2019.");
            }
        });
        item5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int query5 = runSimpleCountQuery("SELECT COUNT(*) FROM cis2019 " +
                        "WHERE GENDER = 'F' and MAJOR = 'Computer Science'");
                System.out.println("In Computer Science, there are " + query5 + " female students");
                mainPanel.setText("In Computer Science, there are " + query5 + " female students");
            }
        });
        item6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int query5 = runSimpleCountQuery("SELECT COUNT(*) FROM cis2019 " +
                        "WHERE GENDER = 'F' and MAJOR = 'Computer Science'");
                int query6 = runSimpleCountQuery("SELECT COUNT(*) FROM cis2019 " +
                        "WHERE GENDER = 'M' and MAJOR = 'Computer Science'");
                System.out.println("I found that there are " + query6 + " male students in Computer Science.");
                mainPanel.setText("I found that there are " + query6 + " male students in Computer Science.");
            }
        });

        //now hook them all together
        subMenu.add(item2);
        fileMenu.add(item1);
        fileMenu.add(subMenu);
        menuBar.add(fileMenu);
        fileMenu.add(item3);
        fileMenu.add(item4);
        fileMenu.add(item5);
        fileMenu.add(item6);

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