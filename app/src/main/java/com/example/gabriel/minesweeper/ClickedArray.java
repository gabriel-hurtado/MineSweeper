package com.example.gabriel.minesweeper;

public class ClickedArray {
    int[] clickedTable;
    int[][] saveTime;
    int size;
    int num;

    public ClickedArray(int size){
        clickedTable = new int[size*size];
        saveTime = new int[size*size][2];
        this.size = size;
        num = 0;
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
        num = nb;
    }

    public int[] getClicked(){
        int[] temp = new int[num];

        if (num != 0) {
            for (int i = 0; i < num; i++) {
                temp[i] = clickedTable[i];
            }
        }

        return temp;
    }

    public void clicked(int position, int minutes, int seconds) {
        clickedTable[num] = position;
        saveTime[num][0] = minutes;
        saveTime[num][1] = seconds;

        num += 1;
    }

    public String boxesInfo() {
        String s = "";
        for (int i = 0; i < num; i++) {
            int numberOfLine = clickedTable[i] / size + 1;
            int numberOfColumn = clickedTable[i] % size + 1;
            s += "Box selected = (" + numberOfLine + ", " + numberOfColumn + ")" + "\n" + saveTime[i][0] + " : " + saveTime[i][1] + "\n";
        }
        return s;
    }


}
