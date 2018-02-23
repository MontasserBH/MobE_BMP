package com.m2dl.mobebmp.mobe_bmp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ma_fac:
            /* DO EDIT */
                return true;
            case R.id.menu_mes_cours:
            /* DO ADD */
                return true;
            case R.id.menu_configuration:
            /* DO DELETE */
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
