package edu.byuh.cis.cs490r.starter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JPanel;


public class Vis extends JPanel {

    int lookWhere;
    public Vis() {
        super();
        lookWhere = 0;
    }
    // position of w of the left eyeball when look left
    public int lookLeft(int w){
        return 56*w/128;
    }
    // position of w of the right eyeball when look right
    public int lookRight(int w){
        return  67*w/128;
    }


    @Override
    public void paintComponent(Graphics g1) {

        int w = getWidth();
        int h = getHeight();
        int LEyeball;
        int REyeball;

        Graphics2D g = (Graphics2D)g1;

        //draw blank background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);

        //render visualization
        g.setColor(Color.BLACK);
        //set line weight
        g.setStroke(new BasicStroke(2));
        //draw face
        g.drawRect(w/8*3, h/8*3,w/4,h/4);
        //draw eyes
        g.drawOval(7*w/16,7*h/16,w/16,h/16);
        g.drawOval(8*w/16,7*h/16,w/16,h/16);
        //draw eye balls
        switch (lookWhere){
            case 1:
                LEyeball = 59*w/128;
                REyeball = lookRight(w);
                break;
            case -1:
                LEyeball = lookLeft(w);
                REyeball = 64*w/128;
                break;
            default:
                LEyeball = 58*w/128;
                REyeball =  65*w/128;
                break;
        }
        //draw Eyeballs
        g.fillOval(LEyeball,29*h/64,w/25,h/25);
        g.fillOval(REyeball,29*h/64,w/25,h/25);
    }


}
