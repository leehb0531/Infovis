package edu.byuh.cis.cs490r.starter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JPanel;


public class Vis extends JPanel {

    private String textToDisplay;

    public Vis() {
        super();
        textToDisplay = "There's nothing to see here.";
    }

    public void setText(String t) {
        textToDisplay = t;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;

        //draw blank background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        //render visualization
        g.setColor(Color.BLACK);
        g.drawString(textToDisplay, 10, 20);

    }

}
