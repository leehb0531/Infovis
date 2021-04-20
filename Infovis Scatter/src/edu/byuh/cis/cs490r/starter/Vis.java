package edu.byuh.cis.cs490r.starter;

import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


public class Vis extends JPanel implements MouseListener, MouseMotionListener {

    private Ellipse2D.Double mousePoint;
    private Rectangle box;
    private Point corner;
    private List<Point2D> scatterData;
    private List<Point2D> relativeScatterData;
    private List<Point2D> coordinates;
    private List<Point2D> zoomCoordinates;
    double maxX;
    double maxY;

    public double getZoomXmin() {
        return zoomXmin;
    }

    public void setZoomXmin(double zoomXmin) {
        this.zoomXmin = zoomXmin;
    }

    public double getZoomXmax() {
        return zoomXmax;
    }

    public void setZoomXmax(double zoomXmax) {
        this.zoomXmax = zoomXmax;
    }

    public double getZoomYmin() {
        return zoomYmin;
    }

    public void setZoomYmin(double zoomYmin) {
        this.zoomYmin = zoomYmin;
    }

    public double getZoomYmax() {
        return zoomYmax;
    }

    public void setZoomYmax(double zoomYmax) {
        this.zoomYmax = zoomYmax;
    }

    double zoomXmin;
    double zoomXmax;
    double zoomYmin;
    double zoomYmax;
    double xmin;
    double xmax;
    double ymin;
    double ymax;
    boolean zoom;
    private String xlabel;
    private String ylabel;
    private Rectangle theBox;

    public Vis() {
        super();
        maxX = 0;
        maxY = 0;
        zoomXmin=0;
        zoomXmax=0;
        zoomYmin=0;
        zoomYmax=0;
        xmin=0;
        xmax=0;
        ymin=0;
        ymax=0;
        relativeScatterData = new ArrayList<>();
        scatterData = new ArrayList<>();
        coordinates = new ArrayList<>();
        zoomCoordinates = new ArrayList<>();
        mousePoint = new Ellipse2D.Double();
        addMouseListener(this);
        addMouseMotionListener(this);
        box = null;
        zoom = false;
    }

    public void setData(List<Point2D> datas) {
        relativeScatterData.clear();
        scatterData = datas;
        maxX = 0;
        maxY = 0;
        for (var data : datas) {
            if (data.getX() > maxX) {
                maxX = data.getX();
            }
            if (data.getY() > maxY) {
                maxY = data.getY();
            }
        }
        for (var data : datas) {
            var relativeXY = new Point2D.Double(data.getX() / maxX, data.getY() / maxY);
            relativeScatterData.add(relativeXY);
        }
        repaint();
    }

    public void reset(int w, int h){
        setXmin(w*0.1);
        setXmax(w*0.9);
        setYmin(h*0.1);
        setYmax(h*0.9);
    }
    @Override
    public void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;

        //draw blank background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        //render visualization
        g.setColor(Color.BLACK);

        final int h = getHeight();
        final int w = getWidth();

