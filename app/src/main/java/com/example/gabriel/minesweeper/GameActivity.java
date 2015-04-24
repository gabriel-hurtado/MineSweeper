package com.example.gabriel.minesweeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GameActivity extends Activity {
    String minutes;
    String seconds;
    Boolean finished;
    Boolean isLandscape;
    FrameLayout layoutbase;
    int heightColumn;
    int[] minePositions;
    private Intent in;
    private Bundle gameLog;
    private Boolean timer;
    private double percentage;
    private int size;
    private GridView gridView;
    private grid gameGeneration;
    private Intent i;
    private Boolean victory;
    private int remainingBox;
    int TIME;
    TextView timeTextView;
    TextView boxTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gridView = (GridView) findViewById(R.id.gridview);
        in = getIntent();
        gameLog = in.getExtras();
        finished = false;
        isLandscape = getApplicationContext().getResources().getBoolean(R.bool.is_landscape);
        timer = gameLog.getBoolean("timer");
        percentage = gameLog.getDouble("percentage");
        size = Integer.parseInt(gameLog.getString("size"));
        minePositions = generateMines(percentage,size);
        gameGeneration = new grid(size, percentage);
        int numberOfMine = (int) (size*size*percentage);
        remainingBox = (size * size) - (numberOfMine);
        i = new Intent(this, ResultGameActivity.class);
        TIME = 120000;


        gridView.setNumColumns(size);
        gridView.setAdapter(new ButtonAdapter(this));

        boxTextView = (TextView) findViewById(R.id.GameTextView3);
        boxTextView.setText("Number of Mines : " + remainingBox);

        if (isLandscape) {
            layoutbase = (FrameLayout) findViewById(R.id.layout);
        }

        if (timer) {
            new CountDownTimer(TIME, 1000) {
                TextView timeTextView = (TextView) findViewById(R.id.GameTextView2);

                public void onTick(long millisUntilFinished) {
                    minutes = String.format("%02d", millisUntilFinished / 60000);
                    seconds = String.format("%02d", millisUntilFinished % 60000 / 1000);
                    timeTextView.setText("Time remaining : " + minutes + ":" + seconds);
                }

                public void onFinish() {
                    if (!finished) {
                        finished = true;
                        victory = false;
                        seconds = "0";
                        Toast.makeText(getApplicationContext(), "No more time ! Game over ...", Toast.LENGTH_SHORT).show();
                        stopGame();
                    }
                }
            }.start();
        }
    }

    /*Intento de guardar los datos del echo de girrar la plantalla
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
       super.onRestoreInstanceState(savedInstanceState);
       int saveRemainingBox = savedInstanceState.getInt("saved_remaining_box");
       boxTextView.setText("Number of Mines : " + saveRemainingBox);
       //if (timer) {
           int saveMinutes = Integer.parseInt(savedInstanceState.getString("saved_minutes"));
           int saveSeconds = Integer.parseInt(savedInstanceState.getString("saved_seconds"));
           String CHRONO = String.valueOf(saveMinutes * 60000 + saveSeconds * 1000);
       }//
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("saved_remaining_box", remainingBox);
        //if(timer){
            outState.putString("saved_minutes", minutes);
            outState.putString("saved_seconds", seconds);
        }//
    }*/

    public void stopGame() {
        if (timer) {
            gameLog.putInt("minutes", Integer.parseInt(minutes));
            gameLog.putInt("seconds", Integer.parseInt(seconds));
        }

        gameLog.putBoolean("victory", victory);
        gameLog.putInt("remainingBox", remainingBox);
        i.putExtras(gameLog);
        // Execute some code after 2 seconds have passed
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(i);
            }
        }, 2000);

    }

    public int[] generateMines(double percentage, int size) {
        List<Integer> list = new ArrayList<>();
        int[] listArray;
        int numberOfMines = (int) ((size * size) * percentage);
        for (int i = 0; i < numberOfMines; ) {
            int rand = ((int) (Math.random() * (size * size) ));
            if (!list.contains(rand)) {
                list.add(rand);
                i++;
            }
        }
        listArray = convertIntegers(list);
        return listArray;
    }

    public int getLine(int position, int size) {
        return position / size;
    }

    public int getColumn(int position, int size) {
        return position % size;
    }

    private int getColumnWidth(Context context, GridView gridView) {
        if (android.os.Build.VERSION.SDK_INT >= 16)
            return gridView.getColumnWidth();

        int cols = gridView.getNumColumns();
        int width = gridView.getWidth();

        return (width) / cols;
    }

    public Button[] BoxesNear(int position) {
        int numberOfLine = position / size;
        int numberOfColumn = position % size;
        int[] positions = new int[8];
        int numNear = 0;
        boolean notSameCase;

        for (int bouclei = 0; bouclei < size; bouclei++) {
            for (int bouclej = 0; bouclej < size; bouclej++) {
                notSameCase = !((bouclei == numberOfLine) && (bouclej == numberOfColumn));
                if ((((bouclei >= numberOfLine - 1) && (bouclei <= numberOfLine + 1)) && ((bouclej >= numberOfColumn - 1) && (bouclej <= numberOfColumn + 1))) && notSameCase) {
                    positions[numNear] = bouclei * size + bouclej;
                    numNear += 1;
                }
            }
        }
        return getButtons(positions, numNear);

    }

    public Button[] getButtons(int[] positions, int length) {
        Button[] boxes = new Button[length];
        int tempPos;
        for (int i = 0; i < length; i++) {
            tempPos = positions[i];
            boxes[i] = (Button) gridView.getChildAt(tempPos);
        }
        return boxes;
    }

    class MyOnClickListener implements View.OnClickListener {
        private int position;

        public MyOnClickListener(int position) {
            this.position = position;
        }

        public void onClick(View v) {
            if (!finished) {
                switch (gameGeneration.toSimpleArray()[this.position]) {
                    case 0:
                        v.setBackgroundResource(R.drawable.empty);
                        remainingBox -= 1;
                        v.setEnabled(false);
                        for (Button but : BoxesNear(this.position)) {
                            if (but.isEnabled()) {
                                but.performClick();
                            }
                        }
                        break;
                    case 1:
                        v.setBackgroundResource(R.drawable.n1);
                        v.setEnabled(false);
                        remainingBox -= 1;
                        break;
                    case 2:
                        v.setBackgroundResource(R.drawable.n2);
                        v.setEnabled(false);
                        remainingBox -= 1;
                        break;
                    case 3:
                        v.setBackgroundResource(R.drawable.n3);
                        v.setEnabled(false);
                        remainingBox -= 1;
                        break;
                    case 4:
                        v.setBackgroundResource(R.drawable.n4);
                        v.setEnabled(false);
                        remainingBox -= 1;
                        break;
                    case 5:
                        v.setBackgroundResource(R.drawable.n5);
                        v.setEnabled(false);
                        remainingBox -= 1;
                        break;
                    case 6:
                        v.setBackgroundResource(R.drawable.n6);
                        v.setEnabled(false);
                        remainingBox -= 1;
                        break;
                    case 7:
                        v.setBackgroundResource(R.drawable.n7);
                        v.setEnabled(false);
                        remainingBox -= 1;
                        break;
                    case 8:
                        v.setBackgroundResource(R.drawable.n8);
                        v.setEnabled(false);
                        remainingBox -= 1;
                        break;
                    case -1:
                        v.setEnabled(false);
                        finished = true;
                        Toast.makeText(getApplicationContext(), "Booom ! Game over ...", Toast.LENGTH_SHORT).show();
                        victory = false;
                        gameLog.putInt("position", position);
                        for (Button but : getButtons(minePositions,minePositions.length) ){
                                but.setBackgroundResource(R.drawable.bomb);
                        }
                        v.setBackgroundResource(R.drawable.bomb);
                        stopGame();
                        break;
                }

                if (remainingBox == 0) {
                    finished = true;
                    victory = true;
                    Toast.makeText(getApplicationContext(), "Look like we've got a winner !", Toast.LENGTH_SHORT).show();
                    stopGame();
                }

            TextView textView = (TextView) findViewById(R.id.GameTextView3);
            textView.setText("Number of Boxes : " + remainingBox);
            }
        }
    }

    public class grid {
        private int[][] gridArray;

        public grid(int size, double percentage) {

            this.gridArray = new int[size][size];
            this.putMines(minePositions);
            this.addNumberOfMinesNear();
        }

        public int getSize() {
            return this.gridArray[0].length;
        }

        public int[] toSimpleArray() {
            int size = this.getSize();
            int[] simpleArray = new int[size * size];
            int counter = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    simpleArray[counter] = this.gridArray[i][j];
                    counter++;
                }
            }
            return simpleArray;
        }

        private void putMines(int[] minesArray) {
            for (int minePosition : minesArray) {
                int line = getLine(minePosition, size);
                int column = getColumn(minePosition, size);
                this.gridArray[line][column] = -1;
            }
        }

        private void addNumberOfMinesNear() {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (this.gridArray[i][j] != -1) {
                        gridArray[i][j] = this.minesNear(i, j);
                    }
                }
            }
        }

        private int minesNear(int i, int j) {
            int value = 0;
            /*counter the number of mines near and return an int*/
            for (int bouclei = 0; bouclei < size; bouclei++) {
                for (int bouclej = 0; bouclej < size; bouclej++) {
                    if (this.gridArray[bouclei][bouclej] == -1) {
                        if (((bouclei >= i - 1) && (bouclei <= i + 1) && ((bouclej >= j - 1) && (bouclej <= j + 1)))) {
                            value += 1;
                        }
                    }
                }
            }
            return value;
        }


    }

    public class ButtonAdapter extends BaseAdapter {
        private Context mContext;

        public ButtonAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return size * size;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Button btn;
            btn = new Button(mContext);

            btn.setBackgroundResource(R.drawable.undiscovered);

            if (!isLandscape) {

                //Scale button using layout params
                int width = getColumnWidth(mContext, (GridView) ((GameActivity) mContext).findViewById(R.id.gridview));

                btn.setLayoutParams(new GridView.LayoutParams(width, width));
            } else {
                heightColumn = layoutbase.getHeight();
                int widthFull = layoutbase.getWidth();
                heightColumn = heightColumn / (size);
                btn.setLayoutParams(new GridView.LayoutParams(heightColumn, heightColumn));
                if (heightColumn > 0) {
                    int paddingLeft = (widthFull / 2) - ((size * heightColumn) / 2);
                    gridView.setHorizontalSpacing(heightColumn);
                    gridView.setPadding(paddingLeft, 0, 0, 0);
                }
            }
            btn.setOnClickListener(new MyOnClickListener(position));
            return btn;
        }
    }

    public static int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next();
        }
        return ret;
    }

}
