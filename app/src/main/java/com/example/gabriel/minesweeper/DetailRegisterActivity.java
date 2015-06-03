package com.example.gabriel.minesweeper;


import android.support.v4.app.FragmentActivity;
import android.os.Bundle;


public class DetailRegisterActivity extends FragmentActivity {

    public static final String EXTRA_POSITION = "com.example.gabriel.minesweper.EXTRA_POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_register);

        RegisterFrag fgreg = (RegisterFrag)getSupportFragmentManager().findFragmentById(R.id.FrgRegister);
        fgreg.showDetail((getIntent().getStringExtra(EXTRA_POSITION)));
    }
}
