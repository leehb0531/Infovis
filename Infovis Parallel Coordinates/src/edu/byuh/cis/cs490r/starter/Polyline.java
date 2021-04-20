package edu.byuh.cis.cs490r.starter;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Polyline /*implements Serializable, Comparable<Polyline>*/ {
    private GeneralPath gPath;
    private ArrayList<Point2D> points;
    private static Stroke regularLine = new BasicStroke(1);
    private static Stroke thickLine = new BasicStroke(5);
    private enum State {
            REGULAR,
        HIGHLIGHTED,
        INVISIBLE
    }
    private Color invisibleColor = new Color(230,230,230);
    State state = State.REGULAR;
    public Polyline(){
        points = new ArrayList<Point2D>();
        gPath = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
    }
    public void addPoint(Point2D point){
        addPointAt(point.getX(),point.getY());
    }
    public void addPointAt(Double x, Double y){
        if (points.isEmpty()) {
            gPath.moveTo(x, y);
        } else {
            gPath.lineTo(x, y);
        }
        points.add(new Point2D.Double(x,y));
    }
    public void draw(Graphics2D g){
        g.setStroke(regularLine);
        if(state == State.HIGHLIGHTED){
            g.setColor(Color.red);
            g.setStroke(thickLine);
        } else if (state == State.REGULAR){
            g.setColor(Color.black);
        } else{
            g.setColor(invisibleColor);
        }
        g.draw(gPath);
    }
    public Point2D getPointAt(final int index) {
        return points.get(index);
    }
    public int getNumPoints() {
        return points.size();
    }

    public double getDistanceFromPoint(int x, int y){
        double min = Double.MAX_VALUE;
        for(int i =1; i<points.size();i++){
            double x1 = points.get(i).getX();
            double y1 = points.get(i).getY();
            double x2 = points.get(i-1).getX();
            double y2 = points.get(i-1).getY();
            double dist = Line2D.ptSegDistSq(x1,y1,x2,y2,x,y);

            if(dist<min){
                min = dist;
            }
        }
        return min;
    }
    public boolean isIntersected(Rectangle2D r){
        boolean result = false;
        for(int i =1; i<points.size();i++) {
            double x1 = points.get(i).getX();
            double y1 = points.get(i).getY();
            double x2 = points.get(i - 1).getX();
            double y2 = points.get(i - 1).getY();
            Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
            if (line.intersects(r)) {
                result = true;
            }
        }
        return result;
    }

    public void highlight() {
        state = State.HIGHLIGHTED;
    }
    public void unhighlight() {
        state = State.REGULAR;
    }
    public void invisible() {
        state = State.INVISIBLE;
    }
    public boolean isInvisible() {
        return (state == State.INVISIBLE);
    }

    public boolean isHighlighted() {
        return (state == State.HIGHLIGHTED);
    }

    public boolean isNormal() {
        return (state == State.REGULAR);
    }
//    @Override
//    public int compareTo(Polyline other) {
//        if (isHighlighted()) {
//            return 1;
//        }
//        if (isInvisible()) {
//            return -1;
//        }
//        if (isNormal()) {
//            if (other.isHighlighted()) {
//                return -1;
//            }
//            if (other.isInvisible()) {
//                return 1;
//            }
//        }
//        return 0;
//    }

}
