package edu.byuh.cis.cs490r.starter;

import java.awt.event.*;

import javax.swing.*;

public class Main extends JFrame {

    private Vis mainPanel;
    private MouseListener ml;
    private int frameW;
    public Main() {
        JMenuBar mb = setupMenu();
        setJMenuBar(mb);

        mainPanel = new Vis();
        setContentPane(mainPanel);

        setSize(800,600);
        frameW = getWidth();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Staring");
        setVisible(true);
    }

    private JMenuBar setupMenu() {
        //instantiate menubar, menus, and menu options
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Face");
        JMenuItem reset = new JMenuItem("Reset");
        JMenuItem faceitem1 = new JMenuItem("Look Left");
        JMenuItem faceitem2 = new JMenuItem("Look Right");
        JMenu subMenu = new JMenu("Clickable");
        JMenuItem clickableT = new JMenuItem("Yes");
        JMenuItem clickableF = new JMenuItem("No");
        

        //setup action listeners
        //if click 'Reset' menu
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.lookWhere = 0; //middle
                mainPanel.repaint();
            }
        });
        //if click 'Look Left' menu
        faceitem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.lookWhere = -1; // left
                mainPanel.repaint();
            }
        });
        //if click 'Look Right' menu
        faceitem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.lookWhere = 1; // right
                mainPanel.repaint();
            }
        });
        //if click 'Yes'
        clickableT.addActionListener(e -> {
            // Handling Mouse Event
            ml= new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int x = e.getX();
                    if(x < frameW/2){
                        mainPanel.lookWhere = -1;
                        mainPanel.repaint();
                    } else{
                        mainPanel.lookWhere = 1;
                        mainPanel.repaint();
                    }
                }
            };
            mainPanel.addMouseListener(ml);
        });

        //if click "No"
        clickableF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // remove the listener object
                mainPanel.removeMouseListener(ml);
                mainPanel.lookWhere = 0;
                mainPanel.repaint();
            }
        });

        //now hook them all together
        subMenu.add(clickableT);
        subMenu.add(clickableF);
        fileMenu.add(faceitem1);
        fileMenu.add(faceitem2);
        fileMenu.add(subMenu);
        menuBar.add(fileMenu);
        fileMenu.add(reset);

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