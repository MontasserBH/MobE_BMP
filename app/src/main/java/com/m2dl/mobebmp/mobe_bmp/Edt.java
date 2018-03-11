package com.m2dl.mobebmp.mobe_bmp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * activity qui affiche l'emploi du temps en fonction de l'url par défaut (M2 DL) ou celle entrée dans les paramètres
 */

public class Edt extends AppCompatActivity {
    private WebView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edt);
        view = (WebView) findViewById(R.id.edtView);
        view.setWebViewClient(new WebViewClient());
        view.getSettings().setLoadsImagesAutomatically(true);
        view.getSettings().setJavaScriptEnabled(true);
        view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        view.loadUrl(getUrl());
        view.getSettings().setBuiltInZoomControls(true);
        view.getSettings().setDisplayZoomControls(false);
    }

    private String getUrl()
    {
        /*SharedPreferences settings = getSharedPreferences("preferences", Context.MODE_PRIVATE);*/
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String url = settings.getString(getString(R.string.config_urlEdt_key), getString(R.string.config_urlEdt_value));
        if (url.equals(""))
        {
            url = getString(R.string.config_urlEdt_value);
        }
        return url;
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
                intent = new Intent(Edt.this, Home.class);
                startActivity(intent);
                return true;
            case R.id.menu_mes_cours:
            /* DO ADD */
                return true;
            case R.id.menu_configuration:
            /* DO DELETE */
                return true;
            case R.id.menu_informations_personnelles :
                intent = new Intent(Edt.this, Configurations.class);
                startActivity(intent);
                return true;
            case R.id.menu_emploi_du_temps:
                intent = new Intent(Edt.this, Edt.class);
                startActivity(intent);
                return true;
            case R.id.menu_informations_fac:
                intent = new Intent(Edt.this,QRCodeReader.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