        if (!zoom) {
            reset(w, h);
            //draw each dot
            coordinates.clear();

            for (var relative : relativeScatterData) {
                int x = (int) (xmin + (relative.getX() * (xmax - xmin)));
                int y = (int) (ymax - (relative.getY() * (ymax - ymin)));
                g.fillRect(x - 2, y - 2, 4, 4);
                ;
                coordinates.add(new Point2D.Double(x, y));
            }
            if(!relativeScatterData.isEmpty()) {
                //draw x and y axis
                g.drawLine((int) xmin, (int) ymax, (int) xmax, (int) ymax);
                g.drawLine((int) xmin, (int) ymin, (int) xmin, (int) ymax);
                //lable x and y axis
                int j = 1;
                for (int i = 4; i > 0; i--) {
                    xlabel = String.format("%.2f", j * maxX / 4);
                    ylabel = String.format("%.2f", i * maxY / 4);
                    g.drawString(xlabel, (int) (xmin+(xmax-xmin) / 4 * j-g.getFontMetrics().stringWidth(xlabel)/2), (int) ymax + 10);
                    g.drawString(ylabel, (int) xmin - g.getFontMetrics().stringWidth(ylabel), (int) ((ymax-ymin) / 4 * j - ymin));
                    j++;
                }
            }

        } else {
            reset(w,h);
            if(!relativeScatterData.isEmpty()) {
                //draw x and y axis
                g.drawLine((int) xmin, (int) ymax, (int) xmax, (int) ymax);
                g.drawLine((int) xmin, (int) ymin, (int) xmin, (int) ymax);

                //lable x and y axis
                int k = 0;
                double scaleZoomX= 0;
                double scaleZoomY = 0;
                for (int i = 4; i > 0; i--) {
                    scaleZoomX += Math.abs(zoomXmax-zoomXmin)/4;
                    xlabel = String.format("%.2f", zoomXmin + scaleZoomX);
                    ylabel = String.format("%.2f", zoomYmin-scaleZoomY);
                    g.drawString(xlabel, (int) (xmin+((xmax-xmin) / 4 * (k+1))-g.getFontMetrics().stringWidth(xlabel)), (int) ymax + 10);
                    g.drawString(ylabel, (int) xmin - g.getFontMetrics().stringWidth(ylabel), (int) (ymin + (ymax-ymin)/4*k));
                    k++;
                    scaleZoomY += Math.abs(zoomYmin-zoomYmax)/4;
                }

                // draw dots
                for(var dot: coordinates){
                    int x = (int)(xmin+(dot.getX()-getBox().getX())/(getBox().getWidth())*(xmax-xmin));
                    int y = (int)(ymin +(dot.getY()-getBox().getY())/(getBox().getHeight())*(ymax-ymin));
                    g.fillRect(x-2,y-2,4,4);
                    zoomCoordinates.add(new Point2D.Double(x,y));
                }
            }
        }

        //draw container when drag mouse
        if(box != null){
            g.draw(box);
        }
        //draw mouse pointer during mouse's move
        if(mousePoint != null){
            g.draw(mousePoint);
        }
    }
    private void setXmax(double max){
        xmax = max;
    }
    private void setYmax(double max){
        ymax = max;
    }
    private void setXmin(double min){
        xmin = min;
    }
    private void setYmin(double min) { ymin = min; }
    private double getXmax(){return xmax;}
    private double getXmin(){return xmin;}
    private double getYmax(){return ymax;}
    private double getYmin(){return ymin;}

    private void setBox(Rectangle box){
         theBox=box;
    }
    private Rectangle getBox(){
        return theBox;
    }
        @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        corner = new Point(e.getX(), e.getY());
        //create a new rectangle anchored at "corner"
        box = new Rectangle(corner);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //not coordinates but values for zoom
        zoomXmax = (((box.getX()+box.getWidth())-xmin)/(xmax-xmin))*(maxX);
        zoomYmax = ((ymax-(box.getY()+box.getHeight()))/(ymax-ymin))*(maxY);
        zoomXmin = ((box.getX()-xmin)/(xmax-xmin))*(maxX);
        zoomYmin = (ymax-box.getY())/(ymax-ymin)*(maxY);
        List<Point2D> dump = new ArrayList<>();
        for(var eachDot: coordinates){
            if(!box.contains(eachDot)){
                dump.add(eachDot);
            }
        }
        coordinates.removeAll(dump);
        dump.clear();
        setBox(box);
        box = null;
        zoom = true;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mousePoint = new Ellipse2D.Double();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mousePoint = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        box.setFrameFromDiagonal(corner.x, corner.y, x, y);
        repaint();
    }
    // extra credit parts
    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        mousePoint.setFrameFromCenter(x,y,x+3,y-3);
        if(zoom){
            for(var dot: zoomCoordinates){
                if(mousePoint.contains(dot.getX()-2,dot.getY()-2)){
                    System.out.println("zoom got dot right");
                }
            }
        } else{
            for(var dot: coordinates){
                if(mousePoint.contains(dot.getX()-2,dot.getY()-2)){
                    double originX = (dot.getX()-getXmin())/(getXmax()-getXmin())*maxX;
                    double originY = (getYmax()-dot.getY())/(getYmax()-getYmin())*maxY;
                    setToolTipText("You got the dot X " + String.format("%.2f",originX)+" : Y "+String.format("%.2f",originY));
                }
            }
        } repaint();
    }
}
