package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.abs;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numInput=sc.nextInt();
        Integer[] inputInt =new Integer[numInput];
        ArrayList<Integer> dummy = new ArrayList<>();
        ArrayList<Integer> delete = new ArrayList<>();
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> reDup = new ArrayList<>();

        for(int i=0;i<numInput;i++){
            inputInt[i]=sc.nextInt();
        }
        Arrays.sort(inputInt);

        for (int i=0; i<numInput-1; i++){
            if (abs(inputInt[i]-inputInt[i+1]) == 1){
                dummy.add(inputInt[i]);
                delete.add(inputInt[i+1]);
            } else {
                dummy.add(inputInt[i]);
                dummy.add(inputInt[i+1]);
            }
        }
        for(var i : dummy){
            if(!reDup.contains(i)){
                reDup.add(i);
            }
        }
        for(var i : reDup){
            if(!delete.contains(i)){
                result.add(i);
            }
        }
        int sum = 0;
        for (int value : result) {
            sum += value;
        }
        System.out.println(sum);
	// write your code here
    }
}
