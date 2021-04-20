package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.Naming;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class Main extends JFrame{

    private Vis mainPanel;
    Dimension screenSize;
    int result;
    private Timer timer;
    private TimerTask task;
    private int delaying;
//    private boolean stopped;

    public Main(){
        delaying = 100; // 1s
        JMenuBar menuBar = setupMenu();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setJMenuBar(menuBar);
        mainPanel = new Vis();
        setContentPane(mainPanel);

        setSize(800,600);
        setLocation(screenSize.width/2-getWidth()/2, screenSize.height/2-getHeight()/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sorting Algorithm");
        setVisible(true);

        result = -1;
//        stopped = false;
    }

    // Set up MenuBar*******************************
    private JMenuBar setupMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("File");
        JMenuItem openFile = new JMenuItem("Open data file");
        JMenu menu2 = new JMenu("Algorithm");
        JMenuItem bubbleBT = new JMenuItem("Bubble Sort");
        JMenuItem quickBT = new JMenuItem("Quick Sort");
        JMenuItem heapBT = new JMenuItem("Heap Sort");
        JMenuItem cocktailBT = new JMenuItem("Cocktail Sort");
        JMenuItem mergeBT = new JMenuItem("Merge Sort");
        JMenuItem insertBT = new JMenuItem("Insertion Sort");
        JMenuItem selectBT = new JMenuItem("Selection Sort");
        JMenuItem pancakeBT = new JMenuItem("Pancake Sort");
        JMenuItem cycleBT = new JMenuItem("Cycle Sort");
        JMenuItem gnomeBT = new JMenuItem("Gnome Sort");

//        JMenuItem stop = new JMenuItem("Stop");

        // open data file and give it to Vis.java
        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.blank();
                String filePath = "/Users/hongbinlee/Project/CS490r/Infovis_Sorting";
                JFileChooser fc = new JFileChooser(filePath);
                if(e.getSource() == openFile){
                    result = fc.showOpenDialog(getParent());
                    if (result == JFileChooser.APPROVE_OPTION){
                        File selectedFile = fc.getSelectedFile();
                        fileList(selectedFile);
                    }
                    else{
                        System.out.println("Could not read the file.");
                    }
                }
                mainPanel.repaint();
            }
        });

        // button for bubble sort
        bubbleBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(result!=-1) {
                    timer = new Timer();
                    mainPanel.reset();
                    mainPanel.setSorted(true);
                    mainPanel.setSortName("bubble");
                    task = new TimerTask() {
                        int counter = 1;
                        @Override
                        public void run() {
                            if(counter ==2){
                                System.out.println("cancel");
                                timer.cancel();
                            } else {
                                System.out.println("Bubble Sort Begin");
                                bubbleTask();
                                counter++;
                            }
                        }
                    };
                    timer.scheduleAtFixedRate(task, 0, delaying);
                } else{
                    System.out.println("please open data file first");
                }
            }
        });
        // button for quick sort
        quickBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(result!=-1) {
                    timer = new Timer();
                    mainPanel.reset();
                    mainPanel.setSorted(true);
                    mainPanel.setSortName("quick");
                    task = new TimerTask() {
                        int counter = 1;
                        @Override
                        public void run() {
                            if(counter == 2){
                                timer.cancel();
                            } else {
                                System.out.println("Quick Sort Begin");
                                quickTask(mainPanel.getBarList(),0,mainPanel.getBarList().size()-1);
                                counter++;
                            }
                        }
                    };
                    timer.scheduleAtFixedRate(task, 0, delaying);
                } else{
                    System.out.println("please open data file first");
                }
            }
        });
        // button for heap sort
        heapBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(result!=-1) {
                    timer = new Timer();
                    mainPanel.reset();
                    mainPanel.setSorted(true);
                    mainPanel.setSortName("heap");
                    task = new TimerTask() {
                        int counter = 1;
                        @Override
                        public void run() {
                            if(counter == 2){
                                timer.cancel();
                            } else {
                                System.out.println("Heap Sort Begin");
                                heapTask(mainPanel.getBarList(),mainPanel.getBarList().size());
                                counter++;
                            }
                        }
                    };
                    timer.scheduleAtFixedRate(task, 0, delaying);
                } else{
                    System.out.println("please open data file first");
                }
            }
        });
        //button for cocktail sort
        cocktailBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(result!=-1){
                    timer = new Timer();
                    mainPanel.reset();
                    mainPanel.setSorted(true);
                    mainPanel.setSortName("cocktail");
                    task = new TimerTask() {
                        int counter = 1;
                        @Override
                        public void run() {
                            if(counter == 2){
                                timer.cancel();
                            } else {
                                cocktailTask(mainPanel.getBarList());
                                counter++;
                            }
                        }
                    };
                    timer.scheduleAtFixedRate(task,0,delaying);
                } else {
                    System.out.println("please open data file first");
                }
            }
        });
        // button for merge sort
        mergeBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(result != -1){
                    timer = new Timer();
                    mainPanel.reset();
                    mainPanel.setSorted(true);
                    mainPanel.setSortName("merge");
                    task = new TimerTask() {
                        int counter = 1;
                        @Override
                        public void run() {
                            if(counter == 2){
                                timer.cancel();
                            } else {
                                mergeTask(mainPanel.getBarList(),0,mainPanel.getBarList().size()-1);
                                counter++;
                            }
                        }
                    };
                    timer.scheduleAtFixedRate(task,0,delaying);
                } else {
                    System.out.println("please open data file first");
                }
            }
        });// end merge sort
        // button for insertion sort
        insertBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(result != -1){
                    timer = new Timer();
                    mainPanel.reset();
                    mainPanel.setSorted(true);
                    mainPanel.setSortName("insertion");
                    task = new TimerTask() {
                        int counter = 1;
                        @Override
                        public void run() {
                            if(counter == 2){
                                timer.cancel();
                            } else {
                                insertTask(mainPanel.getBarList());
                                counter++;
                            }
                        }
                    };
                    timer.scheduleAtFixedRate(task,0,delaying);
                } else {
                    System.out.println("please open data file first");
                }
            }
        });
        selectBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(result != -1){
                    timer = new Timer();
                    mainPanel.reset();
                    mainPanel.setSorted(true);
                    mainPanel.setSortName("selection");
                    task = new TimerTask() {
                        int counter = 1;
                        @Override
                        public void run() {
                            if(counter == 2){
                                timer.cancel();
                            } else {
                                selectTask(mainPanel.getBarList());
                                counter++;
                            }
                        }
                    };
                    timer.scheduleAtFixedRate(task,0,delaying);
                } else {
                    System.out.println("please open data file first");
                }
            }
        });
        pancakeBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(result != -1){
                    timer = new Timer();
                    mainPanel.reset();
                    mainPanel.setSorted(true);
                    mainPanel.setSortName("pancake");
                    task = new TimerTask() {
                        int counter = 1;
                        @Override
                        public void run() {
                            if(counter == 2){
                                timer.cancel();
                            } else {
                                pancakeTask(mainPanel.getBarList(),mainPanel.getBarList().size());
                                counter++;
                            }
                        }
                    };
                    timer.scheduleAtFixedRate(task,0,delaying);
                } else {
                    System.out.println("please open data file first");
                }
            }
        });
        cycleBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(result != -1){
                    timer = new Timer();
                    mainPanel.reset();
                    mainPanel.setSorted(true);
                    mainPanel.setSortName("cycle");
                    task = new TimerTask() {
                        int counter = 1;
                        @Override
                        public void run() {
                            if(counter == 2){
                                timer.cancel();
                            } else {
                                cycleTask(mainPanel.getBarList(),mainPanel.getBarList().size());
                                counter++;
                            }
                        }
                    };
                    timer.scheduleAtFixedRate(task,0,delaying);
                } else {
                    System.out.println("please open data file first");
                }
            }
        });
        gnomeBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(result != -1){
                    timer = new Timer();
                    mainPanel.reset();
                    mainPanel.setSorted(true);
                    mainPanel.setSortName("gnome");
                    task = new TimerTask() {
                        int counter = 1;
                        @Override
                        public void run() {
                            if(counter == 2){
                                timer.cancel();
                            } else {
                                gnomeTask(mainPanel.getBarList(),mainPanel.getBarList().size());
                                counter++;
                            }
                        }
                    };
                    timer.scheduleAtFixedRate(task,0,delaying);
                } else {
                    System.out.println("please open data file first");
                }
            }
        });

        menu1.add(openFile);
        menu2.add(bubbleBT);
        menu2.add(quickBT);
        menu2.add(heapBT);
        menu2.add(cocktailBT);
        menu2.add(mergeBT);
        menu2.add(insertBT);
        menu2.add(selectBT);
        menu2.add(pancakeBT);
        menu2.add(cycleBT);
        menu2.add(gnomeBT);
        menuBar.add(menu1);
        menuBar.add(menu2);
