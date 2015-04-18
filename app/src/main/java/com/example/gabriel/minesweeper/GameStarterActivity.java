package com.example.gabriel.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class GameStarterActivity extends Activity {
    private Intent in = new Intent(this, GameActivity.class);
    private Bundle starterInfos = new Bundle();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_starter);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.rgPercentMines);
        radioGroup.setOnCheckedChangeListener(new RadioGroupInfo());
    }

     /*In the class with the mines percentages handler
     starterInfos.putDouble("percentage", Double value) */

    private class RadioGroupInfo implements RadioGroup.OnCheckedChangeListener {

        public RadioGroupInfo() {
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            RadioButton newChecked = (RadioButton)findViewById(checkedId);
            String dataNewChecked = newChecked.getText().toString();
            Double percentage = 0.0;
            switch (dataNewChecked){
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
            starterInfos.putDouble("percentage", percentage);
        }
    }

    /*In the class with the timer checkbox
     starterInfos.putBoolean("timer", boolean value) */

    public void onClickCheckBox(View clickedButton) {
        CheckBox buttonCheckbox = (CheckBox)clickedButton;
        String status;
        if (buttonCheckbox.isChecked()) {
            starterInfos.putBoolean("timer", true);
        } else {
            starterInfos.putBoolean("timer", false);
        }
    }

    public void showGame (View clickedButton) {

        final EditText et1 = (EditText)
                findViewById(R.id.GameStarterEditText1);
        String user_name = et1.getText().toString();
        starterInfos.putString("UserName", user_name);


        final EditText et2 = (EditText)
                findViewById(R.id.GameStarterEditText2);
        String size = et2.getText().toString();
        starterInfos.putString("size", size);

        in.putExtras(starterInfos);
        startActivity(in);
    }
}
