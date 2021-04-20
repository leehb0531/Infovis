package edu.byuh.cis.cs490r.starter;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;

import static java.lang.Math.abs;


public class Vis extends JPanel {

    private Map<String, Double> data;
    private LinkedHashMap<String, Double> relativeData;
    private String cType;
    private double setMaxY;
    private LinkedHashMap<Integer, Integer> coordinates;
    public Vis() {
        super();
        cType = "Bar";
        relativeData = new LinkedHashMap<>();
        coordinates = new LinkedHashMap<>();
        setMaxY=0;
    }

    public void setChartType(String t){
        cType = t;
    }
    public void setText(String t) {
        repaint();
    }

    public void setData(Map<String, Double> map) {
        relativeData.clear();
        double max =0;

        data = map;
        var allValues = data.values();

        //find maximum value
        for (var val : allValues) {
            if (val > max) {
                max = val;
            }
        }
        setMaxValue(max);

        for (var key : data.keySet()) {
            relativeData.put(key, data.get(key) / max);
        }

        repaint();
    }

    private void setMaxValue(double m){
        setMaxY= m;
    }

    //actual drawing
    @Override
    public void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;
        float w = getWidth();
        float h = getHeight();
        int xmax = (int)(w*0.925);
        int xmin = (int)(w*0.075);
        int ymax = (int)(h*0.95);
        int ymin = (int)(h*0.05);
        Random rand = new Random();

        //draw blank background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, (int)w, (int)h);

        //render visualization
        g.setColor(Color.BLACK);

        //x-axis
        g.drawLine(xmin, ymax, xmax,ymax);
        //y-axis
        g.drawLine(xmin, ymin, xmin, ymax);
        int x = xmin + xmin; // x == start point of x-axis of each bar
        int y=relativeData.size();
        int yHeight=0;

        if(cType.equals("Bar")){
            for (var eachKey : relativeData.keySet()) {
                //random color for each bar
                int r = rand.nextInt(255);
                int cg = rand.nextInt(255);
                int b = rand.nextInt(255);
                g.setColor(new Color(r,cg,b));

                double barHeight = (ymax-ymin) * relativeData.get(eachKey);

                //draw a bar
                g.fillRect(x, ymax-(int)barHeight, (xmax-xmin) / relativeData.size()/2, (int)barHeight);

                g.setColor(Color.black);
                // label x value
                g.drawString(eachKey, x+((((xmax-xmin) / relativeData.size()/2)-(g.getFontMetrics().stringWidth(eachKey)))/2), ymax+ getFont().getSize());
                //label y value
                String ylabel = String.format("%.2f",y*setMaxY/relativeData.size());
                g.drawString(ylabel, xmin-(g.getFontMetrics().stringWidth(ylabel)), ymin+(yHeight)*ymax/ relativeData.size());
                y--;
                yHeight++;
                System.out.println(ylabel+":"+y+":"+yHeight);
                x += (xmax-xmin) / relativeData.size(); //going to next bar
            }
        } else{
            double circleW = getWidth()*0.025;
            coordinates.clear();
            for (var eachKey : relativeData.keySet()){
                //set random color
                int r = rand.nextInt(255);
                int cg = rand.nextInt(255);
                int b = rand.nextInt(255);
                g.setColor(new Color(r,cg,b));

                double pointHeight = (ymax-ymin) * relativeData.get(eachKey);
                //draw a circle
                g.fillOval(x+((xmax-xmin) / relativeData.size()/2)/2-(int)(circleW/2), ymax-(int)pointHeight-(int)(circleW/2),(int)circleW,(int)circleW);
                // store xcoordinate and y coordinate
                coordinates.put((x+((xmax-xmin) / relativeData.size()/2)/2),ymax-(int)pointHeight);

                g.setColor(Color.black);
                // label x value
                g.drawString(eachKey, x+((((xmax-xmin) / relativeData.size()/2)-(g.getFontMetrics().stringWidth(eachKey)))/2), ymax+ getFont().getSize());
                //label y value
                String ylabel = String.format("%.2f",y*setMaxY/relativeData.size());
                g.drawString(ylabel, xmin-(g.getFontMetrics().stringWidth(ylabel)), ymin+(yHeight)*ymax/ relativeData.size());


                y--;
                yHeight++;

                x += (xmax-xmin) / relativeData.size(); //going to next bar
            }
            for (var c = 0; c<coordinates.size()-1;c++){
                g.drawLine((int)coordinates.keySet().toArray()[c],(int)coordinates.get(coordinates.keySet().toArray()[c]),(int)coordinates.keySet().toArray()[c+1],(int)coordinates.get(coordinates.keySet().toArray()[c+1]));
            }
        }
    }
}
