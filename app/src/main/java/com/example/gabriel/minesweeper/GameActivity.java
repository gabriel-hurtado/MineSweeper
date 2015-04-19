package com.example.gabriel.minesweeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import android.widget.TextView;


public class GameActivity extends Activity {
    private Intent in;
    private Bundle gameLog;
    private Boolean timer;
    private double percentage;
    private int size;
    private String UserName;
    private GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gridView = (GridView) findViewById(R.id.gridview);
        // test TextView t=(TextView)findViewById(R.id.myTextView);
        in = getIntent();
        gameLog = in.getExtras();
        timer = gameLog.getBoolean("timer");
        percentage = gameLog.getDouble("percentage");
        size = Integer.parseInt(gameLog.getString("size"));
        UserName = gameLog.getString("UserName");

        gridView.setNumColumns(size);
        gridView.setAdapter(new ButtonAdapter(this));

        if(timer){
            //showTimer();
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id)
            {
                //reactionar al click
            }
        });


        //showGame(gameGeneration);


        /*
        Use the Onclick to know is the game is ended, and at the end send an intent with the log in Bundle.
        (We must have a variable to know how much boxes are undiscovered)

        to check if lost:
        if (timer){
        verifyTimer();
        }
        boxClickedIsMine();

        at the end
        if(timer){
        gameLog.putInt("time",int time);
        }
        gameLog.putInt("remaining",int remainingMines);

        */



        /*test
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<size; i++){
            for(int j=0; j<size;j++){
                builder.append(String.valueOf(gameGeneration.getArray()[i][j])+" ");
            }
            builder.append("\n");
        }
        t.setText(builder.toString());
*/

    }





    public class grid {
      private int[][] gridArray;

        public grid(int size, double percentage){
           this.gridArray= new int[size][size];
            this.putMines(generateMines(percentage,size));
            this.addNumberOfMinesNear();
        }

        public int getSize(){
            return this.gridArray[0].length;
        }

        public int[] toSimpleArray(){
            int size=this.getSize();
            int[] simpleArray= new int[size*size];
            int counter=0;
            for (int i=0;i<size;i++){
                for (int j=0;j<size;j++){
                  simpleArray[counter]=this.gridArray[i][j];
                  counter++;
                }
            }
            return simpleArray;
        }

        private void putMines(int[] minesArray){
          for(int minePosition: minesArray){
           int line=getLine(minePosition,size);
           int column = getColumn(minePosition,size);
           this.gridArray[line][column]=-1;
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
            for(int bouclei=0;bouclei<size;bouclei++){
                for(int bouclej=0;bouclej<size;bouclej++) {
                    if (this.gridArray[bouclei][bouclej]==-1){
                        if (((bouclei>=i-1)&&(bouclei<=i+1) && ((bouclej>=j-1)&&(bouclej<=j+1)))){
                            value+=1;
                        }
                    }
                }
            }

            return value;
        }

        public int[][] getArray(){
            return this.gridArray;
        }



    }

    public int[] generateMines(double percentage, int size){
        List<Integer> list = new ArrayList<>();
        int[] listArray;
        int numberOfMines = (int) ((size*size)*percentage);
        for(int i = 0; i < numberOfMines;)
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

   public int getLine (int position,int size){
       return position/size;
   }

   public int getColumn (int position, int size){
       return position%size;
   }





    //class of the adaptor
    public class ButtonAdapter extends BaseAdapter {
        private Context mContext;
        private grid gameGeneration= new grid(size, percentage);


        public ButtonAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return gameGeneration.getSize()*gameGeneration.getSize();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            Button btn;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                btn = new Button(mContext);
                btn.setPadding(2, 2, 2, 2);
            }
            else {
                btn = (Button) convertView;
            }

            switch (gameGeneration.toSimpleArray()[position]){
                case 0:
                 btn.setBackgroundResource(R.drawable.empty);
                case 1:
                 btn.setBackgroundResource(R.drawable.n1);
                case 2:
                    btn.setBackgroundResource(R.drawable.n2);
                case 3:
                    btn.setBackgroundResource(R.drawable.n3);
                case 4:
                    btn.setBackgroundResource(R.drawable.n4);
                case 5:
                    btn.setBackgroundResource(R.drawable.n5);
                case 6:
                    btn.setBackgroundResource(R.drawable.n6);
                case 7:
                    btn.setBackgroundResource(R.drawable.n7);
                case 8:
                    btn.setBackgroundResource(R.drawable.n8);
                case -1:
                    btn.setBackgroundResource(R.drawable.bomb);}
            btn.setId(position);
            return btn;
        }
    }
        /* references to our images
        private Integer[] drawable = {
                R.drawable.undiscovered,
                R.drawable.n1,
                R.drawable.n2,
                R.drawable.n3,
                R.drawable.n4,
                R.drawable.n5,
                R.drawable.n6,
                R.drawable.n7,
                R.drawable.n8,
                R.drawable.bomb,
        };*/
    }
