package edu.byuh.cis.cs490r.starter;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;


public class Vis extends JPanel implements MouseListener, MouseMotionListener, ComponentListener {

    private List<Axis> axes;
    private Ellipse2D.Double seth;
    private Rectangle box;
    private Point corner;
    private List<Polyline> polyline;
    private Polyline selected;
    public boolean firstRun;
//    private boolean mouseIn;
//    Polyline selected;
    List<String> checkDuplicateString;
    public boolean resizing;
    public List<Polyline> selectedLines;
//    public List<Polyline> middleLines;
    public List<Polyline> dummyLines;
    public boolean forMoving;

    public Vis() {
        super();
        axes = new ArrayList<>();
        seth = new Ellipse2D.Double(50, 100, 40, 40);
        addComponentListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        box = new Rectangle();
        polyline = new ArrayList<>();
        firstRun = true;
        resizing = true;
        selected = null;
        selectedLines = new ArrayList<>();
        dummyLines = new ArrayList<>();
        forMoving = true;
//        mouseIn = true;
    }
    public List<Axis> getAxes() {
        return axes;
    }

    public void setAxes(List<Axis> ax) {
        axes = ax;
    }
    public void setFirstRun(boolean firstRun) {
        this.firstRun = firstRun;
    }
    public double getMaxValue(int index) {

        double maxY = 0;

        if(axes.get(index).data.get(0) instanceof String){
            checkDuplicateString = new ArrayList<>();
            for(var d :axes.get(index).data){
                if(!checkDuplicateString.contains(d.toString())){
                    checkDuplicateString.add(d.toString());
                }
            }
            maxY= checkDuplicateString.size();
        } else {
            double max = 0;
            for(var d: axes.get(index).data){
                if(Double.parseDouble(String.valueOf(d))>max){
                    max = Double.parseDouble(String.valueOf(d));
                }
            }
            maxY = max;
//            System.out.print("---- this(int)this.data.get(i): " + (int)this.data.get(i) +" , max = "+ max1);
        }
        return maxY;
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
        final int highH = (int)(h*0.1);
        final int lowH = (int)(h*0.9);
        final int w = getWidth();
        final double hlength = h*0.8;

        //find out how many rows there are


        double xPos = 0;
        final int numAxis = axes.size();
        for (int i = 0; i < numAxis; i++) {
            //draw parallel lines
            xPos += w / numAxis;
            axes.get(i).setGeometry((int) xPos - w / (2 * numAxis), highH, (int) xPos - w / (2 * numAxis), lowH);
            axes.get(i).draw(g);

            //draw column names for each x-axis
            String axeName = axes.get(i).columnName;
            g.setFont(new Font("SansSerf", Font.BOLD, 10)); // change font style
            g.drawString(axeName, (int) xPos - g.getFontMetrics().stringWidth(axeName) / 2 - w / (2 * numAxis), lowH + g.getFont().getSize());
        }
        //draw poly line

        if (!axes.isEmpty()) {
            int numRow = axes.get(1).data.size();
            if(resizing) {
                resizing = false;
                polyline.clear();
                for (int j = 0; j < numRow; j++) {
                    var line = new Polyline();
                    for (int k = 0; k < numAxis; k++) {
                        line.addPoint(axes.get(k).getPointAT(g, j));
                        polyline.add(line);
                    }
                }
            }
            for (var li : polyline) {
                li.draw(g);
            }
        }

        //draw standard y-axis
        xPos = 0;
        for (int i = 0; i < numAxis; i++) {
            xPos += w / numAxis;
            g.setColor(Color.red);
            double max = getMaxValue(i);
            if (axes.get(i).data.get(1) instanceof String) { // when it is string
                for (int j = 0; j < checkDuplicateString.size(); j++) {
                    g.drawString(String.valueOf(checkDuplicateString.get(j)), (int) xPos - w / (2 * numAxis) - g.getFontMetrics().stringWidth(checkDuplicateString.get(j)), (int) ((max - (checkDuplicateString.indexOf(checkDuplicateString.get(j)) % max)) * (lowH - highH) / max - (highH / max)));
                }
            } else { // when it is numeric
                for (int j = 0; j < 4; j++) {
                    g.drawString(String.format("%.1f", (4 - j) * max / 4), (int) xPos - w / (2 * numAxis) - g.getFontMetrics().stringWidth(String.valueOf((4 - j) * max / 4)), (int) (highH + j * hlength / 4));
                }
                g.drawString("0", (int) xPos - w / (2 * numAxis) - g.getFontMetrics().stringWidth("0"), lowH);
            }
            g.setColor(Color.black);
        }

        if(box != null){
            g.setColor(Color.blue);
            g.setStroke(new BasicStroke(1));
            g.draw(box);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        forMoving = true;
        resizing = false;
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        forMoving=false;
        selected = null;
        corner = new Point(e.getX(), e.getY());
        //create a new rectangle anchored at "corner"
        box = new Rectangle(corner);
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        box.setFrameFromDiagonal(corner.x, corner.y, x, y);

        if(firstRun){
            for(var li : polyline) {
                firstRun = false;
                li.invisible();
            }
        }
        for(var li: polyline){
            if (li.isIntersected(box)) {
                if(!li.isHighlighted()){
                    li.highlight();
                    selectedLines.add(li);
                    dummyLines.add(li);
                }
            }
        }
        for (var li: dummyLines){
            if(!li.isIntersected(box)){
                li.invisible();
                selectedLines.remove(li);
            }
        }

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dummyLines.clear();
        for(var li: polyline){
            if(selectedLines.contains(li)) {
                li.unhighlight();
            }else{
                li.invisible();
            }
        }
        selectedLines.clear();
        box = null;
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(forMoving) {
            double min = 1000;
            int x = e.getX();
            int y = e.getY();
//        Point2D.Double mouse = new Point2D.Double(x,y);
            for (var each : polyline) {
                each.unhighlight();
                double distance = each.getDistanceFromPoint(x, y);
//            double distance = each.getDistanceFromPoint(mouse);
                if (distance < min) {
                    min = (distance);
                    selected = each;
                }
            }
            int index = polyline.indexOf(selected);
            String toolTip = "";
            for(var ax: axes){
                toolTip += (ax.columnName+": "+ax.data.get(index/axes.size())+", ");
            }
            if(toolTip.length()>1) {
                setToolTipText(toolTip.substring(0, toolTip.length() - 2));
            }
            if (selected != null) {
                selected.highlight();
                repaint();
            }
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        resizing = true;
        repaint();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