//        menuBar.add(stop);

        return menuBar;
    }
    //save data into an array and pass it to Vis.java. this method is called in openFile.actionListener
    private void fileList(File selFile){
        List<Integer> list = new ArrayList<>();
        try {
            Scanner sc = new Scanner(selFile);
            while(sc.hasNextInt()) {
                int data = sc.nextInt();
                list.add(data);
            }
            mainPanel.setDataList(list);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    // bubble sort data list******************
    public void bubbleTask(){
        boolean swaped = false;
        for (int i = 0; i < mainPanel.getBarList().size()-1; i++) {
            for (int j = 0; j < mainPanel.getBarList().size()-1;j++){
                mainPanel.getBarList().get(j).setStatusCompareFrom();
                mainPanel.getBarList().get(j+1).setStatusCompareTo();
//                System.out.println("From: "+j+", To: "+ (j+1));
                mainPanel.repaint();
                delay(delaying);
                if(mainPanel.getBarList().get(j).getValue()>mainPanel.getBarList().get(j+1).getValue()){
                    Collections.swap(mainPanel.getBarList(),j,j+1);
                    swaped = true;
                    mainPanel.repaint();
//                    System.out.println("swaped");
                    delay(delaying);
                } else{
//                    System.out.println("No swap");
                }
                mainPanel.getBarList().get(j).setStatusNormal();
                mainPanel.getBarList().get(j+1).setStatusNormal();
            }
            if(!swaped){
                break;
            }
        }
        repaint();
    }// end bubble sort*********
    //quick sort********************
    private void quickTask(List<Bar> arr, int low, int high){
        if(low < high){
            int pi = partition(arr,low,high);
            quickTask(arr,low,pi-1);
            quickTask(arr,pi+1,high);
        }
        mainPanel.repaint();
    }
    private int partition(List<Bar> arr, int low, int high){
        int pivot = arr.get(high).getValue();
        arr.get(high).setStatusPivot();
        int i = low - 1;
        for(int j = low; j<=high-1;j++){
            if(arr.get(j).getValue() <= pivot){
                arr.get(j).setStatusCompareFrom();
                mainPanel.repaint();
                delay(delaying);
                i++;
                if(i != j) {
                    arr.get(i).setStatusCompareFrom();
                    arr.get(j).setStatusCompareTo();
                    mainPanel.repaint();
                    delay(delaying);
                    Collections.swap(mainPanel.getBarList(), i, j);
                    mainPanel.repaint();
                    delay(delaying);
                    arr.get(i).setStatusNormal();
                    arr.get(j).setStatusNormal();
                    mainPanel.repaint();
                    delay(delaying);
                } else{
                    arr.get(i).setStatusCompareFrom();
                    mainPanel.repaint();
                    delay(50);
                    arr.get(j).setStatusCompareTo();
                    mainPanel.repaint();
                    delay(50);
                    arr.get(i).setStatusNormal();
                    arr.get(j).setStatusNormal();
                    mainPanel.repaint();
                    delay(50);
                }
            }
        }
        arr.get(i+1).setStatusCompareFrom();
        mainPanel.repaint();
        delay(delaying);
        Collections.swap(mainPanel.getBarList(),i+1,high);
        mainPanel.repaint();
        delay(delaying);
        arr.get(i+1).setStatusNormal();
        arr.get(high).setStatusNormal();
        return i+1;
    }//end quick sort******
    // heap sort***************
    public void heapTask(List<Bar> arr, int n){
        for(int i = n/2-1; i>=0; i--){
            heapify(arr,n,i);
        }
        for(int i = n-1; i>=0;i--){
            arr.get(0).setStatusCompareFrom();
            arr.get(i).setStatusCompareTo();
            mainPanel.repaint();
            delay(delaying);
            Collections.swap(arr, 0, i);
            mainPanel.repaint();
            delay(delaying);
            arr.get(0).setStatusNormal();
            arr.get(i).setStatusNormal();
            heapify(arr,i,0);
        }
        mainPanel.repaint();
    }
    public void heapify(List<Bar> arr, int n, int i){
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n && arr.get(l).getValue() > arr.get(largest).getValue())
            largest = l;

        // If right child is larger than largest so far
        if (r < n && arr.get(r).getValue() > arr.get(largest).getValue())
            largest = r;

        // If largest is not root
        if (largest != i) {
            arr.get(i).setStatusCompareFrom();
            arr.get(largest).setStatusCompareTo();
            mainPanel.repaint();
            delay(delaying);
            Collections.swap(arr, i, largest);
            mainPanel.repaint();
            delay(delaying);
            arr.get(i).setStatusNormal();
            arr.get(largest).setStatusNormal();
            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    } // end heap sort *******
    //cocktail sort********************
    public void cocktailTask(List<Bar> a){
        boolean swapped = true;
        int start = 0;
        int end = a.size();

        while (swapped == true)
        {
            // reset the swapped flag on entering the
            // loop, because it might be true from a
            // previous iteration.
            swapped = false;

            // loop from bottom to top same as
            // the bubble sort
            for (int i = start; i < end - 1; ++i)
            {
                a.get(i).setStatusCompareFrom();
                a.get(i+1).setStatusCompareTo();
                mainPanel.repaint();
                delay(delaying);
                if (a.get(i).getValue() > a.get(i+1).getValue()) {
                    Collections.swap(a, i, i+1);
                    mainPanel.repaint();
                    delay(delaying);
                    swapped = true;
                }
                a.get(i).setStatusNormal();
                a.get(i+1).setStatusNormal();
            }

            // if nothing moved, then array is sorted.
            if (swapped == false)
                break;

            // otherwise, reset the swapped flag so that it
            // can be used in the next stage
            swapped = false;

            // move the end point back by one, because
            // item at the end is in its rightful spot
            end = end - 1;

            // from top to bottom, doing the
            // same comparison as in the previous stage
            for (int i = end - 1; i >= start; i--)
            {
                a.get(i).setStatusCompareFrom();
                a.get(i+1).setStatusCompareTo();
                mainPanel.repaint();
                delay(delaying);
                if (a.get(i).getValue() > a.get(i+1).getValue())
                {
                    Collections.swap(a, i, i+1);
                    mainPanel.repaint();
                    delay(delaying);
                    swapped = true;
                }
                a.get(i).setStatusNormal();
                a.get(i+1).setStatusNormal();
            }

            // increase the starting point, because
            // the last stage would have moved the next
            // smallest number to its rightful spot.
            start = start + 1;
        }
        mainPanel.repaint();
    }// end cocktail sort ******
    // merge partially
    public void merge(List<Bar> arr, int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        Bar[] L = new Bar[n1+1];
        Bar[] R = new Bar[n2+1];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i]=arr.get(l+i);
        for (int j = 0; j < n2; ++j)
            R[j]=arr.get(m + 1 + j);

        L[n1] = new Bar();
        R[n2] = new Bar();

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        for(int k = l; k<=r;k++){
            if(L[i].getValue()<=R[j].getValue()){
                Bar dummy =arr.get(k);
                L[i].setStatusPivot();
                R[j].setStatusCompareTo();
                repaint();
                delay(delaying);

                arr.set(k,L[i]);
                arr.set(arr.indexOf(L[i]),dummy);

                arr.get(k).setStatusCompareTo();
                arr.get(arr.indexOf(L[i])).setStatusPivot();
                repaint();
                delay(delaying);
                dummy.setStatusNormal();
                arr.get(arr.indexOf(L[i])).setStatusNormal();
                i++;
            } else {
                Bar dummy =arr.get(k);

                L[i].setStatusCompareTo();
                R[j].setStatusPivot();
                repaint();
                delay(delaying);

                arr.set(arr.indexOf(R[j]),dummy);
                arr.set(k,R[j]);

                arr.get(k).setStatusCompareTo();
                arr.get(arr.indexOf(R[j])).setStatusPivot();
                repaint();
                delay(delaying);

                R[j].setStatusNormal();
                arr.get(arr.indexOf(R[j])).setStatusNormal();
                j++;
            }
        }
    }// finish merge partially

    // merge sort****************
    void mergeTask(List<Bar> arr, int l, int r)
    {
        if (l < r) {
            // Find the middle point
            int m =(l+r)/2;

            // Sort first and second halves
            mergeTask(arr, l, m);
            mergeTask(arr, m + 1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
            repaint();
        }
    }//end merge sort*********
    //insertion sort ****************
    void insertTask(List<Bar> arr)
    {
        for(int j=1; j<arr.size();j++ ){
            Bar key = arr.get(j);
            int i = j-1;
            arr.get(i).setStatusCompareFrom();
            key.setStatusPivot();
            repaint();
            delay(delaying);
            arr.get(i).setStatusNormal();
            while(i>=0&& arr.get(i).getValue()>key.getValue()){
                arr.get(i).setStatusCompareFrom();
                repaint();
                arr.set(i+1,arr.get(i));
                arr.set(i,key);
                repaint();
                delay(delaying);
                arr.get(i+1).setStatusNormal();
                i--;
            }
            arr.set(i+1,key);
            repaint();
            key.setStatusNormal();
            repaint();
        }

    }// end insertion sort***
    //selection sort **************
    public void selectTask(List<Bar> arr){
        int n = arr.size();
        for(int i=0; i < n-1;i++){
            int min = i;
            for(int j = i+1; j<n; j++){
                if(arr.get(j).getValue() < arr.get(min).getValue()){
                    min = j;
                }
            }
            arr.get(min).setStatusCompareFrom();
            arr.get(i).setStatusCompareTo();
            repaint();
            delay(delaying);
            Collections.swap(arr,min,i);
            repaint();
            delay(delaying);
            arr.get(min).setStatusNormal();
            arr.get(i).setStatusNormal();
        }
        repaint();
    }//end selection sort***
    public void pancakeTask(List<Bar> arr, int n){
        // Start from the complete
        // array and one by one
        // reduce current size by one
        for (int curr_size = n; curr_size > 1; --curr_size) {
            // Find index of the
            // maximum element in
            // arr[0..curr_size-1]
            int mi = findMax(arr, curr_size);

            // Move the maximum element
            // to end of current array
            // if it's not already at
            // the end
            if (mi != curr_size-1)
            {
                // To move at the end,
                // first move maximum
                // number to beginning
                flip(arr, mi);

                // Now move the maximum
                // number to end by
                // reversing current array
                flip(arr, curr_size-1);
            }
        }repaint();
    }
    /* Reverses arr[0..i] */
    public void flip(List<Bar> arr, int i) {
        int start = 0;
        while (start < i)
        {
            arr.get(i).setStatusCompareFrom();
            arr.get(start).setStatusCompareTo();
            repaint();
            delay(delaying);
            Collections.swap(arr,i,start);
            repaint();
            delay(delaying);
            arr.get(i).setStatusNormal();
            arr.get(start).setStatusNormal();
            start++;
            i--;
        }
    }

    // Returns index of the
    // maximum element in
    // arr[0..n-1]
    public int findMax(List<Bar> arr, int n) {
        int mi, i;
        for (mi = 0, i = 0; i < n; ++i)
            if (arr.get(i).getValue() > arr.get(mi).getValue()) {
                mi = i;
            }
        return mi;
    }
    //cycle sort ***********
    public void cycleTask(List<Bar> arr, int n){
        // count number of memory writes
        int writes = 0;

        // traverse array elements and put it to on
        // the right place
        for (int cycle_start = 0; cycle_start <= n - 2; cycle_start++) {
            // initialize item as starting point
            Bar item = arr.get(cycle_start);

            // Find position where we put the item. We basically
            // count all smaller elements on right side of item.
            int pos = cycle_start;
            for (int i = cycle_start + 1; i < n; i++)
                if (arr.get(i).getValue() < item.getValue()) {
                    pos++;
                }
            // If item is already in correct position
            if (pos == cycle_start) {
                continue;
            }
            // ignore all duplicate elements
            while (item.getValue() == arr.get(pos).getValue()) {
                pos += 1;
            }
            // put the item to it's right position
            if (pos != cycle_start) {
                arr.get(pos).setStatusCompareFrom();
                item.setStatusCompareTo();
                repaint();
                delay(delaying);
                Bar temp = item;
                item = arr.get(pos);
                arr.set(pos,temp);
                repaint();
                delay(delaying);
                arr.get(pos).setStatusNormal();
                item.setStatusNormal();
                writes++;
            }

            // Rotate rest of the cycle
            while (pos != cycle_start) {
                pos = cycle_start;

                // Find position where we put the element
                for (int i = cycle_start + 1; i < n; i++)
                    if (arr.get(i).getValue() < item.getValue()) {
                        pos += 1;
                    }
                // ignore all duplicate elements
                while (item.getValue() == arr.get(pos).getValue()) {
                    pos += 1;
                }
                // put the item to it's right position
                if (item != arr.get(pos)) {
                    arr.get(pos).setStatusCompareFrom();
                    item.setStatusCompareTo();
                    repaint();
                    delay(delaying);
                    Bar temp = item;
                    item = arr.get(pos);
                    arr.set(pos,temp);
                    repaint();
                    delay(delaying);
                    arr.get(pos).setStatusNormal();
                    item.setStatusNormal();
                    writes++;
                }
            }
        }repaint();
    }//cycle sort end*****
    //gnome sort(Stupid sort) *****************
    public void gnomeTask(List<Bar> arr, int n) {
        int index = 0;

        while (index < n) {
            if (index == 0)
                index++;
            if (arr.get(index).getValue() >= arr.get(index - 1).getValue()) {
                index++;
            } else {
                arr.get(index-1).setStatusCompareFrom();
                arr.get(index).setStatusCompareTo();
                repaint();
                delay(delaying);
                Collections.swap(arr,index-1, index);
                repaint();
                delay(delaying);
                arr.get(index-1).setStatusNormal();
                arr.get(index).setStatusNormal();
                index--;
            }
        }repaint();
    }//end gnome sort*****

    private void delay(int delay){
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //main
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
