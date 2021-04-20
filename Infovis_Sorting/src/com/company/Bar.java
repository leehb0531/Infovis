package com.company;
import javax.swing.*;
import java.awt.*;
import java.text.AttributedCharacterIterator;

enum Status {
    NORMAL,
    COMPAREFROM,
    COMPARETO,
    PIVOT
}

public class Bar {

    private int value;
    private double height;
    private Color color;
    private Status status;
    private double width;
    private String name;

    public Bar(int v, double w, double h){
        value = v;
        width = w;
        height = h;
        color = Color.gray;
        status = Status.NORMAL;
        name = String.valueOf(value);
    }
    public Bar(){
        value = Integer.MAX_VALUE;
    }

    public void draw(Graphics2D g, double xpos, double ypos){
        if(this.status == Status.NORMAL){
            setNewColor(Color.gray);
        } else if(this.status == Status.COMPAREFROM){
            setNewColor(Color.red);
        } else if(this.status == Status.PIVOT){
            setNewColor(Color.blue);
        } else {
            setNewColor(Color.orange);
        }
        g.setColor(this.color);
        g.fillRect((int)xpos,(int)(ypos-this.height),(int)this.width,(int)this.height);
    }
    public void drawValue(Graphics2D g, double xpos, double ypos){
        if(this.status == Status.NORMAL){
            g.setFont(new Font(null,Font.PLAIN,(int)(this.width/3)));
        } else{
            g.setFont(new Font(null,Font.BOLD,(int)(this.width/3+4)));
        }
        g.drawString( name, (int)(xpos+(this.width/2)-(g.getFontMetrics().stringWidth(String.valueOf(this.value)))/2), (int)(ypos+g.getFont().getSize()));
    }

    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
    public void setStatusCompareFrom(){
        this.status = Status.COMPAREFROM;
    }
    public void setStatusCompareTo(){
        this.status = Status.COMPARETO;
    }

    public void setStatusNormal(){
        this.status = Status.NORMAL;
    }
    public void setStatusPivot(){
        this.status = Status.PIVOT;
    }
    public Status getStatus() {
        return status;
    }
    public void setNewColor(Color color) {
        this.color = color;
    }
}
