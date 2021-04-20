package com.company;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Node {

    private File file;
    private String path;
    private float size;
    private ArrayList<Node> children;
    private String colorTheme;
    private long dateOfFile;
    private int left,top,width,height;

    public Node(){}
    public Node(File f, String theme){
        file = f;
        colorTheme = theme;
        path = f.getPath();
        size=0;
        children = new ArrayList<>();
        dateOfFile = file.lastModified()%255;
        left = 0;
        top = 0;
        width = 0;
        height = 0;
        try{
            if(f.isFile()){
                size = f.length(); // in Kilobytes
            } else if(f.isDirectory()){
                File[] kids = f.listFiles();
                for(var k: kids){
                    Node n = new Node(k,theme);
                    children.add(n);
                    size += n.getSize();
                }
            }
        } catch(NullPointerException e){
            System.out.println("NullPointerException Caught");
        }
    }

    //draw node
    public void draw(Graphics2D g, int x, int y, int w, int h, String orientation) throws IOException {
        this.setLeft(x);
        this.setTop(y);
        this.setWidth(w);
        this.setHeight(h);
        if (this.file.isFile()){ // if it is file
            decideColorTheme(g, this.colorTheme);
            g.fillRect(x,y,w,h);
        } else{ // if it is folder
            if( orientation.equals("HORIZONTAL")){
                double pixelPerByte = w/size;
                for(var child: children){
                    double childWidth = pixelPerByte*child.size;
                    child.draw(g,x,y,(int)childWidth,h,"VERTICAL");
                    x += childWidth+0.4;
                }
            } else{ // for vertical
                double pixelPerByte = h/size;
                for(var child: children){
                    double childHeight = pixelPerByte*child.size;
                    child.draw(g,x,y,w,(int)childHeight,"HORIZONTAL");
                    y+= childHeight+0.2;
                }
            }
        }
    }

    public Node getNodeAt(int x,int y){
        Node result = this;
        for (var c: this.children){
            if (x>c.left && y>c.top && x<c.left+c.width && y<c.top+c.height){
                result = c.getNodeAt(x,y);
            }
        }
        return result;
    }

    private void decideColorTheme(Graphics2D g,String ct) throws IOException {
        if(ct.equals("type")){//documents, spreadsheets, slideshows, plain text, executables, source code, object code, images, audio.
            System.out.println("Path is : " + this.path);
            if(this.path.endsWith(".png")||this.path.endsWith(".jpg")){
                g.setColor(Color.CYAN);
            } else if(this.path.endsWith(".txt")){
                g.setColor(Color.orange);
            } else if(this.path.endsWith(".xlsx")){
                g.setColor(Color.green);
            } else if(this.path.endsWith(".doc")||this.path.endsWith(".docx")||this.path.endsWith(".pdf")){
                g.setColor(Color.red);
            } else if(this.path.endsWith(".ppt")){
                g.setColor(Color.blue);
            } else if(this.path.endsWith(".in")||this.path.endsWith(".ans")||this.path.endsWith(".spring")||this.path.endsWith(".sh")||this.path.endsWith(".bat")||this.path.endsWith(".exe")){
                g.setColor(Color.yellow);
            } else if(this.path.endsWith(".py")||this.path.endsWith(".java")||this.path.endsWith(".cpp")||this.path.endsWith(".class")){
                g.setColor(Color.WHITE);
            } else if(this.path.endsWith(".zip")||this.path.endsWith(".nib")){
                g.setColor(Color.pink);
            } else if(this.path.endsWith(".rtf")||this.path.endsWith(".car")||this.path.endsWith(".aiff")||this.path.endsWith(".aif")||this.path.endsWith(".pem")){
                g.setColor(new Color(132,124,53));
            } else{
                g.setColor(Color.black);
            }
        } else if(ct.equals("age")){
            g.setColor(new Color((int)getdateOfFile(),(int)getdateOfFile(),(int)getdateOfFile()));
        } else if(ct.equals("random")){
            Random rand = new Random();
            float r = rand.nextFloat();
            float gr = rand.nextFloat();
            float b = rand.nextFloat();
            g.setColor(new Color(r,gr,b));
        } else { // color theme = none
            g.setColor(Color.white);
        }
    }
    public String showPathnSize(){
        return this.path+" : "+size+" bytes";
    }

    // getter and setter
    public float getSize() {
        return size;
    }
    public File getFile() {
        return file;
    }
    public String getPath() {
        return path;
    }
    public void setColorTheme(String theme){
        colorTheme = theme;
    }
    public int getLeft() {
        return left;
    }
    public ArrayList<Node> getChildren() {
        return children;
    }
    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public String getColorTheme() {
        return colorTheme;
    }
    public long getdateOfFile() {
        return dateOfFile;
    }
}