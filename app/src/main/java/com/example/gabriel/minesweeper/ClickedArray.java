package com.example.gabriel.minesweeper;

/**
 * Created by Gabriel on 20/05/2015.
 */
public class ClickedArray {
    int[] clickedTable;
    int size;
    int num;
    public ClickedArray(int size){
        clickedTable=new int[size*size];
        this.size=size;
        num=0;

    }

    public int[] getArray(){
        return clickedTable;
    }
    public void writeArray(int[] arr){

        clickedTable=arr;
    }
    public int getNum(){
        return num;
    }
    public void writeNum(int nb){
        num=nb;
    }
    public int[] getClicked(){
        int[] temp= new int[num];

        if (num!=0) {
            for (int i = 0; i < num; i++) {
                temp[i] = clickedTable[i];
            }
        }

        return temp;
    }


    public void clicked(int position) {
        clickedTable[num]=position;
        num+=1;
    }
}
