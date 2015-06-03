package com.example.gabriel.minesweeper;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class AccesDBActivity extends FragmentActivity implements QueryFrag.QueryListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acces_db);

        QueryFrag frgQuery = (QueryFrag)getSupportFragmentManager().findFragmentById(R.id.FrgQuery);
        frgQuery.setQueryListener(this);
    }

    @Override
    public void onGameSelected(String id) {
        boolean queryInfo =
                (getSupportFragmentManager().findFragmentById(R.id.FrgRegister) != null);

        if (queryInfo) {
            RegisterFrag fgreg = (RegisterFrag) getSupportFragmentManager().findFragmentById(R.id.FrgRegister);
            fgreg.showDetail(id);
        }
        else {
            Intent i = new Intent (this,DetailRegisterActivity.class);
            i.putExtra(DetailRegisterActivity.EXTRA_POSITION, id);
            startActivity(i);
        }

    }
}
