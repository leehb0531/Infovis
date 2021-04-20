package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

public class Vis extends JPanel implements MouseListener, MouseMotionListener {


    private Node root;

    public Vis(){
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    public void setRootNode(Node n){
        root = n;
    }
    @Override
    public void paintComponent(Graphics g1){
        Graphics2D g = (Graphics2D) g1;

        int w = getWidth();
        int h = getHeight();
        int x = 0;
        int y = 0;

        //draw blank background
        g.setColor(Color.black);
        g.fillRect(x,y,w,h);

        if(root != null){
            try {
                root.draw(g,x,y,w,h,"HORIZONTAL");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e){

    }
    @Override
    public void mouseExited(MouseEvent e){
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        if(root!=null){
            setToolTipText(root.getNodeAt(x,y).getPath());
        }
    }
}
