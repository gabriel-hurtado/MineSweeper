package com.example.gabriel.minesweeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

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
    private grid gameGeneration;
    private Intent i;
    private Boolean victory;
    private int remainingBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gridView = (GridView) findViewById(R.id.gridview);
        in = getIntent();
        gameLog = in.getExtras();
        timer = gameLog.getBoolean("timer");
        percentage = gameLog.getDouble("percentage");
        size = Integer.parseInt(gameLog.getString("size"));
        UserName = gameLog.getString("UserName");
        gameGeneration = new grid(size, percentage);
        remainingBox=(int) ((size * size) - (size * size * percentage)+1.0);
        i = new Intent(this, ResultGameActivity.class);

        gridView.setNumColumns(size);
        gridView.setAdapter(new ButtonAdapter(this));

        if (timer) {
            CountDownTimer cT = new CountDownTimer(120000, 1000) {
                TextView textView = (TextView) findViewById(R.id.GameTextview);

                public void onTick(long millisUntilFinished) {

                    String minutes = String.format("%02d", millisUntilFinished / 60000);
                    String seconds = String.format("%02d", millisUntilFinished % 60000 / 1000);

                    textView.setText("Time remaining : " + minutes + ":" + seconds);
                }

                public void onFinish() {
                    //put the time left in the bundle
                    victory=false;
                    stopGame();

                }
            };
            cT.start();
        }
    }


       public void stopGame(){
        gameLog.putBoolean("victory",victory);
        i.putExtras(gameLog);
        startActivity(i);
    }


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






    class MyOnClickListener implements View.OnClickListener
    {
        private final int position;

        public MyOnClickListener(int position)
        {
            this.position = position;
        }

        public void onClick(View v)
        {
            switch (gameGeneration.toSimpleArray()[this.position]){
                case 0:
                    v.setBackgroundResource(R.drawable.empty);
                    remainingBox-=1;
                    break;
                case 1:
                    v.setBackgroundResource(R.drawable.n1);
                    remainingBox-=1;
                    break;
                case 2:
                    v.setBackgroundResource(R.drawable.n2);
                    remainingBox-=1;
                    break;
                case 3:
                    v.setBackgroundResource(R.drawable.n3);
                    remainingBox-=1;
                    break;
                case 4:
                    v.setBackgroundResource(R.drawable.n4);
                    remainingBox-=1;
                    break;
                case 5:
                    v.setBackgroundResource(R.drawable.n5);
                    remainingBox-=1;
                    break;
                case 6:
                    v.setBackgroundResource(R.drawable.n6);
                    remainingBox-=1;
                    break;
                case 7:
                    v.setBackgroundResource(R.drawable.n7);
                    remainingBox-=1;
                    break;
                case 8:
                    v.setBackgroundResource(R.drawable.n8);
                    remainingBox-=1;
                    break;
                case -1:
                    Toast.makeText(getApplicationContext(), "Booom ! Game over ...", Toast.LENGTH_SHORT).show();
                    victory=false;
                    v.setBackgroundResource(R.drawable.bomb);
                    stopGame();
                    break;}

            if(remainingBox==0){
                victory=true;
                Toast.makeText(getApplicationContext(), "Look like we've got a winner !", Toast.LENGTH_SHORT).show();
                stopGame();
            }
        }
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


        public ButtonAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return size*size;
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
                btn = new Button(mContext);
                btn.setPadding(2, 2, 2, 2);
            }
            else {
                btn = (Button) convertView;
            }

            btn.setId(position);
            btn.setBackgroundResource(R.drawable.undiscovered);
            btn.setOnClickListener(new MyOnClickListener(position));
            return btn;
        }
    }
    }
