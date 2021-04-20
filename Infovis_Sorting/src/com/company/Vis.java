package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class Vis p {
    private List<Integer> originalList;
    private List<Integer> dataList;
    private List<Bar> barList;
    private boolean isSorted;
    private boolean firstRun;
    private String sortName;


    public Vis() {
        originalList = new ArrayList<>();
        dataList = new ArrayList<>();
        barList = new ArrayList<>();
        sortName = "none";
        isSorted = false;
        firstRun = true;
    }

    public List<Integer> getDataList() {
        return dataList;
    }
    public void setSorted(boolean sorted) {
        isSorted = sorted;
    }
    public void setDataList(List<Integer> dataList) {
        this.originalList = dataList;
        this.dataList = dataList;
    }
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
    public String getSortName() {
        return sortName;
    }

    public List<Bar> getBarList() {
        return barList;
    }


    private void drawXYaxis(Graphics2D g, int w, int h, Color color){
        g.setColor(color);
        double xleft = 0.01;
        double xright = 0.99;
        double ytop = 0.01;
        double ybottom = 0.95;
        g.drawLine((int)(w*xleft),(int)(h*ybottom),(int)(w*xright),(int)(h*ybottom)); // x axis
        g.drawLine((int)(w*xleft),(int)(h*ytop),(int)(w*xleft),(int)(h*ybottom)); // y axis
    }
    private Integer getMaxValue(){
        int max = 0;
        for(var d: dataList){
            if(max < d){
                max = d;
            }
        }
        return max;
    }
    private void drawBarList(Graphics2D g, int w, int h){
        double barWidthWOPadding = w*0.98/dataList.size();
        double xpos = w*0.01+barWidthWOPadding*0.02;
        double ypos = h*0.95;
        for(var bar: barList){
            bar.draw(g,xpos,ypos);
            bar.drawValue(g,xpos,ypos);
            xpos += barWidthWOPadding;
        }
    }

    private void instBarList(int w, int h){ //instantiate a Bar and add it to barList
        if(barList.isEmpty()) {
            int max = getMaxValue();
            for (var data : dataList) {
                barList.add(new Bar(data, w * 0.98 / dataList.size() * 0.96, (h * 0.94) * ((double) data / max)));
            }
        }
    }

    private void drawBackground(Graphics2D g, int w, int h,Color color){
        g.setColor(color);
        g.drawRect(0,0,w,h);
    }
    public void reset(){
        dataList = originalList;
        barList = new ArrayList<>();
        int max = getMaxValue();
        for (var data : dataList) {
            barList.add(new Bar(data, getWidth() * 0.98 / dataList.size() * 0.96, (getHeight() * 0.94) * ((double) data / max)));
        }
        sortName = "none";
        isSorted = false;
        firstRun = true;
    }
    public void blank(){
        originalList = new ArrayList<>();
        dataList = new ArrayList<>();
        barList = new ArrayList<>();
        sortName = "none";
        isSorted = false;
        firstRun = true;
    }

    // Actual drawing
    @Override
    public void paintComponent(Graphics g1){
        Graphics2D g = (Graphics2D) g1;
        int w = getWidth();
        int h = getHeight();

        drawBackground(g,w,h,Color.white); // draw background
        if(!dataList.isEmpty()) {
            drawXYaxis(g, w, h, Color.black); // draw X,Y axes

            // instantiate bars
            instBarList(w, h);

            // draw Rectangle of Data
            drawBarList(g, w, h);
        }
    }
}
