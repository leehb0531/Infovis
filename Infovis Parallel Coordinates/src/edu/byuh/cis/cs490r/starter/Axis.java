package edu.byuh.cis.cs490r.starter;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Axis {
    String columnName;
    //what type is it? (string, numbers)
    ArrayList<Object> data;
    private Line2D.Double geometry;

    public Axis(String name) {
        columnName = name;
        data = new ArrayList<>();
    }

    public void extractData(ResultSet rs) {
        try {
            Object item = rs.getObject(columnName);
            data.add(item);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void debug() {
        System.out.println("PRINTING DATA FOR COLUMN " + columnName);
        for (var d : data) {
            System.out.println(d);
        }
    }
    public void setGeometry(double x, double y, double w, double h) {
        geometry = new Line2D.Double(x, y, w, h);
    }

    public void draw(Graphics2D g) {
        g.draw(geometry);
    }

    public Point2D.Double getPointAT(Graphics2D g,int i){
//        System.out.print("When index "+ i+", the this.data.get(i): "+this.data.get(i));
        double yAxis = 0;
        double max = 0;

        List<String> dummy;
        if (this.data.get(i) instanceof String){
            dummy = new ArrayList<>();
            for(var d : this.data){
                if(!dummy.contains(d.toString())){
                    dummy.add(d.toString());
                }
            }
            max = dummy.size();
            yAxis=(max-(dummy.indexOf(this.data.get(i)))%max)*(geometry.y2-geometry.y1)/max-(geometry.y1/max);
//            System.out.print("--- this.data.indexOf(string): "+this.data.indexOf(this.data.get(i))+" , max = "+ max);
        } else {
            for(var d: data){
                if(Double.parseDouble(String.valueOf(d))>max){
                    max = Double.parseDouble(String.valueOf(d));
                }
            }
            yAxis = (max- Double.parseDouble(String.valueOf(this.data.get(i)))) / max * (geometry.y2 - geometry.y1)+geometry.y1;
//            System.out.print("---- this "+this.data.get(i).getClass().getName()+" this.data.get(i): " + String.valueOf(this.data.get(i)) +" , max = "+ max);
        }
//        System.out.println(": yAxis = "+ yAxis);
        return new Point2D.Double(geometry.x1,yAxis);
    }
}
