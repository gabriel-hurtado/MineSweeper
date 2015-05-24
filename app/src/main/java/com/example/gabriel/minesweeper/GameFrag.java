package com.example.gabriel.minesweeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class GameFrag extends Fragment {

    GridListener mCallback;
    String minutes;
    String seconds;
    Boolean finished;
    Boolean isLandscape;
    FrameLayout layoutbase;
    int heightColumn;
    private Bundle gameLog;
    private Boolean timer;
    public double percentage;
    public int size;
    public GridView gridView;
    private Intent i;
    private Boolean victory;
    private int remainingBox;
    private MyCountDownTimer myCountDownTimer;
    long TIME;
    TextView timeTextView;
    TextView boxTextView;
    public tools game;
    private ClickedArray clickedButtons;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int ot = getActivity().getResources().getConfiguration().orientation;
        switch(ot) {
            case Configuration.ORIENTATION_LANDSCAPE:
                isLandscape = true;
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                isLandscape = false;
                break;
        }

        gameLog = getActivity().getIntent().getExtras();
        finished = false;
        timer = gameLog.getBoolean("timer");
        percentage = gameLog.getDouble("percentage");
        size = Integer.parseInt(gameLog.getString("size"));
        game= new tools(size, percentage);
        int numberOfMine = (int) (size * size * percentage);
        remainingBox = (size * size) - (numberOfMine);
        TIME = size * size * 4000;
        clickedButtons = new ClickedArray(size);

        if (timer) {
            startTIME(TIME);
        }

        if (savedInstanceState != null) {
            clickedButtons.writeArray(savedInstanceState.getIntArray("clicked"));
            clickedButtons.writeNum(savedInstanceState.getInt("num"));
            game=savedInstanceState.getParcelable("CUSTOM_LISTING");

            if (timer) {
                minutes = String.valueOf(savedInstanceState.getString("saved_minutes"));
                seconds = String.valueOf(savedInstanceState.getString("saved_seconds"));
                TIME = Long.parseLong(minutes) * 60000 +  Long.parseLong(seconds) * 1000;
                startTIME(TIME);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        if(isLandscape){
            layoutbase = (FrameLayout) getView().findViewById(R.id.layout);
        }

        gridView = (GridView) getView().findViewById(R.id.gridview);
        gridView.setNumColumns(size);
        gridView.setAdapter(new ButtonAdapter(getActivity()));

        boxTextView = (TextView) getView().findViewById(R.id.GameTextView3);
        boxTextView.setText("Remaining Box : " + remainingBox);
        //Execute ths after gridview generation
        Handler myHandler = new Refresh();
        Message m = new Message();
        myHandler.sendMessageDelayed(m, 1000);

        i = new Intent(getActivity(), ResultGameActivity.class);
    }

    @Override
    public void onAttach(Activity ac) {
        super.onAttach(ac);

        try {
            mCallback = (GridListener) ac;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(ac.toString() + " must implement OnCorreosListener");
        }
    }

    public void startTIME(long TIME) {
        myCountDownTimer = new MyCountDownTimer(TIME, 1000);
        myCountDownTimer.start();
    }

    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long milliseconds, long countDownInterval) {
            super(milliseconds, countDownInterval);
        }

        public void onTick(long millisUntilFinished) {
            minutes = String.format("%02d", millisUntilFinished / 60000);
            seconds = String.format("%02d", millisUntilFinished % 60000 / 1000);
            TextView timeTextView = (TextView) getView().findViewById(R.id.GameTextView2);
            timeTextView.setText("Time remaining : " + minutes + ":" + seconds);
        }

        public void onFinish() {
            if (!finished) {
                finished = true;
                victory = false;
                seconds = "0";
                Toast.makeText(getActivity(), "No more time ! Game over ...", Toast.LENGTH_SHORT).show();
                stopGame();
            }
        }
    }

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
                int width = tools.getColumnWidth(mContext, (GridView) ((GameActivity) mContext).findViewById(R.id.gridview));

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

    public interface GridListener {
        void onBoxSelected(ClickedArray cl);
    }

    public void setGridListener(GridListener listener) {
        this.mCallback = listener;
    }

    class MyOnClickListener implements View.OnClickListener {
        private int position;

        public MyOnClickListener(int position) {
            this.position = position;
        }

        public void onClick(View v) {
            if (!finished) {
                clickedButtons.clicked(this.position, Integer.parseInt(minutes), Integer.parseInt(seconds));
                switch (game.getGrid().toSimpleArray()[this.position]) {
                    case 0:
                        v.setBackgroundResource(R.drawable.empty);
                        remainingBox -= 1;
                        v.setEnabled(false);
                        for (Button but : game.BoxesNear(this.position, gridView)) {
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
                        Toast.makeText(getActivity(), "Booom ! Game over ...", Toast.LENGTH_SHORT).show();
                        victory = false;
                        gameLog.putInt("position", position);
                        for (Button but : tools.getButtons(game.getMinePos(), game.getMinePos().length, gridView) ){
                            but.setBackgroundResource(R.drawable.bomb);
                        }
                        v.setBackgroundResource(R.drawable.bomb);
                        stopGame();
                        break;
                }

                if (remainingBox == 0) {
                    finished = true;
                    victory = true;
                    Toast.makeText(getActivity(), "Look like we've got a winner !", Toast.LENGTH_SHORT).show();
                    stopGame();
                }

                TextView textView = (TextView) getView().findViewById(R.id.GameTextView3);
                textView.setText("Remaining Box : " + remainingBox);
            }
            mCallback.onBoxSelected(clickedButtons);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("saved_remaining_box", remainingBox);
        outState.putIntArray("clicked", clickedButtons.getArray());
        outState.putInt("num", clickedButtons.getNum());
        outState.putParcelable("CUSTOM_LISTING", game  );
        if (timer) {
            outState.putString("saved_minutes", minutes);
            outState.putString("saved_seconds", seconds);
            myCountDownTimer.cancel();
        }
    }

    class Refresh extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (clickedButtons.getNum() != 0) {
                for (Button but : tools.getButtons(clickedButtons.getClicked(), clickedButtons.getNum(), gridView)) {
                    if (but.isEnabled()) {
                        but.performClick();

                    }
                }
            }
        }
    }

}
