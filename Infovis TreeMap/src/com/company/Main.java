package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class Main extends JFrame {

    private Vis mainPanel;
    private String route;
    private Node file;
    private File saveFile;

    //constructor
    public Main(){
        JMenuBar mb = setupMenu();
        setJMenuBar(mb);

        file = null;
        saveFile = null;
        mainPanel = new Vis();
        setContentPane(mainPanel);
        route = null;
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tree Map for "+ route);
        setVisible(true);
    }

    private JMenuBar setupMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem item1 = new JMenuItem("Choose Folder");
        JMenu item2 = new JMenu("Color by");
        JMenuItem item2a = new JMenuItem("None");
        JMenuItem item2b = new JMenuItem("File Type");
        JMenuItem item2c = new JMenuItem("File Age");
        JMenuItem item2d = new JMenuItem("Random Fun");

        // setup action listeners
        // choose folder
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView());
                fc.setDialogTitle("Choose a folder");
                fc.setCurrentDirectory(new File(System.getProperty("user.home")));
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int fcValue = fc.showOpenDialog(getParent());
                if(fcValue == JFileChooser.APPROVE_OPTION){
                    saveFile = fc.getSelectedFile();
                    file = new Node(saveFile,"none");
                    mainPanel.setRootNode(file);
                    mainPanel.repaint();
                    setRoute(file);
                    setTitle("Treemap for "+file.getPath());
                } else{
                    System.out.println("Cancel was selected");
                }
//                System.out.println("clicked item1");
//                route = "/Users/hongbinlee/Downloads";
//                mainPanel.getRootNode(new Node(new File(route)));
//                mainPanel.repaint();
//                setTitle("Tree Map for "+ route);
//                System.out.println("finished item1");
            }
        });
        // color by none
        item2a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                file = new Node(saveFile,"none");
                System.out.println("color type: "+ file.getColorTheme());
                mainPanel.setRootNode(file);
                mainPanel.repaint();
            }
        });
        //color by file type
        item2b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                file = new Node(saveFile,"type");
                mainPanel.setRootNode(file);
                System.out.println("color type: "+ file.getColorTheme());
                mainPanel.repaint();
            }
        });
        //color by file age
        item2c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                file = new Node(saveFile,"age");
                mainPanel.setRootNode(file);
                System.out.println("color type: "+ file.getColorTheme());
                mainPanel.repaint();
            }
        });
        //color randomly
        item2d.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                file = new Node(saveFile,"random");
                mainPanel.setRootNode(file);
                System.out.println("color type: "+ file.getColorTheme());
                repaint();
            }
        });

        fileMenu.add(item1);
        fileMenu.add(item2);

        item2.add(item2a);
        item2.add(item2b);
        item2.add(item2c);
        item2.add(item2d);

        menuBar.add(fileMenu);

        return menuBar;
    }
    public void setRoute(Node f){
        route = f.getPath();
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { new Main(); }
        });
    }
}
