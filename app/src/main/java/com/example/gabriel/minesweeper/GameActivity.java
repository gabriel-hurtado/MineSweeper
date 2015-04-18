package com.example.gabriel.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GameActivity extends Activity {
    private Intent in;
    private Bundle infos;
    private Boolean timer;
    private double percentage;
    private int size;
    private String UserName;
    private grid gameGeneration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        in = getIntent();
        infos = in.getExtras();
        timer = infos.getBoolean("timer");
        percentage = infos.getDouble("percentage");
        size = infos.getInt("size");
        UserName = infos.getString("UserName");
        gameGeneration= new grid(size);
        gameGeneration.putMines(generateMines(percentage,size));


    }

    public class grid {
      private int[][] gridArray;

        public grid(int size){
           this.gridArray= new int[size][size];
         /*inizialize to 0*/
        }

        private void putMines(int[] minesArray){
          /*put the mines at their place*/
        }


    }

    public int[] generateMines(double percentage, int size){
        List<Integer> list = new ArrayList<Integer>();
        int[] listArray;
        int numberOfMines = (int) ((size*size)*percentage);
        for(int i = 0; i < numberOfMines-1;)
        {

            //Number between 0 and max
            int rand = ((int)(Math.random() * ((size*size)-1)));
            if(!list.contains(rand))
            {
                list.add(rand);
                i++;
            }
        }
        listArray= convertIntegers(list);
        return listArray;
    }

    public static int[] convertIntegers(List<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }



}
