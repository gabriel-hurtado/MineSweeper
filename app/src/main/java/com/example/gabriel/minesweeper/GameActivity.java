package com.example.gabriel.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

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
    private TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        t=(TextView)findViewById(R.id.myTextView);
        in = getIntent();
        infos = in.getExtras();
        timer = infos.getBoolean("timer");
        percentage = infos.getDouble("percentage");
        size = infos.getInt("size");
        UserName = infos.getString("UserName");
        gameGeneration= new grid(size);
        gameGeneration.putMines(generateMines(percentage,size));
        gameGeneration.addNumberOfMinesNear();
        gameGeneration.test();

    }

    public class grid {
      private int[][] gridArray;

        public grid(int size){
           this.gridArray= new int[size][size];
        }

        private void putMines(int[] minesArray){
          for(int mineSize: minesArray){
           this.gridArray[mineSize/size][mineSize%size]=-1;
          }
        }

        private void addNumberOfMinesNear(){
         for(int i=0;i<size;i++){
            for(int j=0;j<size;j++) {
                if (this.gridArray[i][j]!=-1){
                    gridArray[i][j]=this.minesNear(i, j);
                }
            }
         }
        }

        private int minesNear(int i,int j){
             int value=0;
            /*counter the number of mines near and return an int*/
            for(int bouclei=0;i<size;i++){
                for(int bouclej=0;j<size;j++) {
                    if (this.gridArray[bouclei][bouclej]==-1){
                        if (((bouclei>=i-1)&&(bouclei<=i+1) && ((bouclej>=j-1)&&(bouclej<=j+1)))){
                            value+=1;
                        }
                    }
                }
            }

            return value;
        }

        private void test(){
            for(int i = 0; i < size-1; i++) {
                for (int j = 0; j < size - 1; j++) {
                    t.append(""+this.gridArray[i][j]);
                }
                t.append("\n");
            }
        }

    }

    public int[] generateMines(double percentage, int size){
        List<Integer> list = new ArrayList<>();
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
            ret[i] = iterator.next();
        }
        return ret;
    }



}
