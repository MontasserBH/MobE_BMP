package com.m2dl.mobebmp.mobe_bmp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Franck on 09/03/2018.
 */

public class Configurations extends PreferenceActivity implements View.OnClickListener {

    SharedPreferences settings;
    Button validation;
    EditText urlEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getSharedPreferences("preferences.xml", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_configurations);
        validation = (Button) findViewById(R.id.config_validation);
        urlEdt = (EditText) findViewById(R.id.config_urlEdt_value);

        validation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String newUrlEdt = urlEdt.getText().toString();

        SharedPreferences.Editor edit = settings.edit();
        edit.putString(getString(R.string.config_urlEdt_key), newUrlEdt);
        edit.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_ma_fac:
            /* DO EDIT */
                return true;
            case R.id.menu_geoloc:
                intent = new Intent(Configurations.this, Home.class);
                startActivity(intent);
                return true;
            case R.id.menu_mes_cours:
            /* DO ADD */
                return true;
            case R.id.menu_configuration:
            /* DO DELETE */
                return true;
            case R.id.menu_informations_personnelles :
                intent = new Intent(Configurations.this, Configurations.class);
                startActivity(intent);
                return true;
            case R.id.menu_emploi_du_temps:
                intent = new Intent(Configurations.this, Edt.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}