package com.example.gabriel.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class GameStarterActivity extends Activity {
    public Double percentage;
    Bundle starterInfos = new Bundle();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        percentage = 0.25;
        setContentView(R.layout.activity_game_starter);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgPercentMines);
        radioGroup.setOnCheckedChangeListener(new RadioGroupInfo());
    }

    public void onClickCheckBox(View clickedButton) {
        CheckBox buttonCheckbox = (CheckBox) clickedButton;
        if (buttonCheckbox.isChecked()) {
            starterInfos.putBoolean("timer", true);
        } else {
            starterInfos.putBoolean("timer", false);
        }
    }

    public void showGame(View clickedButton) {
        final EditText et1 = (EditText) findViewById(R.id.GameStarterEditText1);
        String user_name = et1.getText().toString();
        starterInfos.putString("UserName", user_name);

        final EditText et2 = (EditText) findViewById(R.id.GameStarterEditText2);
        String size = et2.getText().toString();
        starterInfos.putString("size", size);
        starterInfos.putDouble("percentage", percentage);

        Intent in = new Intent(this, GameActivity.class);
        in.putExtras(starterInfos);
        boolean good = false;

        if (!size.equals("")) {
            int sizeGrill = Integer.parseInt(size);
            if (sizeGrill > 2 && sizeGrill < 12) {
                good = true;
            }
        }

        if (user_name.equals("")) {
            if (good) {
                Toast.makeText(GameStarterActivity.this, "You have to put a content in User", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(GameStarterActivity.this, "You have to put a content in User and in Size (2<size<12)", Toast.LENGTH_LONG).show();
            }
        }

        if (!user_name.equals("")) {
            if (good) {
                startActivity(in);
            } else {
                Toast.makeText(GameStarterActivity.this, "You have to put a content in Size (2<size<12)", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class RadioGroupInfo implements RadioGroup.OnCheckedChangeListener {

        public RadioGroupInfo() {
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            RadioButton newChecked = (RadioButton) findViewById(checkedId);
            String dataNewChecked = newChecked.getText().toString();
            switch (dataNewChecked) {
                case "15 %":
                    percentage = 0.15;
                    break;
                case "25 %":
                    percentage = 0.25;
                    break;
                case "35 %":
                    percentage = 0.35;
                    break;
            }
        }
    }

}
